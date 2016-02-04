package nl.gohla.graph.persistent.internal.dexx.sorted;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;
import nl.gohla.graph.persistent.IGraphFactory;

public class DexxSortedGraphFactory<V, E extends Comparable<E>> implements IGraphFactory<V, E> {
    @Override public IGraph<V, E> of() {
        return new DexxSortedGraph<V, E>();
    }

    @Override public IGraphBuilder<V, E> builder() {
        return new DexxSortedGraphBuilder<V, E>();
    }
}
