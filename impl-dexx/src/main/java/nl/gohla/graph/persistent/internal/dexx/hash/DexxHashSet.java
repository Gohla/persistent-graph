package nl.gohla.graph.persistent.internal.dexx.hash;

import java.util.Collection;
import java.util.Iterator;

import nl.gohla.graph.persistent.ISet;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.Sets;
import com.github.andrewoma.dexx.collection.Traversable;

public class DexxHashSet<T> implements ISet<T> {
    public final Set<T> set;


    public DexxHashSet() {
        this(Sets.<T>of());
    }

    public DexxHashSet(T value) {
        this(Sets.<T>of(value));
    }

    public DexxHashSet(Collection<? extends T> values) {
        final Builder<T, Set<T>> builder = Sets.<T>builder();
        for(T value : values) {
            builder.add(value);
        }
        this.set = builder.build();
    }

    public DexxHashSet(Set<T> set) {
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
        return new DexxHashSet<T>(set.add(value));
    }

    @Override public ISet<T> addAll(Collection<? extends T> values) {
        final Builder<T, Set<T>> builder = Sets.<T>builder();
        builder.addAll((Traversable<T>) set);
        for(T value : values) {
            builder.add(value);
        }
        return new DexxHashSet<T>(builder.build());
    }

    @Override public ISet<T> remove(T value) {
        return new DexxHashSet<T>(set.remove(value));
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
        @SuppressWarnings("unchecked") DexxHashSet<T> other = (DexxHashSet<T>) obj;
        if(!set.equals(other.set))
            return false;
        return true;
    }

    @Override public String toString() {
        return set.toString();
    }
}
