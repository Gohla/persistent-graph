package nl.gohla.graph.persistent.internal.dexx;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.internal.AGraphNode;

public class DexxGraphNode<V, E> extends AGraphNode<V, E> {
    public DexxGraphNode(IGraphEdges<E> inc, int node, V nodeLabel, IGraphEdges<E> out) {
        super(inc, node, nodeLabel, out);
    }


    @Override public IGraphNode<V, E> modify(IGraphEdges<E> newInc, IGraphEdges<E> newOut) {
        return new DexxGraphNode<V, E>(newInc, node, nodeLabel, newOut);
    }
}
