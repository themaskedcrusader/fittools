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

package fittools;

import com.thoughtworks.selenium.CommandProcessor;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("unchecked")
public class FitToolsUtils {
    public static CommandProcessor cp;
    public static boolean debug;

    private boolean started;
    private Map global = new HashMap();
    private int count = 0;
    private static FitToolsUtils thisInstance = new FitToolsUtils();

    private FitToolsUtils() {}

    public static FitToolsUtils getInstance() {
        return thisInstance;
    }

    public String getGlobal(String key) {
        return (String) global.get(key);
    }

    public void setGlobal(String key, String value) {
        this.global.put(key, value);
    }

    private int countUp() {
        count++;
        if (count == 10)
            count = 0;
        return count;
    }

    public String generateUnique() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssS");
        return sdf.format(d) + countUp();
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return started;
    }

    public void log(String message) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String caller = stack[2].getMethodName();
        if (debug)
            System.out.println("[" + caller + "]:: " + message);
    }

    public void debug(String message) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String caller = stack[2].getMethodName();
        if (debug)
            System.out.println("DEBUG [" + caller + "]:: " + message);
    }
}
