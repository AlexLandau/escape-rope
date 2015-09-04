package net.alloyggp.escaperope;

public class Delimiters {
    private Delimiters() {
        //Not instantiable
    }

    public static Delimiter getCsvLineDelimiter() {
        return CsvLineDelimiter.create();
    }

    public static Delimiter getEscapeCharDelimiter(int delimiterChar, int escapeChar) {
        return EscapeCharDelimiter.create(delimiterChar, escapeChar);
    }
}
