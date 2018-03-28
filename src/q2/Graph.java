package q2;

import java.util.ArrayList;
import java.util.Collections;

class Graph {
    private final ArrayList<ArrayList<Integer>> adjMatrix;
    private final ArrayList<Node> nodes;

    Graph(int size) {
        adjMatrix = new ArrayList<>(size);
        nodes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            nodes.add(new Node());
            adjMatrix.add(new ArrayList<>());
        }
    }

    public int getSize() {
        return nodes.size();
    }

    public boolean addEdge(int srcID, int destID) {
        if (containsEdge(srcID, destID)) {
            return false;
        }
        adjMatrix.get(srcID).add(destID);
        adjMatrix.get(destID).add(srcID);
        return true;
    }

    private boolean containsEdge(int srcID, int destID) {
        return adjMatrix.get(srcID).contains(destID) &&
                adjMatrix.get(destID).contains(srcID);
    }

    public void setNodeColor(int nodeID) {
        nodes.get(nodeID).setColor(getSmallestNodeColor(nodeID));
    }

    // returns true if the node has the same color as any adjacent node with a lower id
    public boolean hasConflictingColor(int nodeID) {
        ArrayList<Integer> adjNodes = adjMatrix.get(nodeID);
        for (Integer adjNodeID : adjNodes) {
            if (getNodeColor(nodeID) == getNodeColor(adjNodeID) && adjNodeID < nodeID) {
                return true;
            }
        }
        return false;
    }

    private int getNodeColor(int nodeID) {
        return nodes.get(nodeID).getColor();
    }

    public int getNumEdges() {
        int count = 0;
        for (ArrayList<Integer> adjList : adjMatrix) {
            count += adjList.size();
        }
        // divide by two because we store edges both ways in this implementation
        return count / 2;
    }

    public int getMaxDegree() {
        int maxDegree = 0;
        int id = -1;
        for (int i = 0; i < adjMatrix.size(); i++) {
            if (adjMatrix.get(i).size() > maxDegree) {
                maxDegree = adjMatrix.get(i).size();
                id = i;
            }
        }

        // debug
        System.out.println("node ID with max degree: " + id);
        System.out.println("node color: " + getNodeColor(id));
        System.out.println("adjacent nodes: " + adjMatrix.get(id));
        System.out.print("adjacent node colors: [");
        for (int i = 0; i < adjMatrix.get(id).size(); i++) {
            System.out.print(getNodeColor(adjMatrix.get(id).get(i)));
            if (i < adjMatrix.get(id).size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        return maxDegree;
    }

    public int getMaxColor() {
        ArrayList<Integer> colors = new ArrayList<>();
        for (Node n : nodes) {
            colors.add(n.getColor());
        }
        Collections.sort(colors);
        return colors.get(colors.size() - 1);
    }

    private int getSmallestNodeColor(int nodeID) {
        int minColor = 1;
        ArrayList<Integer> adjNodes = adjMatrix.get(nodeID);
        ArrayList<Integer> adjColors = new ArrayList<>(adjNodes.size());
        for (Integer adjNode : adjNodes) {
            adjColors.add(getNodeColor(adjNode));
        }
        Collections.sort(adjColors);
        for (Integer adjColor : adjColors) {
            if (adjColor == minColor) {
                minColor++;
            }
        }
        return minColor;
    }
}
