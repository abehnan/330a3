package q2;

import java.util.ArrayList;
import java.util.Collections;

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

    public boolean addEdge(int srcID, int destID) {
        if (adjArray.get(srcID).contains(destID)) {
            return false;
        }
        adjArray.get(srcID).add(destID);
        adjArray.get(destID).add(srcID);
        return true;
    }

    public boolean containsEdge(int srcID, int destID) {
        return adjArray.get(srcID).contains(destID) &&
                adjArray.get(destID).contains(srcID);
    }

    public void setNodeColor(int nodeID) {
        nodes.get(nodeID).setColor(getSmallestNodeColor(nodeID));
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

    public int getMaxDegree() {
        int maxDegree = 0;
        for (ArrayList<Integer> aList : adjArray) {
            if (aList.size() > maxDegree) {
                maxDegree = aList.size();
            }
        }
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
        ArrayList<Integer> adjNodes = adjArray.get(nodeID);
        ArrayList<Integer> adjColors = new ArrayList<>(adjNodes.size());
        for (Integer node : adjNodes) {
            adjColors.add(nodes.get(node).getColor());
        }
        Collections.sort(adjColors);
        for (Integer color : adjColors) {
            if (color == minColor) {
                minColor++;
            }
        }
        return minColor;
    }
}
