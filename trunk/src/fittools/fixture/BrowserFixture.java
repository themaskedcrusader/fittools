/* FitTools: FitNesse Plugin for Automation of Web Applications
 * Copyright (c) 2015 Christopher Schalk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 **********************************************************************************************************************/

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
