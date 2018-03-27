package q2;

import java.util.ArrayList;

class AssignThread extends Thread {
    private final Graph graph;
    private final ArrayList<Integer> config;
    private final int start, end;

    AssignThread(Graph graph, ArrayList<Integer> config, int start, int end) {
        this.graph = graph;
        this.config = config;
        this.start = start;
        this.end = end;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            graph.setNodeColor(config.get(i));
        }
    }
}
