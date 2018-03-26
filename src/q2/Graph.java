package q2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

class Graph {
    private final ArrayList<ArrayList<Integer>> adjArray;
    private final ArrayList<Node> nodes;

    Graph(int size) {
        adjArray = new ArrayList<>(size);
        nodes = new ArrayList<>(size);
        for (AtomicInteger i = new AtomicInteger(); i.get() < size; i.getAndIncrement()) {
            nodes.add(new Node());
            adjArray.add(new ArrayList<>());
        }
    }

    public boolean addEdge(int srcID, int destID) {
        if (containsEdge(srcID, destID)) {
            return false;
        }
        adjArray.get(srcID).add(destID);
        adjArray.get(destID).add(srcID);
        return true;
    }

    private boolean containsEdge(int srcID, int destID) {
        return adjArray.get(srcID).contains(destID) &&
                adjArray.get(destID).contains(srcID);
    }

    public void setNodeColor(int nodeID) {
        nodes.get(nodeID).setColor(getSmallestNodeColor(nodeID));
    }

    public boolean hasConflict(int nodeID) {
        ArrayList<Integer> adjNodes = adjArray.get(nodeID);
        ArrayList<Integer> adjColors = new ArrayList<>(adjNodes.size());
        for (Integer node : adjNodes) {
            adjColors.add(getNodeColor(node));
        }
        return adjColors.contains(getNodeColor(nodeID));
    }

    private int getNodeColor(int nodeID) {
        return nodes.get(nodeID).getColor();
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
            adjColors.add(getNodeColor(node));
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
