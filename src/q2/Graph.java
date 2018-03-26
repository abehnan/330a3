package q2;

import java.util.ArrayList;

class Graph {
    private final ArrayList<ArrayList<Integer>> adjArray;
    private final ArrayList<Node> nodes;

    Graph(int size) {
        adjArray = new ArrayList<>(size);
        nodes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodes.add(new Node(i));
            adjArray.add(new ArrayList<>());
        }
    }

    public void addEdge(int srcID, int destID) {
        adjArray.get(srcID).add(destID);
        adjArray.get(destID).add(srcID);
    }

    public boolean containsEdge(int srcID, int destID) {
        return adjArray.get(srcID).contains(destID) &&
                adjArray.get(destID).contains(srcID);
    }

    public void setNodeColor(int nodeID, int color) {
        nodes.get(nodeID).setColor(color);
    }

    public int getNodeColor(int nodeID) {
        return nodes.get(nodeID).getColor();
    }

    public ArrayList<Integer> getAdjacentNodes(int nodeID) {
        return adjArray.get(nodeID);
    }

    public int getSize() {
        return nodes.size();
    }

}
