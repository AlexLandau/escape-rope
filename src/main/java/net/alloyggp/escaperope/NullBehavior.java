package net.alloyggp.escaperope;

public enum NullBehavior {
    KEEP,
    MAKE_EMPTY,
    THROW,
    ;

    //Keep out of the API
    /*package-private*/ String apply(String input) {
//        return applyFunction.apply(input);
        if (this == KEEP) {
            return input;
        } else if (this == MAKE_EMPTY) {
            return (input != null) ? input : "";
        } else {
            if (input == null) {
                throw new IllegalArgumentException("Cannot have a null input");
            }
            return input;
        }
    }
}
