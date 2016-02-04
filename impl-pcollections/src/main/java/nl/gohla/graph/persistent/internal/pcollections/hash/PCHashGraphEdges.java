package nl.gohla.graph.persistent.internal.pcollections.hash;

import java.util.Collection;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.ISet;
import nl.gohla.graph.persistent.internal.AGraphEdges;
import nl.gohla.graph.persistent.internal.pcollections.PCSet;

public class PCHashGraphEdges<E> extends AGraphEdges<E> {
    public PCHashGraphEdges() {
        this(new PCHashIntMap<ISet<E>>());
    }

    public PCHashGraphEdges(IIntMap<ISet<E>> edges) {
        super(edges);
    }


    @Override public IGraphEdges<E> createEdges(IIntMap<ISet<E>> newEdges) {
        return new PCHashGraphEdges<E>(newEdges);
    }

    @Override public ISet<E> createSet(E label) {
        return new PCSet<E>(label);
    }

    @Override public ISet<E> createSet(Collection<? extends E> labels) {
        return new PCSet<E>(labels);
    }
}
