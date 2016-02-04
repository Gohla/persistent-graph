package nl.gohla.graph.persistent;

/**
 * Factory for creating {@link IGraph}s and {@link IGraphBuilder}s.
 * 
 * @param <V>
 *            Type of node labels. Must implement {@link #hashCode} and {@link #equals}.
 * @param <E>
 *            Type of edge labels. Must implement {@link #hashCode} and {@link #equals}.
 */
public interface IGraphFactory<V, E> {
    IGraph<V, E> of();

    IGraphBuilder<V, E> builder();
}
