package net.alloyggp.escaperope;

public class DelimiterSignatures {
    /**
     * Parses the signature indicated by the prefix of the string.
     */
    public static DelimiterSignature parse(String signedString) {
        if (signedString.length() < 2) {
            throw new IllegalArgumentException("Signed strings must be at least 2 characters long.");
        }

        throw new IllegalArgumentException("Could not parse a signature for the string: " + signedString);
    }
}
