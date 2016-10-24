package net.alloyggp.escaperope;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;
import net.alloyggp.escaperope.rope.ropify.RopeBuilder;

public class JsonArrayRopeDelimiter implements RopeDelimiter {
    private final static JsonArrayRopeDelimiter INSTANCE = new JsonArrayRopeDelimiter();

    private JsonArrayRopeDelimiter() {
        // Use create() instead
    }

    // TODO: Someday, if people are using this, make this implementation
    // non-recursive to avoid stack overflows
    @Override
    public String delimit(Rope rope) {
        StringBuilder sb = new StringBuilder();
        addRope(rope, sb);
        return sb.toString();
    }

    private void addRope(Rope rope, StringBuilder sb) {
        if (rope.isList()) {
            sb.append('[');
            List<Rope> list = rope.asList();
            if (list.size() > 0) {
                addRope(list.get(0), sb);
                for (int i = 1; i < list.size(); i++) {
                    sb.append(',');
                    addRope(list.get(i), sb);
                }
            }
            sb.append(']');
        } else {
            String string = rope.asString();
            if (string == null) {
                sb.append("null");
            } else {
                String escaped = string
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"");
                sb.append('"');
                sb.append(escaped);
                sb.append('"');
            }
        }
    }

    @Override
    public Rope undelimit(String undelimited) {
        StringReader reader = new StringReader(undelimited);
        try {
            return getNextRope(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Rope getNextRope(StringReader reader) throws IOException {
        int c = reader.read();
        if (c == '"') {
            String string = readAndUnescapeString(reader);
            return StringRope.create(string);
        } else if (c == 'n') {
            char[] ullChars = new char[3];
            reader.read(ullChars);
            if (ullChars[0] != 'u' || ullChars[1] != 'l' || ullChars[2] != 'l') {
                throw fail();
            }
            return StringRope.create(null);
        } else if (c == '[') {
            //Handle this inline to reduce stack bloat
            RopeBuilder builder = RopeBuilder.create();
            while (true) {
                int next = peek(reader);
                if (next == ']') {
                    //Skip it
                    reader.read();
                    return builder.toRope();
                } else if (next == ',') {
                    //Skip it
                    reader.read();
                } else if (next != '"' && next != '[' && next != 'n') {
                    throw fail();
                }
                builder.add(getNextRope(reader));
            }
        } else {
            throw fail();
        }
    }

    private int peek(StringReader reader) throws IOException {
        reader.mark(1);
        int value = reader.read();
        reader.reset();
        return value;
    }

    // Assumes we have just read the opening quotation mark.
    private String readAndUnescapeString(StringReader reader) throws IOException {
        boolean escaping = false;
        StringBuilder sb = new StringBuilder();
        while (true) {
            int value = reader.read();
            if (value == -1) {
                throw fail();
            } else if (escaping) {
                sb.append((char) value);
                escaping = false;
            } else if (value == '\\') {
                escaping = true;
            } else if (value == '"') {
                return sb.toString();
            } else {
                sb.append((char) value);
            }
        }
    }

    private RuntimeException fail() {
        throw new IllegalArgumentException("Improperly formatted string. Did it originally come from a JsonArrayRopeDelimiter?");
    }

    public static JsonArrayRopeDelimiter create() {
        return INSTANCE;
    }

}
