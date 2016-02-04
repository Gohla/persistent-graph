package nl.gohla.graph.persistent.internal.pcollections.hash;

import javax.annotation.Nullable;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;

public class PCHashGraphBuilder<V, E> implements IGraphBuilder<V, E> {
    private IGraph<V, E> graph;


    public PCHashGraphBuilder() {
        this.graph = new PCHashGraph<V, E>();
    }


    @Override public IGraphBuilder<V, E> addNode(int node, @Nullable V nodeLabel) {
        graph = graph.addNode(node, nodeLabel);
        return this;
    }

    @Override public IGraphBuilder<V, E> addEdge(int srcNode, int dstNode, E edgeLabel) {
        graph = graph.addEdge(srcNode, dstNode, edgeLabel);
        return this;
    }

    @Override public IGraphBuilder<V, E> addSelfEdge(int node, E edgeLabel) {
        graph = graph.addSelfEdge(node, edgeLabel);
        return this;
    }


    @Override public IGraph<V, E> build() {
        return graph;
    }
}
