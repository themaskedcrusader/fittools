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

@SuppressWarnings("unused")
public class StringFixture extends BaseDoFixture {
    // todo: Add meaningful log/debug messages

    /**
     * Used to create a unique string for specified global.  Unique string is a 5 digit unique number.
     * <b>Usage:</b>
     * <pre>
     * | make unique string for global | myGlobal |
     * </pre>
     *
     * @param global - variable to store generated unique string within
     * @return a copy of the text stored in the global
     */

    public String makeUniqueStringForGlobal(String global) {
        String unique = super.utils.generateUnique();
        unique = unique.substring(unique.length() - 5);
        super.utils.setGlobal(global, unique);
        return unique;
    }

    /**
     * Used to create a unique string of a specified length for specified global.
     * <b>Usage:</b>
     * <pre>
     * | make unique string for global | myGlobal | with length | 9 |
     * </pre>
     *
     * @param global - variable to store generated unique string within
     * @param length - final length of generated string
     * @return a copy of the text stored in the global
     */
    public String makeUniqueStringForGlobalWithLength(String global, int length) {
        String toReturn = "";
        do {
            toReturn += super.utils.generateUnique();
        } while (toReturn.length() < length);
        toReturn = toReturn.substring(toReturn.length() - length);
        super.utils.setGlobal(global, toReturn);
        return toReturn;
    }

    /**
     * Used to create a paragraph of unique words with varying lengths.  Useful when you need to
     * generate a unique string of words, <b>Usage:</b>
     * <pre>
     * | make unique paragraph for global | myGlobal | with length | 15 |
     * </pre>
     *
     * @param global - variable to store generated paragraph within.
     * @param length - how many words to include in paragraph
     */
    public void makeUniqueParagraphForGlobalWithLength(String global, int length) {
        String paragraph = "";
        String word;
        for (int x = 0; x < length; x++) {
            word = makeUniqueStringForGlobalWithLength(global, (int) (Math.random() * 8) + 2);
            paragraph += word + " ";
        }
        utils.setGlobal(global, paragraph);
    }

    /**
     * Used to store a string inside of a global.  Useful when you will be reusing the same string
     * an unknown number of times and to increase manageability of tests.  specified string is parsed
     * and can include previously defined global. <b>Usage:</b>
     * <pre>
     * | store string | this is my string | in global | myGlobal |
     * </pre>
     *
     * @param toStore - String to store in global (parsed)
     * @param global  - variable to store parsed string within.
     */
    public void storeStringInGlobal(String toStore, String global) {
        toStore = parse(toStore);
        utils.setGlobal(global, toStore.trim());
    }

    /**
     * Used to store a specified single word from a string in a global. Useful if you know you will
     * get a certain string and you need only one word from that string for use in later tests.
     * Specified string can include previously defined global. <b>Usage:</b>
     * <pre>
     * | store token | 3 | of string | #[myGlobal] | in global | myGlobal |
     * </pre>
     *
     * @param token  - integer value of word in string, starting with 1 for first word
     * @param string - string that contains wanted word
     * @param global - variable to store word within.
     */
    public void storeTokenOfStringInGlobal(int token, String string, String global) {
        string = parse(string);
        String[] tokens = string.split(" ");
        utils.setGlobal(global, tokens[token - 1]);
    }

    /**
     * Used to print the contents of a global to the test output.
     * <b>Usage:</b>
     * <pre>
     * | print global | myGlobal |
     * </pre>
     *
     * @param global - variable whose contents to print to the FitNesse error page
     * @return true - highlights the row green (successful) in FitNesse
     */
    public boolean printGlobal(String global) {
        System.out.println(global + " value is: " + utils.getGlobal(global));
        return true;
    }

    /**
     * Compares two strings and returns true if they are equal, false if they are not. String is
     * parsed for global before comparison is made. <b>Usage:</b>
     * <pre>
     * | compare string | String 1 | with global | myGlobal |
     * </pre>
     *
     * @param string - String to compare
     * @param global - Global to compare
     * @return - result of comparison (i.e. whether or not the string and the global are the same)
     */
    public boolean compareStringWithGlobal(String string, String global) {
        string = parse(string);
        global = utils.getGlobal(global);
        utils.debug("Comparing: " + string + " : " + global);
        return (string.equals(global));
    }

    /**
     * Used to trim a specified number of characters from the beginning of the global. Useful if
     * the data you need within global doesn't start at the beginning of a word (i.e. an invoice
     * number that is formatted #12345, you need 12345 for later tests). Can be used singular or
     * plural. <b>Usage:</b>
     * <pre>
     * | trim | 1 | character from the beginning of global  | myGlobal |
     * | trim | 3 | characters from the beginning of global | myGlobal |
     * </pre>
     *
     * @param characters - integer number of characters to trim
     * @param global     - global to trim. trimmed string is saved in same global
     */
    public void trimCharactersFromBeginningOfGlobal(int characters, String global) {
        String toTrim = utils.getGlobal(global);
        toTrim = toTrim.substring(characters);
        utils.setGlobal(global, toTrim);
    }

    public void trimCharacterFromBeginningOfGlobal(int characters, String global) {
        trimCharactersFromBeginningOfGlobal(characters, global);
    }

