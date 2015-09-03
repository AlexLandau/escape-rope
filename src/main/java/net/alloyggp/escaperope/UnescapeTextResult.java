package net.alloyggp.escaperope;

public class UnescapeTextResult implements UnescapeResult {
    private final String text;

    private UnescapeTextResult(String text) {
        this.text = text;
    }

    public static UnescapeResult create(String text) {
        return new UnescapeTextResult(text);
    }

    @Override
    public boolean isControlCharacter() {
        return false;
    }

    @Override
    public int getControlCharacter() {
        throw new UnsupportedOperationException("Tried to call getControlCharacter() on a non-control-character result");
    }

    @Override
    public String getNonControlText() {
        return text;
    }

}
