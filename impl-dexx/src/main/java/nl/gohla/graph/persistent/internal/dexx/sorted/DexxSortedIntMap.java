package nl.gohla.graph.persistent.internal.dexx.sorted;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.github.andrewoma.dexx.collection.SortedMap;
import com.github.andrewoma.dexx.collection.SortedMaps;

import nl.gohla.graph.persistent.IIntMap;

public class DexxSortedIntMap<T> implements IIntMap<T> {
    public final SortedMap<Integer, T> map;


    public DexxSortedIntMap() {
        this(SortedMaps.<Integer, T>of());
    }

    public DexxSortedIntMap(SortedMap<Integer, T> map) {
        this.map = map;
    }


    @Override public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override public boolean contains(int key) {
        return map.containsKey(key);
    }

    @Override public @Nullable T get(int key) {
        return map.get(key);
    }

    @Override public Iterable<Integer> keys() {
        return map.keys();
    }

    @Override public Iterable<T> values() {
        return map.values();
    }

    @Override public Iterable<Entry<Integer, T>> entries() {
        return map.asMap().entrySet();
    }

    @Override public IIntMap<T> put(int key, T value) {
        return new DexxSortedIntMap<T>(map.put(key, value));
    }

    @Override public IIntMap<T> remove(int key) {
        return new DexxSortedIntMap<T>(map.remove(key));
    }


    @Override public int hashCode() {
        return map.hashCode();
    }

    @Override public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked") DexxSortedIntMap<T> other = (DexxSortedIntMap<T>) obj;
        if(!map.equals(other.map))
            return false;
        return true;
    }

    @Override public String toString() {
        return map.toString();
    }
}
