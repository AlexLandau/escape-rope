package net.alloyggp.escaperope.rope.ropify;

import net.alloyggp.escaperope.rope.Rope;

public abstract class ListRopeWeaver<T> implements RopeWeaver<T> {
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
