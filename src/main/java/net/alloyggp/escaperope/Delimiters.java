package net.alloyggp.escaperope;

public class Delimiters {
    private Delimiters() {
        //Not instantiable
    }

    public static Delimiter getCsvLineDelimiter(NullBehavior nullBehavior) {
        return CsvLineDelimiter.create(nullBehavior);
    }

    public static Delimiter getEscapeCharDelimiterConvertingNulls(int delimiterChar, int escapeChar) {
        return EscapeCharDelimiter.createConvertingNulls(delimiterChar, escapeChar);
    }

    public static RopeDelimiter getPrototypeRopeDelimiter() {
        return PrototypeRopeDelimiter.create();
    }
}
