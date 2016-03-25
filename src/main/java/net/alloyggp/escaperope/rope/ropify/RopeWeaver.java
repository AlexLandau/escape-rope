package net.alloyggp.escaperope.rope.ropify;

import net.alloyggp.escaperope.rope.Rope;

/**
 * A Weaver is an object (assumed to be stateless or immutable) that
 * specifies how to translate between a particular object type and
 * a Rope.
 *
 * <p>See {@link CoreWeavers} for Weavers for commonly-used types.
 */
//TODO: Rename to just Weaver
public interface RopeWeaver<T> {
    Rope toRope(T object);
    T fromRope(Rope rope);
}
