package net.alloyggp.escaperope;

import java.util.List;

public interface Escaper {
    String escape(String input);
    List<UnescapeResult> unescape(String input);
}
