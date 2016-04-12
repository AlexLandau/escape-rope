package net.alloyggp.escaperope.rope.ropify;

import net.alloyggp.escaperope.rope.Rope;

/**
 * A utility class for writing custom Weaver implementations easily.
 * Implementers should override the {@link #addToList(Object, RopeBuilder)}
 * and {@link #fromRope(RopeList)} methods rather than the Weaver's
 * original {@link #toRope(Object)} and {@link #fromRope(Rope)}
 * methods. These expose {@link RopeBuilder} and {@link RopeList}
 * classes that aid the process of creating and reading ropes,
 * respectively.
 */
public abstract class ListWeaver<T> implements Weaver<T> {
    @Override
    public final Rope toRope(T object) {
        RopeBuilder list = RopeBuilder.create();
        addToList(object, list);
        return list.toRope();
    }

    protected abstract void addToList(T object, RopeBuilder list);

    @Override
    public final T fromRope(Rope rope) {
        if (!rope.isList()) {
            throw new IllegalArgumentException("Input must be a list-type Rope");
        }
        return fromRope(RopeList.create(rope));
    }

    protected abstract T fromRope(RopeList list);
}
