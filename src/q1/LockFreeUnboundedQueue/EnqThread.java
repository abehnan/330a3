package q1.LockFreeUnboundedQueue;

import java.util.concurrent.atomic.AtomicInteger;

// used to create threads that will continuously enqueue items to a BlockingQueue
// stops running when flag is set to false
class EnqThread extends Thread {
    private final LockFreeQueue<Integer> queue;
    private static final AtomicInteger idPool = new AtomicInteger(0);
    static volatile boolean flag = true;

    EnqThread(LockFreeQueue<Integer> queue) {
        this.queue = queue;
    }

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
