package nl.gohla.graph.persistent.internal.pcollections;

import java.util.Collection;
import java.util.Iterator;

import org.pcollections.HashTreePSet;
import org.pcollections.PSet;

import nl.gohla.graph.persistent.ISet;

public class PCSet<T> implements ISet<T> {
    public final PSet<T> set;


    public PCSet() {
        this(HashTreePSet.<T>empty());
    }

    public PCSet(T value) {
        this(HashTreePSet.<T>singleton(value));
    }

    public PCSet(Collection<? extends T> values) {
        this(HashTreePSet.<T>from(values));
    }

    public PCSet(PSet<T> set) {
        this.set = set;
    }


    @Override public Iterator<T> iterator() {
        return set.iterator();
    }

    @Override public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override public boolean contains(T value) {
        return set.contains(value);
    }

    @Override public ISet<T> add(T value) {
        return new PCSet<T>(set.plus(value));
    }

    @Override public ISet<T> addAll(Collection<? extends T> values) {
        return new PCSet<T>(set.plusAll(values));
    }

    @Override public ISet<T> remove(T value) {
        return new PCSet<T>(set.minus(value));
    }


    @Override public int hashCode() {
        return set.hashCode();
    }

    @Override public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked") PCSet<T> other = (PCSet<T>) obj;
        if(!set.equals(other.set))
            return false;
        return true;
    }

    @Override public String toString() {
        return set.toString();
    }
}
