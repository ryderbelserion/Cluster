package com.ryderbelserion.cluster.utils;

import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class StringUtils {

    /**
     * Loops through a string-list and returns a string builder
     *
     * @param list to convert
     * @return the string-builder
     */
    public static String convertList(List<String> list) {
        StringBuilder message = new StringBuilder();

        for (String line : list) {
            message.append(line).append("\n");
        }

        return message.toString();
    }

    public static @Nullable String unescapeJava(@Nullable String value) {
        if (value == null) {
            return null;
        }

        try {
            StringWriter writer = new StringWriter(value.length());
            unescapeJava(writer, value);
            return writer.toString();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @SuppressWarnings({"ReassignedVariable", "DataFlowIssue", "RedundantLabeledSwitchRuleCodeBlock"})
    private static void unescapeJava(@Nullable Writer out, @Nullable String value) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }

        if (value == null) {
            return;
        }

        int sz = value.length();
        StringBuilder unicode = new StringBuilder(4);
        boolean hadSlash = false;
        boolean inUnicode = false;

        for (int i = 0; i < sz; i++) {
            char ch = value.charAt(i);

            if (inUnicode) {
                // if in unicode, then we're reading unicode
                // values in somehow
                unicode.append(ch);

                if (unicode.length() == 4) {
                    // unicode now contains the four hex digits
                    // which represents our unicode character
                    try {
                        int pair = Integer.parseInt(unicode.toString(), 16);
                        out.write((char) pair);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;
                    } catch (NumberFormatException nfe) {
                        throw new RuntimeException("Unable to parse unicode value: " + unicode, nfe);
                    }
                }

                continue;
            }

            if (hadSlash) {
                // handle an escaped value
                hadSlash = false;

                switch (ch) {
                    case '\\' -> out.write('\\');
                    case '\'' -> out.write('\'');
                    case '\"' -> out.write('"');
                    case 'r' -> out.write('\r');
                    case 'f' -> out.write('\f');
                    case 't' -> out.write('\t');
                    case 'n' -> out.write('\n');
                    case 'b' -> out.write('\b');
                    case 'u' -> {
                        // uh-oh, we're in unicode country....
                        inUnicode = true;
                    }

                    default -> out.write(ch);
                }

                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }

            out.write(ch);
        }

        if (hadSlash) {
            // then we're in the weird case of a \ at the end of the
            // string, let's output it anyway.
            out.write('\\');
        }
    }
}