    /**
     * Used to trim a specified number of characters from the end of the global. Useful if
     * the data you need within global doesn't end at the end of a word (i.e. an invoice
     * number that is formatted 12345-PLAY, you need 12345 for later tests). Can be used singular or
     * plural. <b>Usage:</b>
     * <pre>
     * | trim | 1 | character from the end of global | myGlobal |
     * | trim | 5 | characters from the end of global | myGlobal |
     * </pre>
     *
     * @param characters - integer number of characters to trim
     * @param global     - global to trim. Trimmed string is saved in same global
     */
    public void trimCharactersFromEndOfGlobal(int characters, String global) {
        String toTrim = utils.getGlobal(global);
        toTrim = toTrim.substring(0, toTrim.length() - characters);
        utils.setGlobal(global, toTrim);
    }

    public void trimCharacterFromEndOfGlobal(int characters, String global) {
        trimCharactersFromEndOfGlobal(characters, global);
    }

    /**
     * Used to concatenate two strings together and store the resulting string in a specified
     * global. <b>Note:</b> there is <b>no space</b> between string 1 and string 2 in the final
     * string. Strings are parsed for global before they are concatenated.  <b>Usage:</b>
     * <pre>
     * | cat | race | with | way | and store in global | myGlobal |
     * </pre>
     * This example results in the final string being "raceway" (less quotes)
     *
     * @param c1     - first string
     * @param c2     - second string - will be tacked onto the end of the first string
     * @param global - global in which concatenated string will be stored.
     */
    public void catWithAndStoreInGlobal(String c1, String c2, String global) {
        c1 = parse(c1).trim();
        c2 = parse(c2).trim();
        c1 = c1 + c2;
        utils.setGlobal(global, c1);
    }

    /**
     * Used to concatenate two strings together and store the resulting string in a specified
     * global. <b>Note:</b> there <b>is</b> a space between string 1 and string 2 in the final
     * string. Strings are parsed for global before they are concatenated.  <b>Usage:</b>
     * <pre>
     * | cat | this string | with | that string | and store in global | myGlobal | with space |
     * </pre>
     * This example results in the final string being "this string that string" (less quotes)
     *
     * @param c1     - first string
     * @param c2     - second string - will be tacked onto the end of the first string
     * @param global - global in which concatenated string will be stored.
     */
    public void catWithAndStoreInGlobalWithSpace(String c1, String c2, String global) {
        c1 = parse(c1).trim();
        c2 = parse(c2).trim();
        c1 = c1 + " " + c2;
        utils.setGlobal(global, c1);
    }

    /**
     * Used to store the length of a string in a global. Useful for measuring strings to know if it's
     * length is expected. String is parsed for global before processing. <b>Usage:</b>
     * <pre>
     * | store length of global | myString | in global | myGlobal |
     * </pre>
     *
     * @param toGet  - the global to get the length of
     * @param global - global to store the length of specified within.
     */
    public void storeLengthOfGlobalInGlobal(String toGet, String global) {
        toGet = utils.getGlobal(toGet);
        utils.setGlobal(global, "" + toGet.trim().length());
    }

    /**
     * Used to remove a character or string of characters from an already stored global.  Useful if
     * your test captures a string (i.e. an account number), but the data you need is a substring of
     * that string.  <b>Usage:</b>
     * <pre>
     * | remove | Account# | from global | myGlobal |
     * </pre>
     *
     * @param character - string of characters to remove from the global
     * @param global    - global to remove characters from.
     */
    public void removeFromGlobal(String character, String global) {
        String toRemove = utils.getGlobal(global);
        toRemove = toRemove.replace(character, "");
        utils.setGlobal(global, toRemove);
    }

    /**
     * Used to add a string to the beginning of a stored global. <b>Usage:</b>
     * <pre>
     * | add prefix | acct# | to global | myGlobal |
     * </pre>
     *
     * @param prefix - String of characters to add to the global
     * @param global - global to add characters to
     */
    public void addPrefixToGlobal(String prefix, String global) {
        prefix = parse(prefix);
        utils.setGlobal(global, prefix + utils.getGlobal(global));
    }

    /**
     * Used to add a string and a space to the beginning of a stored global. <b>Usage:</b>
     * <pre>
     * | add prefix | acct# | to global | myGlobal | with space |
     * </pre>
     *
     * @param prefix - String of characters to add to the global
     * @param global - global to add the characters to
     */
    public void addPrefixToGlobalWithSpace(String prefix, String global) {
        prefix = parse(prefix);
        utils.setGlobal(global, prefix + " " + utils.getGlobal(global));
    }

    /**
     * Used to add a suffix to the end of a stored global. <b>Usage:</b>
     * <pre>
     * | add suffix | last name | to global | myGlobal |
     * </pre>
     *
     * @param suffix - String of characters to add to the global
     * @param global - global to add characters to
     */
    public void addSuffixToGlobal(String suffix, String global) {
        suffix = parse(suffix);
        utils.setGlobal(global, utils.getGlobal(global) + suffix);
    }

    /**
     * Used to add a space and a suffix to the end of a stored global. <b>Usage:</b>
     * <pre>
     * | add suffix | last name | to global | myGlobal | with space |
     * </pre>
     *
     * @param suffix - String of characters to add to the global
     * @param global - global to add characters to
     */
    public void addSuffixToGlobalWithSpace(String suffix, String global) {
        suffix = parse(suffix);
        utils.setGlobal(global, utils.getGlobal(global) + SPACE + suffix);
    }
}
