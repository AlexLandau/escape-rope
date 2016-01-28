package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.List;

//This attempts to comply with a subset of RFC 4180. Note that this does NOT
//mean that undelimit() will work with arbitrary valid CSV outputs
//from sources other than this class.
public class CsvLineDelimiter implements Delimiter {
    private final Escaper escaper;

    private CsvLineDelimiter(Escaper escaper) {
        this.escaper = escaper;
    }

    public static Delimiter create(NullBehavior behavior) {
        return new CsvLineDelimiter(CsvFieldEscaper.create(behavior));
    }

    @Override
    public String delimit(Iterable<String> inputs) {
        if (inputs == null) {
            throw new NullPointerException();
        }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String input : inputs) {
            if (!isFirst) {
                sb.append(",");
            }
            sb.append(escaper.escape(input));
            isFirst = false;
        }
        return sb.toString();
    }

    @Override
    public List<String> undelimit(String input) {
        if (input == null) {
            throw new NullPointerException();
        }
        List<String> strings = new ArrayList<>();
        for (UnescapeResult result : escaper.unescape(input)) {
            if (!result.isControlCharacter()) {
                strings.add(result.getNonControlText());
            }
        }
        return strings;
    }

}
