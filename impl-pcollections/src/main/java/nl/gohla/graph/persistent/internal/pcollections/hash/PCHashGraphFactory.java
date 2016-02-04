package nl.gohla.graph.persistent.internal.pcollections.hash;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;
import nl.gohla.graph.persistent.IGraphFactory;

public class PCHashGraphFactory<V, E> implements IGraphFactory<V, E> {
    @Override public IGraph<V, E> of() {
        return new PCHashGraph<V, E>();
    }

    @Override public IGraphBuilder<V, E> builder() {
        return new PCHashGraphBuilder<V, E>();
    }
}
