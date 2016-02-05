package nl.gohla.graph.persistent.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;

import nl.gohla.graph.persistent.IGraphEdges;
import nl.gohla.graph.persistent.IIntMap;
import nl.gohla.graph.persistent.ISet;

/**
 * Implementation for {@link IGraphEdges}, with collection-specific details abstracted out.
 * 
 * @param <E>
 *            Type of edge labels. Must implement {@link Object#hashCode} and {@link Object#equals}.
 */
public abstract class AGraphEdges<E> implements IGraphEdges<E> {
    public final IIntMap<ISet<E>> edges;


    public AGraphEdges(IIntMap<ISet<E>> edges) {
        this.edges = edges;
    }


    @Override public boolean containsEdge(int node) {
        return edges.contains(node);
    }

    @Override public boolean containsEdge(int node, E edgeLabel) {
        final ISet<E> edgeLabels = edges.get(node);
        if(edgeLabels == null) {
            return false;
        }
        return edgeLabels.contains(edgeLabel);
    }


    @Override public Iterable<Integer> nodes() {
        return edges.keys();
    }

    @Override public Iterable<E> labels(int node) {
        final ISet<E> edgeLabels = edges.get(node);
        if(edgeLabels == null) {
            return Collections.emptyList();
        }
        return edgeLabels;
    }

    @Override public Iterable<? extends Entry<Integer, ? extends Iterable<E>>> edges() {
        return edges.entries();
    }


    @Override public IGraphEdges<E> put(int node, E label) {
        final IIntMap<ISet<E>> newEdges;
        final ISet<E> labels = edges.get(node);
        if(labels == null) {
            newEdges = edges.put(node, createSet(label));
        } else {
            newEdges = edges.put(node, labels.add(label));
        }
        return createEdges(newEdges);
    }

    @Override public IGraphEdges<E> remove(int node, E label) {
        final ISet<E> labels = edges.get(node);
        if(labels == null) {
            return this;
        } else {
            final ISet<E> newLabels = labels.remove(label);
            if(newLabels.isEmpty()) {
                return createEdges(edges.remove(node));
            } else {
                return createEdges(edges.put(node, newLabels));
            }
        }
    }

    @Override public IGraphEdges<E> removeAll(int node) {
        return createEdges(edges.remove(node));
    }


    public abstract IGraphEdges<E> createEdges(IIntMap<ISet<E>> newEdges);

    public abstract ISet<E> createSet(E label);

    public abstract ISet<E> createSet(Collection<? extends E> labels);


    @Override public int hashCode() {
        return edges.hashCode();
    }

    @Override public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked") final AGraphEdges<E> other = (AGraphEdges<E>) obj;
        if(!edges.equals(other.edges))
            return false;
        return true;
    }

    @Override public String toString() {
        return edges.toString();
    }
}
