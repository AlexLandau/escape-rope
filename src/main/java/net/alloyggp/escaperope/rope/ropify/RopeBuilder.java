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

    public <T> void add(T object, Weaver<? super T> weaver) {
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

    public void add(byte byteValue) {
        add(Byte.toString(byteValue));
    }

    public void add(short shortValue) {
        add(Short.toString(shortValue));
    }

    public void add(int intValue) {
        add(Integer.toString(intValue));
    }

    /**
     * Adds the string representation of the long value.
     */
    public void add(long longValue) {
        add(Long.toString(longValue));
    }

    public void add(boolean booleanValue) {
        add(Boolean.toString(booleanValue));
    }

    public void add(float floatValue) {
        add(Float.toString(floatValue));
    }

    public void add(double doubleValue) {
        add(Double.toString(doubleValue));
    }

    /**
     * Adds an entry with no contents. This can be used to
     * ensure that indices for later entries are maintained
     * if e.g. an element is only conditionally present.
     */
    public void addSpacer() {
        add("");
    }
}
