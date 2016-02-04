package nl.gohla.graph.persistent.internal.pcollections.tree;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;
import nl.gohla.graph.persistent.IGraphFactory;

public class PCTreeGraphFactory<V, E> implements IGraphFactory<V, E> {
    @Override public IGraph<V, E> of() {
        return new PCTreeGraph<V, E>();
    }

    @Override public IGraphBuilder<V, E> builder() {
        return new PCTreeGraphBuilder<V, E>();
    }
}
