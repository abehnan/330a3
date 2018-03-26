package q1.LockFreeUnboundedQueue;

class Main {
    public static void main(String args[]) {

        LockFreeQueue<Integer> queue = new LockFreeQueue<>();
        int p = -1, q = -1, n = -1;
        long startTime;

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

        startTime = System.currentTimeMillis();
        EnqThread[] enqThreads = new EnqThread[p];
        for (int i = 0; i < enqThreads.length; i++) {
            enqThreads[i] = new EnqThread(queue);
            enqThreads[i].start();
        }

        DeqThread[] deqThreads = new DeqThread[q];
        for (int i = 0; i <deqThreads.length; i++) {
            deqThreads[i] = new DeqThread(queue, n);
            deqThreads[i].start();
        }

        for (DeqThread dt : deqThreads) {
            try {
                dt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        EnqThread.flag = false;
        long totalTime = System.currentTimeMillis() - startTime;

        System.out.println("total running time: " + totalTime);
        for (DeqThread dt: deqThreads) {
            System.out.println();
            dt.printValues();
        }

    }
}
