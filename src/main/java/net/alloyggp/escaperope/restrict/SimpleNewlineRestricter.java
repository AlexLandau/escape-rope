package net.alloyggp.escaperope.restrict;

//Just prevents newlines
public class SimpleNewlineRestricter implements Restricter {
    private final char escapeCharacter;

    private SimpleNewlineRestricter(char escapeCharacter) {
        if (escapeCharacter == '\r' || escapeCharacter == '\n') {
            throw new IllegalArgumentException("Can't have a newline character as the escape character");
        }
        this.escapeCharacter = escapeCharacter;
    }

    @Override
    public String restrict(String unrestricted) {
        if (unrestricted == null) {
            return null;
        }
        //TODO: The string concat is ugly here
        return unrestricted.replace(escapeCharacter + "", escapeCharacter + "" + escapeCharacter)
        .replace("\n", escapeCharacter + "n")
        .replace("\r", escapeCharacter + "r");
    }

    @Override
    public String unrestrict(String restricted) {
        if (restricted == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < restricted.length(); /* incremented manually */) {
            int codePoint = restricted.codePointAt(i);
            if (codePoint == escapeCharacter) {
                if (i + 1 >= restricted.length()) {
                    // We don't expect this to be created by us, but "handle" it if assertions are off
                    // TODO: Should we allow people to turn on a strict mode?
                    assert false : "Invalid input " + restricted + ": Ends with unescaped escape character";
                    // Just treat it as the escape character
                    sb.append(escapeCharacter);
                }
                int nextCodePoint = restricted.codePointAt(i + 1);
                i++;
                if (nextCodePoint == 'n') {
                    sb.append('\n');
                } else if (nextCodePoint == 'r') {
                    sb.append('\r');
                } else if (nextCodePoint == escapeCharacter) {
                    sb.append(escapeCharacter);
                } else {
                    // We don't expect this to be created by us, but "handle" it if assertions are off
                    assert false : "Was expecting n, r, or " + escapeCharacter + " after " + escapeCharacter;
                    // Interpret as the character itself (too bad if you had \t for tab...)
                    sb.appendCodePoint(nextCodePoint);
                }
            } else {
                sb.appendCodePoint(codePoint);
            }
            i += Character.charCount(codePoint);
        }
        return sb.toString();
    }

    public static Restricter createDefault() {
        return new SimpleNewlineRestricter('\\');
    }

    public static Restricter createWithEscapeChar(char escapeCharacter) {
        return new SimpleNewlineRestricter(escapeCharacter);
    }
}
