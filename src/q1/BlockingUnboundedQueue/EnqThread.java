package q1.BlockingUnboundedQueue;

import java.util.concurrent.atomic.AtomicInteger;

public class EnqThread extends Thread {
    private static BlockingUnboundedQueue queue = BlockingUnboundedQueue.getInstance();
    private static AtomicInteger idPool = new AtomicInteger(0);
    public static volatile boolean flag = true;

    public void run() {
        while (flag) {
            int currentID = idPool.getAndIncrement();
            queue.enq(currentID);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
