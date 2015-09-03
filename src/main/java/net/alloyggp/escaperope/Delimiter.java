package net.alloyggp.escaperope;

import java.util.List;

public interface Delimiter {
    String delimit(Iterable<String> inputs);
    List<String> undelimit(String input);
}
