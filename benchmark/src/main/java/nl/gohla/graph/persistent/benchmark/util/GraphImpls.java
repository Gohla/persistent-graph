package nl.gohla.graph.persistent.benchmark.util;

import java.util.HashMap;
import java.util.Map;

import nl.gohla.graph.persistent.IGraphFactory;
import nl.gohla.graph.persistent.internal.dexx.hash.DexxHashGraphFactory;
import nl.gohla.graph.persistent.internal.dexx.sorted.DexxSortedGraphFactory;
import nl.gohla.graph.persistent.internal.pcollections.hash.PCHashGraphFactory;
import nl.gohla.graph.persistent.internal.pcollections.tree.PCTreeGraphFactory;

public class GraphImpls {
    public static final String pcHashImpl = "PCollectionsHashed";
    public static final String pcTreeImpl = "PCollectionsTree";
    public static final String dexxHashImpl = "DexxHashed";
    public static final String dexxTreeImpl = "DexxTree";

    private static final Map<String, IGraphFactory<String, String>> impls = new HashMap<>();


    static {
        impls.put(pcHashImpl, new PCHashGraphFactory<String, String>());
        impls.put(pcTreeImpl, new PCTreeGraphFactory<String, String>());
        impls.put(dexxHashImpl, new DexxHashGraphFactory<String, String>());
        impls.put(dexxTreeImpl, new DexxSortedGraphFactory<String, String>());
    }


    public static IGraphFactory<String, String> get(String impl) {
        return impls.get(impl);
    }
}
