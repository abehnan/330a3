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
            nodes.add(new Node());
            adjArray.add(new ArrayList<>());
        }
    }

    public int getSize() {
        return nodes.size();
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

    // returns true if the node has the same color as any adjacent node with a lower id
    public boolean hasConflictingColor(int nodeID) {
        ArrayList<Integer> adjNodes = adjArray.get(nodeID);
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
        for (ArrayList<Integer> aList : adjArray) {
            count += aList.size();
        }
        // divide by two because we store edges both ways in this implementation
        return count / 2;
    }

    public int getMaxDegree() {
        int maxDegree = 0;
        int id = -1;
        for (int i = 0; i < adjArray.size(); i++) {
            if (adjArray.get(i).size() > maxDegree) {
                maxDegree = adjArray.get(i).size();
                id = i;
            }
        }

        // debug
        System.out.println("node ID with max degree: " + id);
        System.out.println("node color: " + getNodeColor(id));
        System.out.println("adjacent nodes: " + adjArray.get(id));
        System.out.print("adjacent node colors: [");
        for (int i = 0; i < adjArray.get(id).size(); i++) {
            System.out.print(getNodeColor(adjArray.get(id).get(i)));
            if (i < adjArray.get(id).size() - 1) {
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
