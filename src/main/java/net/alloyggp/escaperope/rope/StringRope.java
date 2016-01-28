package net.alloyggp.escaperope.rope;

import java.util.List;

import javax.annotation.concurrent.Immutable;

@Immutable
public class StringRope implements Rope {
    private final String string;

    private StringRope(String string) {
        this.string = string;
    }

    public static Rope create(String string) {
        return new StringRope(string);
    }

    @Override
    public boolean isList() {
        return false;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public String asString() {
        return string;
    }

    @Override
    public List<Rope> asList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((string == null) ? 0 : string.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        StringRope other = (StringRope) obj;
        if (string == null) {
            if (other.string != null) {
                return false;
            }
        } else if (!string.equals(other.string)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return string;
    }
}
