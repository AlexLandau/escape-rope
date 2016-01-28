package net.alloyggp.escaperope;

import net.alloyggp.escaperope.rope.Rope;

public interface RopeDelimiter {
    String delimit(Rope inputs);
    Rope undelimit(String input);
}
