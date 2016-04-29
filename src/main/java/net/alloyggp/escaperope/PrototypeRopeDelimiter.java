package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.List;

import net.alloyggp.escaperope.rope.ListRope;
import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

/**
 * This uses a not-very-smart approach to choosing which delimiters to
 * use, hence "prototype". This will probably be abandoned or replaced
 * at some point. Also, there are no guarantees of consistency of
 * behavior.
 *
 * Also, this should use signed delimiters, which it does not yet.
 *
 * That said, this gives a quick and fairly simple example of how nested
 * delimiting of ropes can work.
 *
 * TODO: Strictly speaking, this forces the null behavior to "convert",
 * which is probably not a good thing for generality.
 */
public class PrototypeRopeDelimiter implements RopeDelimiter {
    //Chosen to fall into the range of characters that are uncommonly
    //used, commonly supported in displays, and not likely to be
    //interpreted as instructions of some sort.
    private static final int FIRST_DELIMITER_CHAR = 0xb0;

    public static PrototypeRopeDelimiter create() {
        return new PrototypeRopeDelimiter();
    }

    @Override
    public String delimit(Rope inputRope) {
        return delimitInner(inputRope, FIRST_DELIMITER_CHAR);
    }

    private String delimitInner(Rope inputRope, int specialChar) {
        List<String> strings = new ArrayList<>();
        if (inputRope.isString()) {
            if (inputRope.asString() == null) {
                strings.add("n");
            } else {
                strings.add("s");
                strings.add(inputRope.asString());
            }
        } else {
            strings.add("l");
            for (Rope rope : inputRope.asList()) {
                strings.add(delimitInner(rope, specialChar + 2));
            }
        }

        Delimiter delimiter = EscapeCharDelimiter.createConvertingNulls(specialChar, specialChar + 1);
        return delimiter.delimit(strings);
    }

    @Override
    public Rope undelimit(String input) {
        return undelimitInner(input, FIRST_DELIMITER_CHAR);
    }

    private Rope undelimitInner(String input, int specialChar) {
        Delimiter delimiter = EscapeCharDelimiter.createConvertingNulls(specialChar, specialChar + 1);
        List<String> strings = delimiter.undelimit(input);

        if (strings.get(0).equals("s")) {
            if (strings.size() != 2) {
                throw new IllegalArgumentException("Expected exactly one string to follow 's' indicating a string entry, but strings were: " + strings);
            }
            return StringRope.create(strings.get(1));
        } else if (strings.get(0).equals("n")) {
            if (strings.size() != 1) {
                throw new IllegalArgumentException("Expected 'n' indicating null to be only entry in list, but strings were: " + strings);
            }
            return StringRope.create(null);
        } else {
            if (!strings.get(0).equals("l")) {
                throw new IllegalArgumentException("Expected first character in delimited rope to be 's', 'n', or 'l', but strings were: " + strings);
            }
            List<Rope> ropes = new ArrayList<>(strings.size() - 1);
            for (String string : strings.subList(1, strings.size())) {
                ropes.add(undelimitInner(string, specialChar + 2));
            }
            return ListRope.create(ropes);
        }
    }

}
