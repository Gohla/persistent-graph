package nl.gohla.graph.persistent.internal;

import java.util.Objects;

import javax.annotation.Nullable;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.IIntMap;

/**
 * Implementation for {@link IGraph}, with collection-specific details abstracted out.
 * 
 * @param <V>
 *            Type of node labels. Must implement {@link #hashCode} and {@link #equals}.
 * @param <E>
 *            Type of edge labels. Must implement {@link #hashCode} and {@link #equals}.
 */
public abstract class AGraph<V, E> implements IGraph<V, E> {
    private final IIntMap<IGraphNode<V, E>> contexts;


    public AGraph(IIntMap<IGraphNode<V, E>> contexts) {
        this.contexts = contexts;
    }


    @Override public boolean isEmpty() {
        return contexts.isEmpty();
    }

    @Override public boolean containsNode(int node) {
        return contexts.contains(node);
    }

    @Override public boolean containsNode(int node, @Nullable V nodeLabel) {
        final IGraphNode<V, E> context = contexts.get(node);
        if(context == null) {
            return false;
        }
        return Objects.equals(nodeLabel, context.label());
    }

    @Override public boolean containsEdge(int srcNode, int dstNode) {
        final IGraphNode<V, E> srcContext = contexts.get(srcNode);
        if(srcContext == null) {
            return false;
        }
        return srcContext.out().containsEdge(dstNode);
    }

    @Override public boolean containsEdge(int srcNode, int dstNode, E edgeLabel) {
        final IGraphNode<V, E> srcContext = contexts.get(srcNode);
        if(srcContext == null) {
            return false;
        }
        return srcContext.out().containsEdge(dstNode, edgeLabel);
    }

    @Override public @Nullable IGraphNode<V, E> get(int node) {
        return contexts.get(node);
    }

    @Override public Iterable<Integer> nodeIds() {
        return contexts.keys();
    }

    @Override public Iterable<? extends IGraphNode<V, E>> nodes() {
        return contexts.values();
    }


