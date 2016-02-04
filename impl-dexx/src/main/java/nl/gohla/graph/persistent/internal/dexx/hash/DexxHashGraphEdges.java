package nl.gohla.graph.persistent.internal.dexx.hash;

import java.util.Collection;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.ISet;
import nl.gohla.graph.persistent.internal.AGraphEdges;

public class DexxHashGraphEdges<E> extends AGraphEdges<E> {
    public DexxHashGraphEdges() {
        this(new DexxHashIntMap<ISet<E>>());
    }

    public DexxHashGraphEdges(IIntMap<ISet<E>> edges) {
        super(edges);
    }


    @Override public IGraphEdges<E> createEdges(IIntMap<ISet<E>> newEdges) {
        return new DexxHashGraphEdges<>(newEdges);
    }

    @Override public ISet<E> createSet(E label) {
        return new DexxHashSet<E>(label);
    }

    @Override public ISet<E> createSet(Collection<? extends E> labels) {
        return new DexxHashSet<E>(labels);
    }
}
