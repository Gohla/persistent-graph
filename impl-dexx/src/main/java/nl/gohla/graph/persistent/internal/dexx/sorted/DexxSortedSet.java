package nl.gohla.graph.persistent.internal.dexx.sorted;

import java.util.Collection;
import java.util.Iterator;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.SortedSet;
import com.github.andrewoma.dexx.collection.SortedSets;
import com.github.andrewoma.dexx.collection.Traversable;

import nl.gohla.graph.persistent.ISet;

public class DexxSortedSet<T extends Comparable<T>> implements ISet<T> {
    public final SortedSet<T> set;


    public DexxSortedSet() {
        this(SortedSets.<T>of());
    }

    public DexxSortedSet(T value) {
        this(SortedSets.<T>of(value));
    }

    public DexxSortedSet(Collection<? extends T> values) {
        final Builder<T, SortedSet<T>> builder = SortedSets.<T>builder();
        for(T value : values) {
            builder.add(value);
        }
        this.set = builder.build();
    }

    public DexxSortedSet(SortedSet<T> set) {
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
        return new DexxSortedSet<T>(set.add(value));
    }

    @Override public ISet<T> addAll(Collection<? extends T> values) {
        final Builder<T, SortedSet<T>> builder = SortedSets.<T>builder();
        builder.addAll((Traversable<T>) set);
        for(T value : values) {
            builder.add(value);
        }
        return new DexxSortedSet<T>(builder.build());
    }

    @Override public ISet<T> remove(T value) {
        return new DexxSortedSet<T>(set.remove(value));
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
        @SuppressWarnings("unchecked") DexxSortedSet<T> other = (DexxSortedSet<T>) obj;
        if(!set.equals(other.set))
            return false;
        return true;
    }

    @Override public String toString() {
        return set.toString();
    }
}
