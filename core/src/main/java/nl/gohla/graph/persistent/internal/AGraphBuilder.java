package nl.gohla.graph.persistent.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;

/**
 * Implementation for {@link IGraphBuilder} that keeps nodes and edges in a mutable data structure, with the
 * implementation of building the persistent graph abstracted out.
 * 
 * @param <V>
 *            Type of node labels. Must implement {@link Object#hashCode} and {@link Object#equals}.
 * @param <E>
 *            Type of edge labels. Must implement {@link Object#hashCode} and {@link Object#equals}.
 */
public abstract class AGraphBuilder<V, E> implements IGraphBuilder<V, E> {
    protected static class Node<V, E> {
        public final Map<Integer, Set<E>> inc = new HashMap<>();
        public final int node;
        public final @Nullable V label;
        public final Map<Integer, Set<E>> out = new HashMap<>();


        public Node(int node, @Nullable V label) {
            this.node = node;
            this.label = label;
        }

        public void addIncEdge(int srcNode, E edgeLabel) throws IllegalStateException {
            Set<E> edges = inc.get(srcNode);
            if(edges == null) {
                edges = new HashSet<E>();
                inc.put(srcNode, edges);
            }

            if(edges.contains(edgeLabel)) {
                throw new IllegalStateException();
            }

            edges.add(edgeLabel);
        }

        public void addOutEdge(int dstNode, E edgeLabel) throws IllegalStateException {
            Set<E> edges = out.get(dstNode);
            if(edges == null) {
                edges = new HashSet<E>();
                out.put(dstNode, edges);
            }

            if(edges.contains(edgeLabel)) {
                throw new IllegalStateException();
            }

            edges.add(edgeLabel);
        }

        public void addSelfEdge(E edgeLabel) throws IllegalStateException {
            addIncEdge(node, edgeLabel);
            addOutEdge(node, edgeLabel);
        }
    }


    protected final Map<Integer, Node<V, E>> nodes = new HashMap<>();


    @Override public IGraphBuilder<V, E> addNode(int node, @Nullable V nodeLabel) throws IllegalStateException {
        if(nodes.containsKey(node)) {
            throw new IllegalStateException();
        }

        nodes.put(node, new Node<V, E>(node, nodeLabel));
        return this;
    }

    @Override public IGraphBuilder<V, E> addEdge(int srcNode, int dstNode, E edgeLabel) throws IllegalStateException {
        if(!nodes.containsKey(srcNode)) {
            throw new IllegalStateException();
        }
        if(!nodes.containsKey(dstNode)) {
            throw new IllegalStateException();
        }

        final Node<V, E> srcNodeObj = nodes.get(srcNode);
        final Node<V, E> dstNodeObj = nodes.get(dstNode);

        srcNodeObj.addOutEdge(dstNode, edgeLabel);
        dstNodeObj.addIncEdge(srcNode, edgeLabel);

        return this;
    }

    @Override public IGraphBuilder<V, E> addSelfEdge(int node, E edgeLabel) throws IllegalStateException {
        if(!nodes.containsKey(node)) {
            throw new IllegalStateException();
        }
        final Node<V, E> nodeObj = nodes.get(node);
        nodeObj.addSelfEdge(edgeLabel);

        return this;
    }


    @Override abstract public IGraph<V, E> build();
}
