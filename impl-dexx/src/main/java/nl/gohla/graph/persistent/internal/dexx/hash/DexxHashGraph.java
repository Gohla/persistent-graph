package nl.gohla.graph.persistent.internal.dexx.hash;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.internal.AGraph;
import nl.gohla.graph.persistent.internal.dexx.DexxGraphNode;

public class DexxHashGraph<V, E> extends AGraph<V, E> {
    public DexxHashGraph() {
        this(new DexxHashIntMap<IGraphNode<V, E>>());
    }

    public DexxHashGraph(IIntMap<IGraphNode<V, E>> contexts) {
        super(contexts);
    }


    @Override public IGraph<V, E> createGraph(IIntMap<IGraphNode<V, E>> contexts) {
        return new DexxHashGraph<V, E>(contexts);
    }

    @Override public IGraphNode<V, E> createNode(int node, V nodeLabel) {
        return new DexxGraphNode<V, E>(new DexxHashGraphEdges<E>(), node, nodeLabel, new DexxHashGraphEdges<E>());
    }
}
