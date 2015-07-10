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

var browserHeader = "!| fittools.fixture.BrowserFixture | ${SEL_HOST} | ${SEL_PORT} | ${BASEURL} | ${BROWSER} |\n";
var target = "${commands[i].target}";
var value = "${commands[i].value}";
var doCommand = "| do command | ${commands[i].command} |";
var doCommandTarget = " with target | " + target + " |";
var doCommandValue = " and value | " + value + " |";
var verify = "| verify | ${commands[i].command} | ";
var storeValue = " | in global | " + value + " |";

function format(testCase, name) {
    return formatCommands(testCase.commands);
}

function formatCommands(commands) {
    var template = "";
    var commandText = browserHeader;

    for (var i = 0; i < commands.length; i++) {

        if (commands[i].command.substring(0, 6) == "assert" ||
                commands[i].command.substring(0, 6) == "verify") {
            commands[i].command = commands[i].command.replace("verify", "assert");
            template = verify;
            template = addSuffix(template, commands[i], doCommandTarget, doCommandValue);

        } else if (commands[i].command.substring(0, 5) == "store") {
            template = prepStore(commands[i].command);

        } else {
            template = doCommand;
            template = addSuffix(template, commands[i], doCommandTarget, doCommandValue);
        }

        if (template != '') {
            var newText = template.replace(/\$\{([a-zA-Z0-9_\.\[\]]+)\}/g,
                    function(str, p1, offset, s) {
                        result = eval(p1);
                        return result != null ? result : '';
                    });

            commandText = commandText + newText + "\n";
        }
    }

    return commandText;
}

function prepStore(command) {
    var toReturn;
    switch (command) {
        case 'storeTextPresent'    : toReturn = "| store text present | ";          break;
        case 'storeText'           : toReturn = "| store text | ";                  break;
        case 'storeTitle'          : toReturn = "| store page title in global | ";  break;
        case 'storeValue'          : toReturn = "| store value of | ";              break;
        case 'storeTable'          : toReturn = "| store table element at | ";      break;
        case 'storeElementPresent' : toReturn = "| store element present | ";       break;
        default: return '';
    }

    return addSuffix(toReturn, command, target, storeValue);
}

function addSuffix(template, command, target, value) {
    if (command.target != '') {
        template = template + target;
    }

    if (command.value != '') {
        template = template + value;
    }

    return template;
}