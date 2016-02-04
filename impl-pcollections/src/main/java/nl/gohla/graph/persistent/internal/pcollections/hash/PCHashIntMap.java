package nl.gohla.graph.persistent.internal.pcollections.hash;

import java.util.Map.Entry;
import java.util.Set;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;

import nl.gohla.graph.persistent.IIntMap;

public class PCHashIntMap<T> implements IIntMap<T> {
    public final HashPMap<Integer, T> map;


    public PCHashIntMap() {
        this(HashTreePMap.<Integer, T>empty());
    }

    public PCHashIntMap(HashPMap<Integer, T> map) {
        this.map = map;
    }


    @Override public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override public boolean contains(int key) {
        return map.containsKey(key);
    }

    @Override public T get(int key) {
        return map.get(key);
    }

    @Override public Set<Integer> keys() {
        return map.keySet();
    }

    @Override public Iterable<T> values() {
        return map.values();
    }

    @Override public Iterable<Entry<Integer, T>> entries() {
        return map.entrySet();
    }

    @Override public IIntMap<T> put(int key, T value) {
        return new PCHashIntMap<T>(map.plus(key, value));
    }

    @Override public IIntMap<T> remove(int key) {
        return new PCHashIntMap<T>(map.minus(key));
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
        @SuppressWarnings("unchecked") PCHashIntMap<T> other = (PCHashIntMap<T>) obj;
        if(!map.equals(other.map))
            return false;
        return true;
    }

    @Override public String toString() {
        return map.toString();
    }
}
