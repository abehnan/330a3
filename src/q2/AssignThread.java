package q2;

import java.util.concurrent.atomic.AtomicReferenceArray;

class AssignThread extends Thread {
    private final AtomicReferenceArray<Integer> config;
    private final Graph g;
    private final int configSize;

    AssignThread(Graph g, AtomicReferenceArray<Integer> configuration, int configSize) {
        this.config = configuration;
        this.g = g;
        this.configSize = configSize;
    }

    public void run() {
        for (int i = 0; i < configSize; i++) {
            try {
                g.setNodeColor(config.get(i));
            } catch (NullPointerException ignored) {}
        }
    }
}
