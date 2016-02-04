package nl.gohla.graph.persistent.internal.pcollections;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.internal.AGraphNode;

public class PCGraphNode<V, E> extends AGraphNode<V, E> {
    public PCGraphNode(IGraphEdges<E> inc, int node, V nodeLabel, IGraphEdges<E> out) {
        super(inc, node, nodeLabel, out);
    }


    @Override public IGraphNode<V, E> modify(IGraphEdges<E> newInc, IGraphEdges<E> newOut) {
        return new PCGraphNode<V, E>(newInc, node, nodeLabel, newOut);
    }
}
