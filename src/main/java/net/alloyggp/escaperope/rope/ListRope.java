package net.alloyggp.escaperope.rope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public class ListRope implements Rope {
    private final List<Rope> list;

    //TODO: Immutify the list when needed
    private ListRope(List<Rope> list) {
        this.list = list;
    }

    public static Rope create(List<Rope> ropes) {
        //TODO: Remove this when unnecessary...
        return new ListRope(Collections.unmodifiableList(new ArrayList<>(ropes)));
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
    public String asString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Rope> asList() {
        return list;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ListRope other = (ListRope) obj;
        if (list == null) {
            if (other.list != null) {
                return false;
            }
        } else if (!list.equals(other.list)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
