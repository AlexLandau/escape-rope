package net.alloyggp.escaperope;

import java.util.function.Function;

public enum NullBehavior {
    KEEP(string -> string),
    MAKE_EMPTY(string -> (string != null) ? string : ""),
    THROW(string -> {throw new IllegalArgumentException("Cannot have a null input");}),
    ;
    private final Function<String, String> applyFunction;

    private NullBehavior(Function<String, String> applyFunction) {
        this.applyFunction = applyFunction;
    }

    //Keep out of the API
    /*package-private*/ String apply(String input) {
        return applyFunction.apply(input);
    }
}
