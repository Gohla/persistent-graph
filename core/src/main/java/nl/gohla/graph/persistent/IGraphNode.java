package nl.gohla.graph.persistent;

import javax.annotation.Nullable;

/**
 * Persistent, labeled, graph node data structure.
 * 
 * @param <V>
 *            Type of node labels. Must implement {@link Object#hashCode} and {@link Object#equals}.
 * @param <E>
 *            Type of edge labels. Must implement {@link Object#hashCode} and {@link Object#equals}.
 */
public interface IGraphNode<V, E> {
    /**
     * Gets the incoming edges.
     * 
     * @return Incoming edges.
     */
    IGraphEdges<E> inc();

    /**
     * Gets the node identifier.
     * 
     * @return Node identifier.
     */
    int node();

    /**
     * Gets the node label. Can be null.
     * 
     * @return Node label.
     */
    @Nullable V label();

    /**
     * Gets the outgoing edges.
     * 
     * @return Outgoing edges.
     */
    IGraphEdges<E> out();


    /**
     * Modifies the incoming and outgoing edges.
     * 
     * @param newInc
     *            New incoming edges.
     * @param newOut
     *            New outgoing edges.
     * @return Graph node with new incoming and outgoing edges.
     */
    IGraphNode<V, E> modify(IGraphEdges<E> newInc, IGraphEdges<E> newOut);

    /**
     * Modifies the incoming edges.
     * 
     * @param newInc
     *            New incoming edges.
     * @return Graph node with new incoming edges.
     */
    IGraphNode<V, E> modifyInc(IGraphEdges<E> newInc);

    /**
     * Modifies the outgoing edges.
     * 
     * @param newOut
     *            New outgoing edges.
     * @return Graph node with new outgoing edges.
     */
    IGraphNode<V, E> modifyOut(IGraphEdges<E> newOut);


    /**
     * Adds edge to itself with given label.
     * 
     * @param edgeLabel
     *            Edge label.
     * @return Graph node with edge added.
     */
    IGraphNode<V, E> addSelf(E edgeLabel);

    /**
     * Removes edge to itself with given label.
     * 
     * @param edgeLabel
     *            Edge label.
     * @return Graph node with edge removed.
     */
    IGraphNode<V, E> removeSelf(E edgeLabel);

    /**
     * Removes all edges to itself.
     * 
     * @return Graph node with edges removed.
     */
    IGraphNode<V, E> removeSelfAll();
}
