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

import fitlibrary.DoFixture;
import fittools.FitToolsUtils;

public class BaseDoFixture extends DoFixture {

    protected final String SPACE = " ";

    protected FitToolsUtils utils = FitToolsUtils.getInstance(); // for utils variables

    public String parse(String toParse) {
        utils.debug("Parsing: " + toParse);
        String[] tokens = toParse.split(SPACE);
        String toReturn = "";
        for (String token : tokens) {
            if (token.indexOf("#[") > -1 && token.indexOf("]") > -1) {
                utils.log("Global Found: " + token);
                toReturn += utils.getGlobal(token.substring(2, token.length() - 1)) + SPACE;
            } else {
                toReturn += token + SPACE;
            }
        }
        utils.debug("Returning: " + toReturn);
        return toReturn.trim();
    }

    public boolean printGplWarranty() {
        System.out.println("" +
                " ----- GPL Warranty Notice: ------------------------------------------\n " +
                " FitTools: FitNesse Plugin for Automation of Web Applications\n" +
                " Copyright (C) 2009, Christopher Schalk (www.themaskedcrusader.com)\n\n" +
                " This program is free software; you can redistribute it and/or\n" +
                " modify it under the terms of the GNU General Public License\n" +
                " as published by the Free Software Foundation; either version 2\n" +
                " of the License, or (at your option) any later version.\n" + " \n" +
                " This program is distributed in the hope that it will be useful,\n" +
                " but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                " MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                " GNU General Public License for more details.\n" + " \n" +
                " You should have received a copy of the GNU General Public License\n" +
                " along with this program; if not, write to the Free Software\n" +
                " Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.\n\n" +
                " Contact the author via email at: christopher.schalk@gmail.com\n" +
                "--------------------------------------------------------------------");
        return true;
    }

    public boolean printRedistributionConditions() {
        System.out.println("REDISTRIBUTION CONDITIONS NOT AVAILABLE, YET");
        return false;
    }
}
