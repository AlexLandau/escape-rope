package net.alloyggp.escaperope.rope;

import java.util.List;

/*
 * A Rope is a String or a list of Ropes. In the future, I hope to
 * make this into a powerful extension of Delimiters' capabilities...
 * if I can get around to it.
 *
 * (I may also consider including the possibility of letting a Rope
 * be a map from Strings to Ropes, to let this more closely resemble
 * simple JSON-like formats.)
 *
 * TODO: Add visitor functionality
 */
public interface Rope {
    boolean isList();
    boolean isString();
    String getString();
    List<Rope> getList();
}
