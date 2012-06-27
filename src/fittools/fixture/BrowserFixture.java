/* FitTools: FitNesse Plugin for Automation of Web Applications
 * Copyright (C) 2009-2012, Christopher Schalk (www.themaskedcrusader.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * Contact the author via email at: christopher.schalk@gmail.com
 */

package fittools.fixture;

import com.thoughtworks.selenium.*;

public class BrowserFixture extends BaseDoFixture {

    public BrowserFixture() {
    }

    public void startBrowser() {
        String selHost = args[0];
        int selPort = Integer.parseInt(args[1]);
        String baseURL = args[2];
        String browser = args[3];
        utils.log("Selenium Host: " + selHost);
        utils.log("Selenium Port: " + selPort);
        utils.log("Browser Type : " + browser);
        if (!utils.isStarted()) {
            try {
                utils.cp = new HttpCommandProcessor(selHost, selPort, browser, baseURL);
                utils.cp.start();
                utils.setStarted(true);
            } catch (Exception e) {
                if (e.getMessage().indexOf("Connection refused: connect") != -1) {
                    throw new RuntimeException("Could not contact Selenium Server; have you started it?\n" +
                            e.getMessage());
                }
            }
        }
    }

    public String doCommand(String command) {
        return doCommandWithTarget(command, null);
    }

    public String doCommandWithTarget(String command, String target) {
        return doCommandWithTargetAndValue(command, target, null);
    }

    public String doCommandWithTargetAndValue(String command, String target, String value) {
        if (!utils.isStarted())
            startBrowser();
        if (utils.isStarted()) {
            String retVal = command + ":";
            if (target == null) {
                value += utils.cp.doCommand(command, new String[]{});
            } else if (value == null) {
                value += utils.cp.doCommand(command, new String[]{target,});
            } else {
                value += utils.cp.doCommand(command, new String[]{target, value,});
            }
            utils.debug(command + ":" + retVal);
            return retVal;
        }
        return "Selenium Not Running!";
    }

    // todo: Add meaningful debugging to these fixtures
    // todo: Parse for Global before processing.

    public boolean verifyWithTarget(String command, String target) {
        return verifyWithTargetAndValue(command, target, null);
    }

    public boolean verifyWithTargetAndValue(String command, String target, String value) {
        try {
            doCommandWithTargetAndValue(command, target, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void storeTextPresentInGlobal(String target, String value) {
        String text = utils.cp.getString("isTextPresent", new String[]{target,});
        utils.setGlobal(value, text);
    }

    public void storePageTitleInGlobal(String value) {
        String text = utils.cp.getString("getTitle", null);
        utils.setGlobal(value, text);
    }

    public void storeValueOfInGlobal(String target, String value) {
        String text = utils.cp.getString("getValue", new String[]{target,});
        utils.setGlobal(value, text);
    }

    public void storeTextInGlobal(String target, String value) {
        String text = utils.cp.getString("getText", new String[]{target,});
        utils.setGlobal(value, text);
    }

    public void storeTableElementAtInGlobal(String target, String value) {
        String text = utils.cp.getString("getTable", new String[]{target,});
        utils.setGlobal(value, text);
    }

    public void storeElementPresentInGlobal(String target, String value) {
        String text = utils.cp.getString("isElementPresent", new String[] {target,});
        utils.setGlobal(value, text);
    }

    public void stopSelenium() {
        if (utils.isStarted()) {
            utils.cp.stop();
            utils.setStarted(false);
        }
    }

    public void pauseSeconds(int seconds) throws Exception {
        Thread.sleep(seconds * 1000);
    }

    public void pauseSecond(int second) throws Exception {
        pauseSeconds(second);
    }

    public boolean setDebug() {
        utils.debug = true;
        return utils.debug;
    }

}
