package nl.gohla.graph.persistent.internal.dexx.sorted;

import java.util.Collection;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.ISet;
import nl.gohla.graph.persistent.internal.AGraphEdges;

public class DexxSortedGraphEdges<E extends Comparable<E>> extends AGraphEdges<E> {
    public DexxSortedGraphEdges() {
        this(new DexxSortedIntMap<ISet<E>>());
    }

    public DexxSortedGraphEdges(IIntMap<ISet<E>> edges) {
        super(edges);
    }


    @Override public IGraphEdges<E> createEdges(IIntMap<ISet<E>> newEdges) {
        return new DexxSortedGraphEdges<E>(newEdges);
    }

    @Override public ISet<E> createSet(E label) {
        return new DexxSortedSet<E>(label);
    }

    @Override public ISet<E> createSet(Collection<? extends E> labels) {
        return new DexxSortedSet<E>(labels);
    }
}
