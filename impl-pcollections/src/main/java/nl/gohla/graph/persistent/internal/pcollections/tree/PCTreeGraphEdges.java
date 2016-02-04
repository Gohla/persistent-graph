package nl.gohla.graph.persistent.internal.pcollections.tree;

import java.util.Collection;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.ISet;
import nl.gohla.graph.persistent.internal.AGraphEdges;
import nl.gohla.graph.persistent.internal.pcollections.PCSet;

public class PCTreeGraphEdges<E> extends AGraphEdges<E> {
    public PCTreeGraphEdges() {
        this(new PCTreeIntMap<ISet<E>>());
    }

    public PCTreeGraphEdges(IIntMap<ISet<E>> edges) {
        super(edges);
    }


    @Override public IGraphEdges<E> createEdges(IIntMap<ISet<E>> newEdges) {
        return new PCTreeGraphEdges<E>(newEdges);
    }

    @Override public ISet<E> createSet(E label) {
        return new PCSet<E>(label);
    }

    @Override public ISet<E> createSet(Collection<? extends E> labels) {
        return new PCSet<E>(labels);
    }
}
