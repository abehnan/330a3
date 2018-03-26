package q2;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        int n = -1, e = -1, t;
        long startTime;
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

        // construct graph and add e random edges
        Graph graph = new Graph(n);
        for (int i = 0; i < e; i++) {
            int srcNode = ThreadLocalRandom.current().nextInt(n);
            int destNode = srcNode;
            while (destNode == srcNode) {
                destNode = ThreadLocalRandom.current().nextInt(n);
            }
            graph.addEdge(srcNode, destNode);
        }
        startTime = System.currentTimeMillis();

        // TODO

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("total running time: " + totalTime);
        System.out.println("max degree: " + graph.getMaxDegree());
        System.out.println("max color: " + graph.getMaxColor());
    }
}
