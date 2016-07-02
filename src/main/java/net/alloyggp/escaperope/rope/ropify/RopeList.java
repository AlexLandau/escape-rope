package net.alloyggp.escaperope.rope.ropify;

import java.util.Iterator;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import net.alloyggp.escaperope.rope.Rope;

/**
 * A utility class for reading transformed contents of a ListRope.
 * The RopeList wraps the Rope and provides useful getters for
 * applying {@link Weaver}s and core weavers.
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

    public <T> T get(int i, Weaver<T> weaver) {
        return weaver.fromRope(list.get(i));
    }

    public byte getByte(int i) {
        return Byte.parseByte(list.get(i).asString());
    }

    public short getShort(int i) {
        return Short.parseShort(list.get(i).asString());
    }

    public int getInt(int i) {
        return Integer.parseInt(list.get(i).asString());
    }

    public long getLong(int i) {
        return Long.parseLong(list.get(i).asString());
    }

    public float getFloat(int i) {
        return Float.parseFloat(list.get(i).asString());
    }

    public double getDouble(int i) {
        return Double.parseDouble(list.get(i).asString());
    }

    public String getString(int i) {
        return list.get(i).asString();
    }

    @Override
    public Iterator<Rope> iterator() {
        return list.iterator();
    }

    public <T> Iterable<T> map(final Weaver<T> innerWeaver) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                final Iterator<Rope> untransformedItr = RopeList.this.iterator();
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return untransformedItr.hasNext();
                    }

                    @Override
                    public T next() {
                        return innerWeaver.fromRope(untransformedItr.next());
                    }
                };
            }
        };
    }

    public Rope getRope(int i) {
        return list.get(i);
    }

}
