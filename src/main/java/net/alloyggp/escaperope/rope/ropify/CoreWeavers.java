package net.alloyggp.escaperope.rope.ropify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.alloyggp.escaperope.rope.ListRope;
import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

//TODO: Fill this out with additional core weavers.
//TODO: Additional boxed primitive types
//TODO: arrayOf
//TODO: mapOf
//TODO: sortedSetOf? sortedMapOf?
//TODO: optional?
public class CoreWeavers {

    //TODO: Handle null values
    public static final Weaver<Integer> INTEGER = new Weaver<Integer>() {
        @Override
        public Rope toRope(Integer i) {
            return StringRope.create(i.toString());
        }

        @Override
        public Integer fromRope(Rope rope) {
            return Integer.valueOf(rope.asString());
        }
    };

    public static final Weaver<Long> LONG = new Weaver<Long>() {
        @Override
        public Rope toRope(Long l) {
            return StringRope.create(l.toString());
        }

        @Override
        public Long fromRope(Rope rope) {
            return Long.valueOf(rope.asString());
        }
    };

    public static final Weaver<Double> DOUBLE = new Weaver<Double>() {
        @Override
        public Rope toRope(Double d) {
            return StringRope.create(d.toString());
        }

        @Override
        public Double fromRope(Rope rope) {
            return Double.valueOf(rope.asString());
        }
    };

    public static final Weaver<String> STRING = new Weaver<String>() {
        @Override
        public Rope toRope(String string) {
            return StringRope.create(string);
        }

        @Override
        public String fromRope(Rope rope) {
            return rope.asString();
        }
    };

    public static final Weaver<int[]> INT_ARRAY = new ListWeaver<int[]>() {
        @Override
        protected void addToList(int[] array, RopeBuilder list) {
            for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
        }

        @Override
        protected int[] fromRope(RopeList list) {
            int[] array = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.getInt(i);
            }
            return array;
        }
    };

    //TODO: Should this have wildcards somewhere?
    public static <T> Weaver<List<T>> listOf(final Weaver<T> innerTypeWeaver) {
        return new ListWeaver<List<T>>() {
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

    public static <T> Weaver<Set<T>> setOf(final Weaver<T> innerTypeWeaver) {
        return new ListWeaver<Set<T>>() {
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

    public static <K, V> Weaver<Map<K, V>> mapOf(final Weaver<K> keyTypeWeaver,
            final Weaver<V> valueTypeWeaver) {
        return new ListWeaver<Map<K, V>>() {
            @Override
            protected void addToList(Map<K, V> map, RopeBuilder list) {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    list.add(keyTypeWeaver.toRope(entry.getKey()));
                    list.add(valueTypeWeaver.toRope(entry.getValue()));
                }
            }

            @Override
            public Map<K, V> fromRope(RopeList ropes) {
                Map<K, V> map = new LinkedHashMap<>(ropes.size() / 2);
                for (int i = 0; i < ropes.size(); i += 2) {
                    K key = ropes.get(i, keyTypeWeaver);
                    V value = ropes.get(i + 1, valueTypeWeaver);
                    map.put(key, value);
                }
                return map;
            }
        };
    }

    //TODO: Singleton?
    public static Weaver<List<Integer>> listOfIntegers() {
        return listOf(INTEGER);
    }

    public static <E extends Enum<E>> Weaver<E> enumOf(final Class<E> enumClass) {
        return new Weaver<E>() {
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

    public static <T> Weaver<T> singletonOf(final T singleton) {
        return new Weaver<T>() {
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

    public static <T> Weaver<T> nullable(final Weaver<T> weaver) {
        return new Weaver<T>() {
            @Override
            public Rope toRope(T object) {
                if (object == null) {
                    return StringRope.create("");
                } else {
                    return ListRope.create(Arrays.asList(weaver.toRope(object)));
                }
            }

            @Override
            public T fromRope(Rope rope) {
                if (rope.isList()) {
                    return weaver.fromRope(rope.asList().get(0));
                } else {
                    return null;
                }
            }
        };
    }

}
