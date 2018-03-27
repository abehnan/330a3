package q2;

import java.util.ArrayList;

class ConflictThread extends Thread {
    private final Graph graph;
    private final ArrayList<Integer> config;
    private final int start, end;
    private final ArrayList<Integer> conflictingNodes;

    ConflictThread(Graph graph, ArrayList<Integer> config, int start, int end) {
        this.graph = graph;
        this.config = config;
        this.start = start;
        this.end = end;
        this.conflictingNodes = new ArrayList<>();
    }

    public ArrayList<Integer> getConflictingNodes() {
        return conflictingNodes;
    }

    public void run() {
        for (int i = start; i < end; i++) {
            if (graph.hasConflictingColor(config.get(i))) {
                conflictingNodes.add(config.get(i));
            }
        }
    }
}
