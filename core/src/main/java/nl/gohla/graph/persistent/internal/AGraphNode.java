package nl.gohla.graph.persistent.internal;

import javax.annotation.Nullable;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IGraphNode;

/**
 * Implementation for {@link IGraphNode}, with collection-specific details abstracted out.
 * 
 * @param <V>
 *            Type of node labels. Must implement {@link #hashCode} and {@link #equals}.
 * @param <E>
 *            Type of edge labels. Must implement {@link #hashCode} and {@link #equals}.
 */
public abstract class AGraphNode<V, E> implements IGraphNode<V, E> {
    public final IGraphEdges<E> inc;
    public final int node;
    public final @Nullable V nodeLabel;
    public final IGraphEdges<E> out;


    public AGraphNode(IGraphEdges<E> inc, int node, @Nullable V nodeLabel, IGraphEdges<E> out) {
        this.inc = inc;
        this.node = node;
        this.nodeLabel = nodeLabel;
        this.out = out;
    }


    @Override public IGraphEdges<E> inc() {
        return inc;
    }

    @Override public int node() {
        return node;
    }

    @Override public V label() {
        return nodeLabel;
    }

    @Override public IGraphEdges<E> out() {
        return out;
    }


    @Override public IGraphNode<V, E> modifyInc(IGraphEdges<E> newInc) {
        return modify(newInc, out);
    }

    @Override public IGraphNode<V, E> modifyOut(IGraphEdges<E> newOut) {
        return modify(inc, newOut);
    }


    @Override public IGraphNode<V, E> addSelf(E edgeLabel) {
        return modify(inc.put(node, edgeLabel), out.put(node, edgeLabel));
    }

    @Override public IGraphNode<V, E> removeSelf(E edgeLabel) {
        return modify(inc.remove(node, edgeLabel), out.remove(node, edgeLabel));
    }

    @Override public IGraphNode<V, E> removeSelfAll() {
        return modify(inc.removeAll(node), out.removeAll(node));
    }


    @Override public abstract IGraphNode<V, E> modify(IGraphEdges<E> newInc, IGraphEdges<E> newOut);


    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + inc.hashCode();
        result = prime * result + node;
        result = prime * result + ((nodeLabel == null) ? 0 : nodeLabel.hashCode());
        result = prime * result + out.hashCode();
        return result;
    }

    @Override public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked") final AGraphNode<V, E> other = (AGraphNode<V, E>) obj;
        if(!inc.equals(other.inc))
            return false;
        if(node != other.node)
            return false;
        if(nodeLabel == null) {
            if(other.nodeLabel != null)
                return false;
        } else if(!nodeLabel.equals(other.nodeLabel))
            return false;
        if(!out.equals(other.out))
            return false;
        return true;
    }

    @Override public String toString() {
        return "(" + inc.toString() + ", " + node + ", " + nodeLabel + ", " + out.toString() + ")";
    }
}
