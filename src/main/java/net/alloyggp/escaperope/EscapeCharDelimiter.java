package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Note that the result ends with the delimiter! This is required to distinguish
//between an empty list and a singleton list containing the empty string.
public class EscapeCharDelimiter implements Delimiter {
    private final int delimiterChar;
    private final Escaper escaper;

    public EscapeCharDelimiter(int delimiterChar, Escaper escaper) {
        this.delimiterChar = delimiterChar;
        this.escaper = escaper;
    }

    public static Delimiter create(int delimiterChar, int escapeChar) {
        if (delimiterChar == escapeChar) {
            throw new IllegalArgumentException("The delimiter char and escape char should not be the same");
        }
        Escaper escaper = EscapeCharEscaper.create(escapeChar, Collections.singleton(delimiterChar));
        return new EscapeCharDelimiter(delimiterChar, escaper);
    }

    @Override
    public String delimit(Iterable<String> inputs) {
        StringBuilder sb = new StringBuilder();
        for (String input : inputs) {
            sb.append(escaper.escape(input));
            sb.appendCodePoint(delimiterChar);
        }
        return sb.toString();
    }

    @Override
    public List<String> undelimit(String input) {
        List<UnescapeResult> resultsWithDelimiters = escaper.unescape(input);
        List<String> textResults = new ArrayList<>();
        if (resultsWithDelimiters.size() == 0) {
            return textResults;
        }
        if (resultsWithDelimiters.get(0).isControlCharacter()) {
            textResults.add("");
        }
        boolean lastWasControlChar = false;
        for (UnescapeResult result : resultsWithDelimiters) {
            if (lastWasControlChar && result.isControlCharacter()) {
                textResults.add("");
            } else if (!result.isControlCharacter()) {
                textResults.add(result.getNonControlText());
            }
            lastWasControlChar = result.isControlCharacter();
        }
        return textResults;
    }

}
