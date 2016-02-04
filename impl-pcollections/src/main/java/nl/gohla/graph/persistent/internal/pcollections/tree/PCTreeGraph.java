package nl.gohla.graph.persistent.internal.pcollections.tree;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.internal.AGraph;
import nl.gohla.graph.persistent.internal.pcollections.PCGraphNode;

public class PCTreeGraph<V, E> extends AGraph<V, E> {
    public PCTreeGraph() {
        this(new PCTreeIntMap<IGraphNode<V, E>>());
    }

    public PCTreeGraph(IIntMap<IGraphNode<V, E>> contexts) {
        super(contexts);
    }


    @Override public IGraph<V, E> createGraph(IIntMap<IGraphNode<V, E>> contexts) {
        return new PCTreeGraph<V, E>(contexts);
    }

    @Override public IGraphNode<V, E> createNode(int node, V nodeLabel) {
        return new PCGraphNode<V, E>(new PCTreeGraphEdges<E>(), node, nodeLabel, new PCTreeGraphEdges<E>());
    }
}
