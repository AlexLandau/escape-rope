package net.alloyggp.escaperope;

public class UnescapeControlResult implements UnescapeResult {
    private final int controlCharacter;

    private UnescapeControlResult(int controlCharacter) {
        this.controlCharacter = controlCharacter;
    }

    public static UnescapeResult create(int controlCharacter) {
        return new UnescapeControlResult(controlCharacter);
    }

    @Override
    public boolean isControlCharacter() {
        return true;
    }

    @Override
    public int getControlCharacter() {
        return controlCharacter;
    }

    @Override
    public String getNonControlText() {
        throw new UnsupportedOperationException("Tried to call getNonControlText() on a control character result");
    }
}
