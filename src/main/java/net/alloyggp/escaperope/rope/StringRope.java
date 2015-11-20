package net.alloyggp.escaperope.rope;

import java.util.List;

public class StringRope implements Rope {
    private final String string;

    private StringRope(String string) {
        this.string = string;
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public List<Rope> getList() {
        throw new UnsupportedOperationException();
    }
}
