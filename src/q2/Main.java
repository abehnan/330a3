package q2;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Main {

    // adds e random edges to graph
    private static void addEdges(Graph graph, int e) {
        int count = 0;
        while (count < e) {
            int srcNode = ThreadLocalRandom.current().nextInt(graph.getSize());
            int destNode = srcNode;
            while (destNode == srcNode) {
                destNode = ThreadLocalRandom.current().nextInt(graph.getSize());
            }
            if (!graph.addEdge(srcNode, destNode)) {
                continue;
            }
            count++;
        }
    }

    // multithreaded assign algorithm
    private static void assign(Graph graph, int numThreads, ArrayList<Integer> workingSet) {
        if (numThreads == 1) {
            AssignThread thread = new AssignThread(graph, workingSet, 0, workingSet.size());
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            ArrayList<AssignThread> threads = new ArrayList<>(numThreads);
            int sublistSize = workingSet.size() / numThreads + 1;
            for (int start = 0; start < workingSet.size(); start += sublistSize) {
                int end = Math.min(start + sublistSize, workingSet.size());
                threads.add(new AssignThread(graph, workingSet, start, end));
            }
            for (AssignThread thread : threads) {
                thread.start();
            }
            for (AssignThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // multithreaded detectConflicts algorithm
    private static ArrayList<Integer> detectConflicts(Graph graph, int numThreads, ArrayList<Integer> workingSet) {
        if (numThreads == 1) {
            ConflictThread thread = new ConflictThread(graph, workingSet, 0, workingSet.size());
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return thread.getConflictingNodes();
        } else {
            ArrayList<Integer> conflictingNodes = new ArrayList<>();
            ArrayList<ConflictThread> threads = new ArrayList<>(numThreads);
            int sublistSize = workingSet.size() / numThreads + 1;
            for (int start = 0; start < workingSet.size(); start += sublistSize) {
                int end = Math.min(start + sublistSize, workingSet.size());
                threads.add(new ConflictThread(graph, workingSet, start, end));
            }
            for (ConflictThread thread : threads) {
                thread.start();
            }
            for (ConflictThread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                conflictingNodes.addAll(thread.getConflictingNodes());
            }
            return conflictingNodes;
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
        System.out.print("constructing graph (this may take a while)...");
        Graph graph = new Graph(n);
        addEdges(graph, e);
        System.out.println("done!");

        // set up initial conflicting node set
        ArrayList<Integer> conflicting = new ArrayList<>(graph.getSize());
        for (int i = 0; i < graph.getSize(); i++) {
            conflicting.add(i);
        }

        // color graph
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (conflicting.size() > 0) {
            System.out.println("\nsize of conflicting set: " + conflicting.size());
            System.out.println("assign iteration #" + i);
            assign(graph, t, conflicting);
            System.out.println("detectConflicts iteration #" + i++);
            conflicting = detectConflicts(graph, t, conflicting);
        }
        long totalTime = System.currentTimeMillis() - startTime;

        // output
        System.out.println();
        System.out.println("size of graph: " + graph.getSize());
        System.out.println("number of edges: " + graph.getNumEdges());
        System.out.println("max degree: " + graph.getMaxDegree());
        System.out.println("max color: " + graph.getMaxColor());
        System.out.println("total running time: " + totalTime);
    }
}
