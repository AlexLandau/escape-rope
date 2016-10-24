package net.alloyggp.escaperope.rope.ropify;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

//TODO: Decide on exact semantics
//TODO: Consider a version with arbitrary predicates for determining delegation,
//instead of just "instanceof"
/*
 * One possibility is the first weaver that matches (i.e. in the exact order
 * weavers were added, and counting any supertype).
 *
 * Another is to look for an exact match first, then look up in the class
 * inheritance hierarchy. This might be more intuitive. (But what about
 * interfaces?)
 *
 * We currently use the first scheme, and furthermore the type that is written
 * is the type of the weaver, not the type of the object (if they differ).
 */
@Deprecated //Not yet semantically stable.
public class SubclassWeaver<T> extends ListWeaver<T> {
    private final Map<Class<? extends T>, Weaver<? extends T>> subclassWeavers;
    private final Map<Class<? extends T>, String> preferredIdentifiers;
    private final Map<String, Class<? extends T>> alternativeIdentifiers;

    private SubclassWeaver(Map<Class<? extends T>, Weaver<? extends T>> subclassWeavers,
            Map<Class<? extends T>, String> preferredIdentifiers,
            Map<String, Class<? extends T>> alternativeIdentifiers) {
        this.subclassWeavers = subclassWeavers;
        this.preferredIdentifiers = preferredIdentifiers;
        this.alternativeIdentifiers = alternativeIdentifiers;
    }

    public static <T> SubclassWeaverBuilder<T> builder(Class<T> clazz) {
        return new SubclassWeaverBuilder<T>(clazz);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" }) //Required to make the cast work
    @Override
    protected void addToList(T object, RopeBuilder list) {
        if (object == null) {
            throw new NullPointerException();
        }
        //TODO:  We could cache class-of-object to relevant subclass...
        for (Entry<Class<? extends T>, Weaver<? extends T>> entry : subclassWeavers.entrySet()) {
            if (entry.getKey().isInstance(object)) {
                list.add(preferredIdentifiers.get(entry.getKey()));
                list.add(object, (Weaver) entry.getValue());
                return;
            }
        }
        throw new RuntimeException("Could not find any subclass weavers that match the class " +
                object.getClass().getCanonicalName() + " of object: " + object);
    }

    @Override
    protected T fromRope(RopeList list) {
        String subclassIdentifier = list.getString(0);
        Class<? extends T> subclass = alternativeIdentifiers.get(subclassIdentifier);
        if (subclass == null) {
            throw new RuntimeException("Could not find any subclass weavers that match the subclass identifier " +
                    subclassIdentifier + " of rope: " + list.getRope(1));
        }
        Weaver<? extends T> weaver = subclassWeavers.get(subclass);
        return list.get(1, weaver);
    }

    //TODO: Implement an additional method that enables automated testing that,
    //e.g., all subclasses of a type have a corresponding subweaver in a weaver

    public static class SubclassWeaverBuilder<T> {
        private final Map<Class<? extends T>, Weaver<? extends T>> addedWeavers =
                new LinkedHashMap<>();
        private final Map<Class<? extends T>, String> preferredIdentifiers = new HashMap<>();
        private final Map<String, Class<? extends T>> alternativeIdentifiers = new HashMap<>();

        private SubclassWeaverBuilder(Class<T> mainClass) {
        }

        public <U extends T> SubclassWeaverBuilder<T> add(Class<U> subclass, Weaver<U> weaver) {
            return add(subclass, subclass.getCanonicalName(), weaver);
        }

        public <U extends T> SubclassWeaverBuilder<T> add(Class<U> subclass, String preferredIdentifier, Weaver<U> weaver) {
            if (addedWeavers.containsKey(subclass)) {
                throw new IllegalArgumentException("The class " + subclass + " already has a weaver specified.");
            }
            if (alternativeIdentifiers.containsKey(preferredIdentifier)) {
                throw new IllegalArgumentException("The identifier " + preferredIdentifier + " is already being used.");
            }
            if (alternativeIdentifiers.containsKey(subclass.getCanonicalName())) {
                throw new IllegalArgumentException("The default identifier " + subclass.getCanonicalName() + " is already being used.");
            }
            addedWeavers.put(subclass, weaver);
            alternativeIdentifiers.put(preferredIdentifier, subclass);
            alternativeIdentifiers.put(subclass.getCanonicalName(), subclass);
            preferredIdentifiers.put(subclass, preferredIdentifier);
            return this;
        }

        /**
         * Adds an alternative identifier for a class that will be recognized when converting
         * from a rope. This may be used for backwards-compatibility, i.e. to recognize an
         * existing identifier that is no longer the default.
         */
        public <U extends T> SubclassWeaverBuilder<T> addAlternativeId(Class<U> subclass, String alternativeIdentifier) {
            if (!addedWeavers.containsKey(subclass)) {
                throw new IllegalArgumentException("The class " + subclass + " has not yet been added to the builder.");
            }
            if (alternativeIdentifiers.containsKey(alternativeIdentifier)) {
                throw new IllegalArgumentException("The identifier " + alternativeIdentifier + " is already being used.");
            }
            alternativeIdentifiers.put(alternativeIdentifier, subclass);
            return this;
        }

        public SubclassWeaver<T> build() {
            return new SubclassWeaver<T>(new LinkedHashMap<>(addedWeavers),
                    new HashMap<>(preferredIdentifiers),
                    new HashMap<>(alternativeIdentifiers));
        }
    }
}
