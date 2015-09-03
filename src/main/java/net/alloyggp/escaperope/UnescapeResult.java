package net.alloyggp.escaperope;

public interface UnescapeResult {
    boolean isControlCharacter();
    int getControlCharacter();
    String getNonControlText();
}
