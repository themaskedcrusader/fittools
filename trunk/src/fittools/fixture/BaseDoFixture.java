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

}
