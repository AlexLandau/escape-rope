package net.alloyggp.escaperope;

/**
 * Restricts a String into a narrower character set and reverses the
 * conversion.
 */
//Note: In some cases the restricted character set may be case-insensitive.
//In this case we should reverse the conversion correctly even if the
//cases have been arbitrarily switched on a character-by-character basis.
public interface Restricter {
    String restrict(String input);
    String unrestrict(String restrictedInput);
}
