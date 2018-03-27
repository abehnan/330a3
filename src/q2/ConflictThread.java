package q2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

class ConflictThread extends Thread {
    private final Graph graph;
    private final AtomicReferenceArray<Integer> config;
    private final ArrayList<Integer> newConfig;
    private final int configSize;
    private static final AtomicInteger newConfigIndex = new AtomicInteger(0);

    public static void resetNewConfigIndex() {
        newConfigIndex.set(0);
    }

    ConflictThread(Graph g, AtomicReferenceArray<Integer> config, int configSize, ArrayList<Integer> newConfig) {
        this.graph = g;
        this.config = config;
        this.newConfig = newConfig;
        this.configSize = configSize;
    }

    public void run() {
        for (int i = 0; i < configSize; i++) {
            try {
                if (graph.hasConflictingColor(config.get(i))) {
                    newConfig.add(config.get(i));
                }
            } catch (NullPointerException ignored) {}
        }
    }
}
