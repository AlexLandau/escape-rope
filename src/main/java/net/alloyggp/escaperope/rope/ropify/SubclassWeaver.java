package net.alloyggp.escaperope.rope.ropify;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

//TODO: Decide on exact semantics
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
//TODO: Add a way to use shorter custom (or even auto-generated) names.
@Deprecated //Not yet semantically stable.
public class SubclassWeaver<T> extends ListRopeWeaver<T> {
    //TODO: This could probably just be a list of pairs.
    //TODO: And this should be paired with a map from canonical name
    //to weaver. Plus we could cache class-of-object to relevant object...
    private final Map<Class<? extends T>, RopeWeaver<? extends T>> subclassWeavers;

    private SubclassWeaver(Map<Class<? extends T>, RopeWeaver<? extends T>> subclassWeavers) {
        this.subclassWeavers = subclassWeavers;
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
        for (Entry<Class<? extends T>, RopeWeaver<? extends T>> entry : subclassWeavers.entrySet()) {
            if (entry.getKey().isInstance(object)) {
                list.add(entry.getKey().getCanonicalName());
                list.add(object, (RopeWeaver) entry.getValue());
                return;
            }
        }
        throw new RuntimeException("Could not find any subclass weavers that match the class " +
                object.getClass().getCanonicalName() + " of object: " + object);
    }

    @Override
    protected T fromRope(RopeList list) {
        String className = list.getString(0);
        for (Entry<Class<? extends T>, RopeWeaver<? extends T>> entry : subclassWeavers.entrySet()) {
            if (entry.getKey().getCanonicalName().equals(className)) {
                return list.get(1, entry.getValue());
            }
        }
        throw new RuntimeException("Could not find any subclass weavers that match the recorded class " +
                className + " of rope: " + list.getRope(1));
    }

    //TODO: Implement an additional method that enables automated testing that,
    //e.g., all subclasses of a type have a corresponding subweaver in a weaver

    public static class SubclassWeaverBuilder<T> {
        private final Map<Class<? extends T>, RopeWeaver<? extends T>> addedWeavers =
                new LinkedHashMap<>();

        private SubclassWeaverBuilder(Class<T> mainClass) {
        }

        public <U extends T> SubclassWeaverBuilder<T> add(Class<U> subclass, RopeWeaver<U> weaver) {
            addedWeavers.put(subclass, weaver);
            return this;
        }

        public SubclassWeaver<T> build() {
            return new SubclassWeaver<T>(new LinkedHashMap<>(addedWeavers));
        }
    }
}
