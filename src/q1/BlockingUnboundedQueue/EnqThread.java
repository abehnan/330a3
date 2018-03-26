package q1.BlockingUnboundedQueue;

import java.util.concurrent.atomic.AtomicInteger;

class EnqThread extends Thread {
    private final BlockingQueue<Integer> queue;
    private static final AtomicInteger idPool = new AtomicInteger(0);
    public static volatile boolean flag = true;

    EnqThread(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        while (flag) {
            Integer currentID = idPool.getAndIncrement();
            queue.enq(currentID);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
