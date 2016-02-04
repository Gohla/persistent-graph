package nl.gohla.graph.persistent.internal.dexx.hash;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;
import nl.gohla.graph.persistent.IGraphFactory;

public class DexxHashGraphFactory<V, E> implements IGraphFactory<V, E> {
    @Override public IGraph<V, E> of() {
        return new DexxHashGraph<V, E>();
    }

    @Override public IGraphBuilder<V, E> builder() {
        return new DexxHashGraphBuilder<V, E>();
    }
}