    @Override public IGraph<V, E> addNode(int node, @Nullable V nodeLabel) throws IllegalStateException {
        if(contexts.contains(node)) {
            throw new IllegalStateException("Node " + node + " already exists");
        }

        final IGraphNode<V, E> context = createNode(node, nodeLabel);
        final IIntMap<IGraphNode<V, E>> newContexts = contexts.put(node, context);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> removeNode(int node) throws IllegalStateException {
        final IGraphNode<V, E> removedContext = contexts.get(node);
        if(removedContext == null) {
            throw new IllegalStateException("Node " + node + " does not exist");
        }

        IIntMap<IGraphNode<V, E>> newContexts = contexts;
        for(Integer inc : removedContext.inc().nodes()) {
            IGraphNode<V, E> ctx = contexts.get(inc);
            ctx = ctx.modifyOut(ctx.out().removeAll(node));
            newContexts = newContexts.put(inc, ctx);
        }
        for(Integer out : removedContext.out().nodes()) {
            IGraphNode<V, E> ctx = newContexts.get(out);
            ctx = ctx.modifyInc(ctx.inc().removeAll(node));
            newContexts = newContexts.put(out, ctx);
        }
        newContexts = newContexts.remove(node);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> addEdge(int srcNode, int dstNode, E edgeLabel) throws IllegalStateException {
        if(srcNode == dstNode) {
            return addSelfEdge(srcNode, edgeLabel);
        }

        if(edgeLabel == null) {
            throw new IllegalStateException("Edge labels may not be null");
        }

        IGraphNode<V, E> srcContext = contexts.get(srcNode);
        if(srcContext == null) {
            throw new IllegalStateException("Source node " + srcNode + " does not exist");
        }
        IGraphNode<V, E> dstContext = contexts.get(dstNode);
        if(dstContext == null) {
            throw new IllegalStateException("Destination node " + dstNode + " does not exist");
        }
        if(srcContext.out().containsEdge(dstNode, edgeLabel)) {
            throw new IllegalStateException("Source node " + srcNode + " already has an outgoing " + edgeLabel
                + "-labeled edge to destination node " + dstNode);
        }
        // Under normal operation, this does not have to be tested, the previous test already covers this case.
        if(dstContext.inc().containsEdge(srcNode, edgeLabel)) {
            throw new IllegalStateException("Destination node " + dstNode + " already has an incoming " + edgeLabel
                + "-labeled edge from source node " + srcNode);
        }

        srcContext = srcContext.modifyOut(srcContext.out().put(dstNode, edgeLabel));
        dstContext = dstContext.modifyInc(dstContext.inc().put(srcNode, edgeLabel));
        IIntMap<IGraphNode<V, E>> newContexts = contexts;
        newContexts = newContexts.put(srcNode, srcContext);
        newContexts = newContexts.put(dstNode, dstContext);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> removeEdge(int srcNode, int dstNode, E edgeLabel) throws IllegalStateException {
        if(srcNode == dstNode) {
            return removeSelfEdge(srcNode, edgeLabel);
        }

        IGraphNode<V, E> srcContext = contexts.get(srcNode);
        if(srcContext == null) {
            throw new IllegalStateException("Source node " + srcNode + " does not exist");
        }
        IGraphNode<V, E> dstContext = contexts.get(dstNode);
        if(dstContext == null) {
            throw new IllegalStateException("Destination node " + dstNode + " does not exist");
        }
        if(!srcContext.out().containsEdge(dstNode, edgeLabel)) {
            throw new IllegalStateException("Source node " + srcNode + " does not have an " + edgeLabel
                + "-labeled outgoing edge to destination node " + dstNode);
        }
        // Under normal operation, this does not have to be tested, the previous test already covers this case.
        if(!dstContext.inc().containsEdge(srcNode, edgeLabel)) {
            throw new IllegalStateException("Destination node " + dstNode + " does not have an incoming " + edgeLabel
                + "-labeled edge from source node" + srcNode);
        }

        srcContext = srcContext.modifyOut(srcContext.out().remove(dstNode, edgeLabel));
        dstContext = dstContext.modifyInc(dstContext.inc().remove(srcNode, edgeLabel));
        IIntMap<IGraphNode<V, E>> newContexts = contexts;
        newContexts = newContexts.put(srcNode, srcContext);
        newContexts = newContexts.put(dstNode, dstContext);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> removeAllEdges(int srcNode, int dstNode) throws IllegalStateException {
        if(srcNode == dstNode) {
            return removeAllSelfEdges(srcNode);
        }

        IGraphNode<V, E> srcContext = contexts.get(srcNode);
        if(srcContext == null) {
            throw new IllegalStateException("Source node " + srcNode + " does not exist");
        }
        IGraphNode<V, E> dstContext = contexts.get(dstNode);
        if(dstContext == null) {
            throw new IllegalStateException("Destination node " + dstNode + " does not exist");
        }
        if(!srcContext.out().containsEdge(dstNode)) {
            throw new IllegalStateException("Source node " + srcNode
                + " does not have any outgoing edges to destination node " + dstNode);
        }
        // Under normal operation, this does not have to be tested, the previous test already covers this case.
        if(!dstContext.inc().containsEdge(srcNode)) {
            throw new IllegalStateException("Destination node " + dstNode
                + " does not have any incoming edges from source node" + srcNode);
        }

        srcContext = srcContext.modifyOut(srcContext.out().removeAll(dstNode));
        dstContext = dstContext.modifyInc(dstContext.inc().removeAll(srcNode));
        IIntMap<IGraphNode<V, E>> newContexts = contexts;
        newContexts = newContexts.put(srcNode, srcContext);
        newContexts = newContexts.put(dstNode, dstContext);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> addSelfEdge(int node, E edgeLabel) throws IllegalStateException {
        if(edgeLabel == null) {
            throw new IllegalStateException("Edge labels may not be null");
        }

        IGraphNode<V, E> context = contexts.get(node);
        if(context == null) {
            throw new IllegalStateException("Node " + node + " does not exist");
        }
        if(context.inc().containsEdge(node, edgeLabel)) {
            throw new IllegalStateException("Node " + node + " already has an incoming " + edgeLabel
                + "-labeled edge to itself");
        }
        // Under normal operation, this does not have to be tested, the previous test already covers this case.
        if(context.out().containsEdge(node, edgeLabel)) {
            throw new IllegalStateException("Node " + node + " already has an outgoing " + edgeLabel
                + "-labeled edge to itself");
        }

        context = context.addSelf(edgeLabel);
        final IIntMap<IGraphNode<V, E>> newContexts = contexts.put(node, context);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> removeSelfEdge(int node, E edgeLabel) throws IllegalStateException {
        IGraphNode<V, E> context = contexts.get(node);
        if(context == null) {
            throw new IllegalStateException("Node " + node + " does not exist");
        }
        if(!context.inc().containsEdge(node, edgeLabel)) {
            throw new IllegalStateException("Node " + node + " does not have an incoming " + edgeLabel
                + "-labeled edge to itself");
        }
        // Under normal operation, this does not have to be tested, the previous test already covers this case.
        if(!context.out().containsEdge(node, edgeLabel)) {
            throw new IllegalStateException("Node " + node + " does not have an outgoing " + edgeLabel
                + "-labeled edge to itself");
        }

        context = context.removeSelf(edgeLabel);
        final IIntMap<IGraphNode<V, E>> newContexts = contexts.put(node, context);
        return createGraph(newContexts);
    }

    @Override public IGraph<V, E> removeAllSelfEdges(int node) throws IllegalStateException {
        IGraphNode<V, E> context = contexts.get(node);
        if(context == null) {
            throw new IllegalStateException("Node " + node + " does not exist");
        }
        if(!context.inc().containsEdge(node)) {
            throw new IllegalStateException("Node " + node + " does not have any incoming edges to itself");
        }
        // Under normal operation, this does not have to be tested, the previous test already covers this case.
        if(!context.out().containsEdge(node)) {
            throw new IllegalStateException("Node " + node + " does not have any outgoing edges to itself");
        }

        context = context.removeSelfAll();
        final IIntMap<IGraphNode<V, E>> newContexts = contexts.put(node, context);
        return createGraph(newContexts);
    }


    public abstract IGraph<V, E> createGraph(IIntMap<IGraphNode<V, E>> contexts);

    public abstract IGraphNode<V, E> createNode(int node, @Nullable V nodeLabel);


    @Override public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + contexts.hashCode();
        return result;
    }

    @Override public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked") final AGraph<V, E> other = (AGraph<V, E>) obj;
        if(!contexts.equals(other.contexts))
            return false;
        return true;
    }

    @Override public String toString() {
        return contexts.toString();
    }
}
