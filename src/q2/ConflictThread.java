package q2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

class ConflictThread extends Thread {
    private final Graph graph;
    private final AtomicReferenceArray<Integer> config;
    private final int start, end;
    private final ArrayList<Integer> newConfig;

    ConflictThread(Graph graph, AtomicReferenceArray<Integer> config, int start, int end, ArrayList<Integer> newConfig) {
        this.graph = graph;
        this.config = config;
        this.start = start;
        this.end = end;
        this.newConfig = newConfig;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            try {
                if (graph.hasConflictingColor(config.get(i))) {
                    newConfig.add(config.get(i));
                }
            } catch (NullPointerException ignored) {}
        }
    }
}
