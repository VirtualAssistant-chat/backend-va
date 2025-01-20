package org.fundacionjala.virtualassistant.util.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSolver {

    public static String deleteTabAndLineBreak(String text) {
        return deleteMatch(text, "[\t\n]");
    }

    public static String deleteMatch(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }
}
