package nl.gohla.graph.persistent.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import nl.gohla.graph.persistent.benchmark.util.GraphImpls;
import nl.gohla.graph.persistent.benchmark.util.GraphUtil;

@State(Scope.Thread)
public class GraphCreationBenchmarks extends BaseState {
    @Benchmark @BenchmarkMode(Mode.SingleShotTime) public void createGraph(Blackhole hole)
        throws IllegalStateException {
        hole.consume(GraphUtil.generateGraph(numNodes, numEdges, GraphImpls.get(implementation).builder()));
    }
}
