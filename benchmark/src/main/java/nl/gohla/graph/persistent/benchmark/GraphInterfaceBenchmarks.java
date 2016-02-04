package nl.gohla.graph.persistent.benchmark;

import java.util.Map.Entry;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphNode;
import nl.gohla.graph.persistent.benchmark.util.GraphImpls;
import nl.gohla.graph.persistent.benchmark.util.GraphUtil;

@State(Scope.Thread)
public class GraphInterfaceBenchmarks extends BaseState {
    public IGraph<String, String> graph;


    @Setup(Level.Trial) public void setup() throws Exception {
        graph = GraphUtil.generateGraph(numNodes, numEdges, GraphImpls.get(implementation).builder());
    }


    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsNodeSuccess(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsNode(0));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsNodeFailure(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsNode(-1));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsNodeWithLabelSuccess(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsNode(0, "node-label-0"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsNodeWithLabelFailure(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsNode(0, "node-label-bogus"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void addNode(Blackhole hole) throws IllegalStateException {
        hole.consume(graph.addNode(-1, "node-label"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void removeNode(Blackhole hole) throws IllegalStateException {
        hole.consume(graph.removeNode(0));
    }


    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsEdgeSuccess(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsEdge(0, 1));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsEdgeFailure(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsEdge(0, 1));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsEdgeWithLabelSuccess(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsEdge(0, 1, "edge-label-1"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void containsEdgeWithLabelFailure(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.containsEdge(0, 1, "edge-label-bogus"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void addEdge(Blackhole hole) throws IllegalStateException {
        hole.consume(graph.addEdge(0, 1, "edge-label"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void removeLabeledEdge(Blackhole hole)
        throws IllegalStateException {
        hole.consume(graph.removeEdge(0, 1, "edge-label-0"));
    }

    @Benchmark @BenchmarkMode(Mode.Throughput) public void removeEdges(Blackhole hole) throws IllegalStateException {
        hole.consume(graph.removeAllEdges(0, 1));
    }


    @Benchmark @BenchmarkMode(Mode.SingleShotTime) public void iterateNodes(Blackhole hole)
        throws IllegalStateException {
        for(IGraphNode<String, String> node : graph.nodes()) {
            hole.consume(node);
        }
    }

    @Benchmark @BenchmarkMode(Mode.SingleShotTime) public void iterateNodesAndEdges(Blackhole hole)
        throws IllegalStateException {
        for(IGraphNode<String, String> node : graph.nodes()) {
            for(Entry<Integer, ? extends Iterable<String>> edge : node.inc().edges()) {
                hole.consume(edge);
            }
            for(Entry<Integer, ? extends Iterable<String>> edge : node.out().edges()) {
                hole.consume(edge);
            }
        }
    }
}
