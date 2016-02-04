package nl.gohla.graph.persistent.benchmark;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import nl.gohla.graph.persistent.benchmark.util.GraphImpls;

@State(Scope.Thread)
public class BaseState {
    @Param({ "500000" }) public int numNodes;
    @Param({ "1000000" }) public int numEdges;
    @Param({ GraphImpls.pcHashImpl, GraphImpls.pcTreeImpl, GraphImpls.dexxHashImpl,
        GraphImpls.dexxTreeImpl }) public String implementation;
}
