package net.alloyggp.escaperope.rope.ropify;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import net.alloyggp.escaperope.rope.ListRope;
import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

/**
 * RopeBuilders are not thread-safe.
 */
@NotThreadSafe
public class RopeBuilder {
    private final List<Rope> list = new ArrayList<>();

    private RopeBuilder() {
        //Use a create() method instead
    }

    public static RopeBuilder create() {
        return new RopeBuilder();
    }

    public void add(Rope rope) {
        list.add(rope);
    }

    public <T> void add(T object, RopeWeaver<? super T> weaver) {
        list.add(weaver.toRope(object));
    }

    public void add(String string) {
        list.add(StringRope.create(string));
    }

    public List<Rope> toList() {
        return new ArrayList<>(list);
    }

    public Rope toRope() {
        return ListRope.create(list);
    }

    /**
     * Adds the string representation of the long value.
     */
    public void add(long longValue) {
        add(Long.toString(longValue));
    }

    public void add(boolean b) {
        add(Boolean.toString(b));
    }
}
