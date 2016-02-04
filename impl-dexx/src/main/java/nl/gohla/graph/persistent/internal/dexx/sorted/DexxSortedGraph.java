package nl.gohla.graph.persistent.internal.dexx.sorted;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.internal.AGraph;
import nl.gohla.graph.persistent.internal.dexx.DexxGraphNode;

public class DexxSortedGraph<V, E extends Comparable<E>> extends AGraph<V, E> {
    public DexxSortedGraph() {
        this(new DexxSortedIntMap<IGraphNode<V, E>>());
    }

    public DexxSortedGraph(IIntMap<IGraphNode<V, E>> contexts) {
        super(contexts);
    }


    @Override public IGraph<V, E> createGraph(IIntMap<IGraphNode<V, E>> contexts) {
        return new DexxSortedGraph<V, E>(contexts);
    }

    @Override public IGraphNode<V, E> createNode(int node, V nodeLabel) {
        return new DexxGraphNode<V, E>(new DexxSortedGraphEdges<E>(), node, nodeLabel, new DexxSortedGraphEdges<E>());
    }
}
