package nl.gohla.graph.persistent;

import javax.annotation.Nullable;

/**
 * Builder for efficiently creating graphs in a mutable way.
 * 
 * @param <V>
 *            Type of node labels. Must implement {@link #hashCode} and {@link #equals}.
 * @param <E>
 *            Type of edge labels. Must implement {@link #hashCode} and {@link #equals}.
 */
public interface IGraphBuilder<V, E> {
    /**
     * @see IGraph#addNode(int, Object)
     */
    IGraphBuilder<V, E> addNode(int node, @Nullable V nodeLabel) throws IllegalStateException;

    /**
     * @see IGraph#addEdge(int, int, Object)
     */
    IGraphBuilder<V, E> addEdge(int srcNode, int dstNode, E edgeLabel) throws IllegalStateException;

    /**
     * @see IGraph#addSelfEdge(int, Object)
     */
    IGraphBuilder<V, E> addSelfEdge(int node, E edgeLabel) throws IllegalStateException;


    /**
     * Builds a persistent graph from the current state of the builder.
     * 
     * @return Persistent graph.
     */
    IGraph<V, E> build();
}
