package nl.gohla.graph.persistent.benchmark.util;

import nl.gohla.graph.persistent.IGraph;
import nl.gohla.graph.persistent.IGraphBuilder;

public class GraphUtil {
    public static IGraph<String, String> generateGraph(final int numNodes, final int numEdges,
        IGraphBuilder<String, String> builder) throws IllegalStateException {
        for(int node = 0; node < numNodes; ++node) {
            builder.addNode(node, "node-label-" + node);
        }
        for(int e = 0; e < numEdges; ++e) {
            final int base;
            if(e > (numNodes - 2)) {
                base = e % (numNodes - 1);
            } else {
                base = e;
            }
            final int srcNode = base;
            final int dstNode = base + 1;
            builder.addEdge(srcNode, dstNode, "edge-label-" + e);
        }
        return builder.build();
    }
}
