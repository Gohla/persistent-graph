package nl.gohla.graph.persistent.internal.dexx.sorted;

import java.util.Map.Entry;
import java.util.Set;

import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Pair;
import com.github.andrewoma.dexx.collection.SortedMap;
import com.github.andrewoma.dexx.collection.SortedMaps;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.ISet;
import nl.gohla.graph.persistent.internal.AGraphBuilder;
import nl.gohla.graph.persistent.internal.dexx.DexxGraphNode;

public class DexxSortedGraphBuilder<V, E extends Comparable<E>> extends AGraphBuilder<V, E> {
    @Override public IGraph<V, E> build() {
        final Builder<Pair<Integer, IGraphNode<V, E>>, SortedMap<Integer, IGraphNode<V, E>>> builder =
            SortedMaps.<Integer, IGraphNode<V, E>>builder();
        for(Node<V, E> node : nodes.values()) {
            final int nodeId = node.node;
            final DexxSortedGraphEdges<E> incEdges = createEdges(node.inc.entrySet());
            final DexxSortedGraphEdges<E> outEdges = createEdges(node.out.entrySet());
            final DexxGraphNode<V, E> graphNode = new DexxGraphNode<V, E>(incEdges, nodeId, node.label, outEdges);
            builder.add(new Pair<Integer, IGraphNode<V, E>>(nodeId, graphNode));
        }
        final SortedMap<Integer, IGraphNode<V, E>> map = builder.build();
        final DexxSortedIntMap<IGraphNode<V, E>> nodes = new DexxSortedIntMap<>(map);
        return new DexxSortedGraph<>(nodes);
    }

    private DexxSortedGraphEdges<E> createEdges(Set<Entry<Integer, Set<E>>> entries) {
        final Builder<Pair<Integer, ISet<E>>, SortedMap<Integer, ISet<E>>> builder =
            SortedMaps.<Integer, ISet<E>>builder();
        for(Entry<Integer, Set<E>> entry : entries) {
            final DexxSortedSet<E> set = new DexxSortedSet<>(entry.getValue());
            builder.add(new Pair<Integer, ISet<E>>(entry.getKey(), set));
        }
        final SortedMap<Integer, ISet<E>> map = builder.build();
        final DexxSortedGraphEdges<E> edges = new DexxSortedGraphEdges<>(new DexxSortedIntMap<>(map));
        return edges;
    }
}
