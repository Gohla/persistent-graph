package nl.gohla.graph.persistent;

import java.util.Map.Entry;

import javax.annotation.Nullable;

/**
 * Persistent integer to object map.
 * 
 * @param <T>
 *            Type of values in the map. Must implement {@link Object#hashCode} and {@link Object#equals}.
 */
public interface IIntMap<T> {
    /**
     * Checks if the map is empty.
     * 
     * @return True if empty, false if not.
     */
    boolean isEmpty();

    /**
     * Checks if the map contains given key.
     * 
     * @param key
     *            Key to check.
     * @return True if contained, false if not.
     */
    boolean contains(int key);


    /**
     * Gets value with given key.
     * 
     * @param key
     *            Key to get value for.
     * @return Value for given key, or null if key does not exist.
     */
    @Nullable T get(int key);


    /**
     * Gets the keys.
     * 
     * @return Keys.
     */
    Iterable<Integer> keys();

    /**
     * Gets the values.
     * 
     * @return Values.
     */
    Iterable<T> values();

    /**
     * Gets the entries.
     * 
     * @return Entries.
     */
    Iterable<Entry<Integer, T>> entries();


    /**
     * Adds or replaces a mapping from given key to given value.
     * 
     * @param key
     *            Key to add a mapping for.
     * @param value
     *            Value to add a mapping to.
     * @return Map with mapping added.
     */
    IIntMap<T> put(int key, T value);

    /**
     * Removes mapping with given key.
     * 
     * @param key
     *            Key to remove mapping for.
     * @return Map with mapping removed.
     */
    IIntMap<T> remove(int key);
}
