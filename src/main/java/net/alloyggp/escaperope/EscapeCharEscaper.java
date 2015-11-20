package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

//This probably has lots of room for performance improvements.
public class EscapeCharEscaper implements Escaper {
    private final int escapeChar;
    private final Set<Integer> controlChars;
    private final Set<Integer> allCharsToEscape;
    private final NullBehavior nullBehavior;

    private EscapeCharEscaper(int escapeChar, Set<Integer> controlChars, Set<Integer> allCharsToEscape,
            NullBehavior nullBehavior) {
        if (controlChars.contains(escapeChar)) {
            throw new IllegalArgumentException("The escape character should not also be a control character.");
        }
        if (!allCharsToEscape.contains(escapeChar)) {
            throw new IllegalArgumentException("The escape character should itself be escaped.");
        }
        if (nullBehavior == NullBehavior.KEEP) {
            throw new IllegalArgumentException("The EscapeCharEscaper can't currently keep null strings.");
        }
        this.escapeChar = escapeChar;
        this.controlChars = controlChars;
        this.allCharsToEscape = allCharsToEscape;
        this.nullBehavior = nullBehavior;
    }

    //TODO: Add version with char/Character inputs
    public static Escaper createConvertingNulls(int escapeChar, Set<Integer> controlChars) {
        Set<Integer> allCharsToEscape = new HashSet<Integer>(controlChars);
        allCharsToEscape.add(escapeChar);
        return new EscapeCharEscaper(escapeChar, controlChars, allCharsToEscape, NullBehavior.MAKE_EMPTY);
    }

    @Override
    public String escape(String input) {
        if (input == null) {
            return "";
        }
        return input.codePoints()
            .flatMap(c -> {
                if (allCharsToEscape.contains(c)) {
                    return IntStream.of(escapeChar, c);
                } else {
                    return IntStream.of(c);
                }
            }).collect(() -> new StringBuilder(),
                    StringBuilder::appendCodePoint,
                    (sb1, sb2) -> sb1.append(sb2))
            .toString();
    }

    @Override
    public List<UnescapeResult> unescape(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        List<UnescapeResult> results = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); /*i is incremented explicitly*/) {
            int codePoint = input.codePointAt(i);
            if (codePoint == escapeChar) {
                i += Character.charCount(codePoint);
                codePoint = input.codePointAt(i);
                sb.appendCodePoint(codePoint);
                i += Character.charCount(codePoint);
            } else if (controlChars.contains(codePoint)) {
                //End ...
                if (sb.length() != 0) {
                    results.add(UnescapeTextResult.create(sb.toString()));
                    sb = new StringBuilder();
                }
                results.add(UnescapeControlResult.create(codePoint));
                i += Character.charCount(codePoint);
            } else {
                sb.appendCodePoint(codePoint);
                i += Character.charCount(codePoint);
            }
        }
        if (sb.length() != 0) {
            results.add(UnescapeTextResult.create(sb.toString()));
        }
        return results;
    }

}
