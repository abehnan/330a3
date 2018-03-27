package q2;

import java.util.concurrent.atomic.AtomicReferenceArray;

class AssignThread extends Thread {
    private final Graph graph;
    private final AtomicReferenceArray<Integer> config;
    private final int start, end;

    AssignThread(Graph graph, AtomicReferenceArray<Integer> config, int start, int end) {
        this.graph = graph;
        this.config = config;
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            try {
                graph.setNodeColor(config.get(i));
            } catch (NullPointerException ignored) {}
        }
    }
}
