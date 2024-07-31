package dev.trigam.kuub.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    Pattern regex;

    public Regex ( String pattern ) {
        this.regex = Pattern.compile(pattern);
    }

    public boolean hasMatch ( String input ) { return this.regex.matcher(input).find(); }
    public static boolean hasMatch( String pattern, String input ) {
        Pattern regex = Pattern.compile(pattern);
        Matcher regexMatcher = regex.matcher(input);
        return regexMatcher.find();
    }

}
