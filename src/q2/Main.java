package q2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Main {

    // partitions an ArrayLists of integers into n ArrayLists
    private static ArrayList<ArrayList<Integer>> partitionReferences(ArrayList<Integer> aList, int n) {
        ArrayList<ArrayList<Integer>> refArray = new ArrayList<>(n);

        // base case: only one thread
        if (n == 1) {
            refArray.set(0, aList);
            return refArray;
        }

        int sublistSize = aList.size() / n + 1;
        for (int start = 0; start < aList.size(); start += sublistSize) {
            int end = Math.min(start + sublistSize, aList.size());
            refArray.add(new ArrayList<>(aList.subList(start, end)));
        }
        return refArray;
    }

    // returns the number of non null references in an AtomicReferenceArray
    private static int getRefArraySize(AtomicReferenceArray<Integer> refs) {
        int size = 0;
        for (int i = 0; i < refs.length(); i++) {
            try {
                refs.get(i);
            } catch (NullPointerException e) {
                continue;
            }
            size++;
        }
        return size;
    }


    // multithreaded assign algorithm
    private static void assign(Graph graph, int numThreads, AtomicReferenceArray<Integer> refs) {

        // partition references
        ArrayList<Integer> refArrayList = new ArrayList<>(refs.length());
        for (int i = 0; i < refs.length(); i++) {
            try {
                refArrayList.add(refs.get(i));
            } catch(NullPointerException ignored) {}
        }
        ArrayList<ArrayList<Integer>> threadConfigs = partitionReferences(refArrayList, numThreads);

        // start assign threads
        ArrayList<AssignThread> assignThreads = new ArrayList<>(numThreads);
        for (int i = 0; i < threadConfigs.size(); i++) {
            Integer[] refArray = new Integer[threadConfigs.get(i).size()];
            for (int j = 0; j < refArray.length; j++) {
                refArray[j] = threadConfigs.get(i).get(j);
            }
            AtomicReferenceArray<Integer> config = new AtomicReferenceArray<>(refArray);
            assignThreads.add(new AssignThread(graph, config, getRefArraySize(config)));
            assignThreads.get(i).start();
        }

        // join assign threads
        for (AssignThread assignThread : assignThreads) {
            try {
                assignThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // multithreaded detectConflicts algorithm
    private static AtomicReferenceArray<Integer> detectConflicts(Graph graph, int numThreads, AtomicReferenceArray<Integer> refs) {

        // partition references
        ArrayList<Integer> refArrayList = new ArrayList<>(refs.length());
        for (int i = 0; i < refs.length(); i++) {
            try {
                refArrayList.add(refs.get(i));
            } catch(NullPointerException ignored) {}
        }
        ArrayList<ArrayList<Integer>> threadConfigs = partitionReferences(refArrayList, numThreads);

        // start conflict threads
        AtomicReferenceArray<Integer> newRefs = new AtomicReferenceArray<>(refs.length());
        ArrayList<ConflictThread> conflictThreads = new ArrayList<>(numThreads);
        ConflictThread.resetNewConfigIndex();
        for (int i = 0; i < threadConfigs.size(); i++) {
            Integer[] refArray = new Integer[threadConfigs.get(i).size()];
            for (int j = 0; j < refArray.length; j++) {
                refArray[j] = threadConfigs.get(i).get(j);
            }
            AtomicReferenceArray<Integer> config = new AtomicReferenceArray<>(refArray);
            conflictThreads.add(new ConflictThread(graph, config, getRefArraySize(config), newRefs));
            conflictThreads.get(i).start();
        }

        // join conflict threads
        for(ConflictThread conflictThread : conflictThreads) {
            try {
                conflictThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return newRefs;
    }

    // adds e random edges to graph
    private static void addEdges(Graph graph, int e) {
        int edgeCount = 0;
        while (edgeCount < e) {
            int srcNode = ThreadLocalRandom.current().nextInt(graph.getSize());
            int destNode = srcNode;
            while (destNode == srcNode) {
                destNode = ThreadLocalRandom.current().nextInt(graph.getSize());
            }
            if (!graph.addEdge(srcNode, destNode)) {
                continue;
            }
            edgeCount++;
        }
    }


    public static void main(String[] args) {

        // check args
        int n = -1, e = -1, t = -1;
        try {
            if (args.length < 3)
                throw new Exception("Missing arguments, only "+args.length+" were specified!");
            n = Integer.parseInt(args[0]);
            e = Integer.parseInt(args[1]);
            t = Integer.parseInt(args[2]);
            if (n <= 3) {
                throw new Exception("n must be greater than 3");
            }
            if (e <= 0) {
                throw new Exception("e must be greater than 0");
            }
            if (t <= 0) {
                throw new Exception("t must be greater than 0");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("running q2 with arguments n=" + n + " e=" + e + " t=" + t);

        // construct graph
        Graph graph = new Graph(n);
        addEdges(graph, e);

        // set up initial conflicting node set
        AtomicReferenceArray<Integer> conflicting = new AtomicReferenceArray<>(graph.getSize());
        for (int i = 0; i < conflicting.length(); i++) {
            conflicting.set(i, i);
        }

        // color graph
        long startTime = System.currentTimeMillis();
        while (getRefArraySize(conflicting) > 0) {
            assign(graph, n, conflicting);
            conflicting = detectConflicts(graph, n, conflicting);
        }
        long totalTime = System.currentTimeMillis() - startTime;

        // output
        System.out.println("total running time: " + totalTime);
        System.out.println("max degree: " + graph.getMaxDegree());
        System.out.println("max color: " + graph.getMaxColor());
    }
}
