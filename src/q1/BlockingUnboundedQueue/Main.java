package q1.BlockingUnboundedQueue;

class Main {
    public static void main(String args[]) {
        BlockingQueue<Integer> queue = new BlockingQueue<>();
        int p = -1, q = -1, n = -1;
        long startTime;

        // check args
        try {
            if (args.length < 3)
                throw new Exception("Missing arguments, only "+args.length+" were specified!");
            p = Integer.parseInt(args[0]);
            q = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);
            if (p < 1 || q < 1 || n < 1)
                throw new Exception("p, q and n must be an integer greater than 1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // start enqueue threads
        startTime = System.currentTimeMillis();
        EnqThread[] enqThreads = new EnqThread[p];
        for (int i = 0; i < enqThreads.length; i++) {
            enqThreads[i] = new EnqThread(queue);
            enqThreads[i].start();
        }

        // start dequeue threads
        DeqThread[] deqThreads = new DeqThread[q];
        for (int i = 0; i <deqThreads.length; i++) {
            deqThreads[i] = new DeqThread(queue, n);
            deqThreads[i].start();
        }

        // join dequeue threads
        for (DeqThread dt : deqThreads) {
            try {
                dt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // kill enqueue threads
        EnqThread.flag = false;

        // output
        long totalTime = System.currentTimeMillis() - startTime;
        DeqThread.printValues();
        System.out.println("total running time: " + totalTime);
    }
}
