package net.alloyggp.escaperope.rope;

import java.util.List;

public class ListRope implements Rope {
    private final List<Rope> list;

    //TODO: Immutify the list when needed
    private ListRope(List<Rope> list) {
        this.list = list;
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public String getString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rope> getList() {
        return list;
    }

}
