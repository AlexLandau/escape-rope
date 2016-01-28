package net.alloyggp.escaperope.rope.ropify;

import java.util.Iterator;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import net.alloyggp.escaperope.rope.Rope;

/**
 * A utility class for reading transformed contents of a ListRope.
 * The RopeList wraps the Rope and provides useful getters for
 * applying {@link RopeWeaver}s and core weavers.
 */
@Immutable
public class RopeList implements Iterable<Rope> {
    private final List<Rope> list;

    private RopeList(List<Rope> list) {
        this.list = list;
    }

    public static RopeList create(Rope listRope) {
        if (!listRope.isList()) {
            throw new IllegalArgumentException("Input must be a list-type Rope");
        }
        //This is safe because we know the list we're given by ListRope is immutable
        return new RopeList(listRope.asList());
    }

    public int size() {
        return list.size();
    }

    //TODO: More compact implementation?
    public boolean getBoolean(int i) {
        return Boolean.parseBoolean(list.get(i).asString());
    }

    public <T> T get(int i, RopeWeaver<T> weaver) {
        return weaver.fromRope(list.get(i));
    }

    public int getInt(int i) {
        return Integer.parseInt(list.get(i).asString());
    }

    public long getLong(int i) {
        return Long.parseLong(list.get(i).asString());
    }

    public String getString(int i) {
        return list.get(i).asString();
    }

    @Override
    public Iterator<Rope> iterator() {
        return list.iterator();
    }

}
