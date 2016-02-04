package nl.gohla.graph.persistent.internal.pcollections.hash;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.internal.AGraph;
import nl.gohla.graph.persistent.internal.pcollections.PCGraphNode;

public class PCHashGraph<V, E> extends AGraph<V, E> {
    public PCHashGraph() {
        this(new PCHashIntMap<IGraphNode<V, E>>());
    }

    public PCHashGraph(IIntMap<IGraphNode<V, E>> contexts) {
        super(contexts);
    }


    @Override public IGraph<V, E> createGraph(IIntMap<IGraphNode<V, E>> contexts) {
        return new PCHashGraph<V, E>(contexts);
    }

    @Override public IGraphNode<V, E> createNode(int node, V nodeLabel) {
        return new PCGraphNode<V, E>(new PCHashGraphEdges<E>(), node, nodeLabel, new PCHashGraphEdges<E>());
    }
}
