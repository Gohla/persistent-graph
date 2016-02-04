package nl.gohla.graph.persistent.internal.dexx.hash;

import java.util.Map.Entry;
import java.util.Set;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Maps;
import com.github.andrewoma.dexx.collection.Pair;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.ISet;
import nl.gohla.graph.persistent.internal.AGraphBuilder;
import nl.gohla.graph.persistent.internal.dexx.DexxGraphNode;

public class DexxHashGraphBuilder<V, E> extends AGraphBuilder<V, E> {
    @Override public IGraph<V, E> build() {
        final Builder<Pair<Integer, IGraphNode<V, E>>, Map<Integer, IGraphNode<V, E>>> builder =
            Maps.<Integer, IGraphNode<V, E>>builder();
        for(Node<V, E> node : nodes.values()) {
            final int nodeId = node.node;
            final DexxHashGraphEdges<E> incEdges = createEdges(node.inc.entrySet());
            final DexxHashGraphEdges<E> outEdges = createEdges(node.out.entrySet());
            final DexxGraphNode<V, E> graphNode = new DexxGraphNode<V, E>(incEdges, nodeId, node.label, outEdges);
            builder.add(new Pair<Integer, IGraphNode<V, E>>(nodeId, graphNode));
        }
        final Map<Integer, IGraphNode<V, E>> map = builder.build();
        final DexxHashIntMap<IGraphNode<V, E>> nodes = new DexxHashIntMap<>(map);
        return new DexxHashGraph<>(nodes);
    }

    private DexxHashGraphEdges<E> createEdges(Set<Entry<Integer, Set<E>>> entries) {
        final Builder<Pair<Integer, ISet<E>>, Map<Integer, ISet<E>>> builder = Maps.<Integer, ISet<E>>builder();
        for(Entry<Integer, Set<E>> entry : entries) {
            final DexxHashSet<E> set = new DexxHashSet<>(entry.getValue());
            builder.add(new Pair<Integer, ISet<E>>(entry.getKey(), set));
        }
        final Map<Integer, ISet<E>> map = builder.build();
        final DexxHashGraphEdges<E> edges = new DexxHashGraphEdges<>(new DexxHashIntMap<>(map));
        return edges;
    }
}
