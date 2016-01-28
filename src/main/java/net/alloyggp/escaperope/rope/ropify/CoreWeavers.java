package net.alloyggp.escaperope.rope.ropify;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

//TODO: Fill this out with additional core weavers.
public class CoreWeavers {

    public static final RopeWeaver<Integer> INTEGER = new RopeWeaver<Integer>() {
        @Override
        public Rope toRope(Integer i) {
            return StringRope.create(i.toString());
        }

        @Override
        public Integer fromRope(Rope rope) {
            return Integer.valueOf(rope.asString());
        }
    };

    //TODO: Should this have wildcards somewhere?
    public static <T> RopeWeaver<List<T>> listOf(final RopeWeaver<T> innerTypeWeaver) {
        return new ListRopeWeaver<List<T>>() {
            @Override
            protected void addToList(List<T> objects, RopeBuilder list) {
                for (T object : objects) {
                    list.add(innerTypeWeaver.toRope(object));
                }
            }

            @Override
            public List<T> fromRope(RopeList ropes) {
                List<T> objects = new ArrayList<>(ropes.size());
                for (Rope rope : ropes) {
                    objects.add(innerTypeWeaver.fromRope(rope));
                }
                return objects;
            }
        };
    }

    public static <T> RopeWeaver<Set<T>> setOf(final RopeWeaver<T> innerTypeWeaver) {
        return new ListRopeWeaver<Set<T>>() {
            @Override
            protected void addToList(Set<T> objects, RopeBuilder list) {
                for (T object : objects) {
                    list.add(innerTypeWeaver.toRope(object));
                }
            }

            @Override
            public Set<T> fromRope(RopeList ropes) {
                Set<T> objects = new LinkedHashSet<>(ropes.size());
                for (Rope rope : ropes) {
                    objects.add(innerTypeWeaver.fromRope(rope));
                }
                return objects;
            }


        };
    }

    //TODO: Singleton?
    public static RopeWeaver<List<Integer>> listOfIntegers() {
        return listOf(INTEGER);
    }

    public static <E extends Enum<E>> RopeWeaver<E> enumOf(final Class<E> enumClass) {
        return new RopeWeaver<E>() {
            @Override
            public Rope toRope(E object) {
                return StringRope.create(object.name());
            }

            @Override
            public E fromRope(Rope rope) {
                return Enum.valueOf(enumClass, rope.asString());
            }
        };
    }

    public static <T> RopeWeaver<T> singletonOf(final T singleton) {
        return new RopeWeaver<T>() {
            @Override
            public Rope toRope(T object) {
                return StringRope.create("");
            }

            @Override
            public T fromRope(Rope rope) {
                return singleton;
            }
        };
    }

}
