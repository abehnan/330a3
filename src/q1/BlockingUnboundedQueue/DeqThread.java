package q1.BlockingUnboundedQueue;

import java.util.Vector;

// used to create threads that dequeue a specified number of items from a blocking queue
// adds dequeued nodes to resultList
class DeqThread extends Thread {
    private final BlockingQueue<Integer> queue;
    private static final Vector<Node<Integer>> resultList = new Vector<>();
    private final int numItems;
    private int count;

    DeqThread(BlockingQueue<Integer> queue, int numItems) {
        this.numItems = numItems;
        this.queue = queue;
        count = 0;
    }

    public void run() {
        Node<Integer> result;
        while (count < numItems) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            try {
                result = queue.deq();
            } catch(Exception e) {
                // if queue is empty...
                continue;
            }
            count++;
            resultList.add(result);
        }
    }

    // prints out operations performed on nodes from all DeqThreads in increasing chronological order
    // not thread-safe
    static void printValues() {
        int enqPointer = 0, deqPointer = 0;
        System.out.println("[timestamp] [operation] [id]");
        // initial case: enqueue and dequeue operations remaining
        while (enqPointer < resultList.size() && deqPointer < resultList.size()) {
            long enqTime, deqTime;
            try {
                enqTime = resultList.get(enqPointer).getEnqTime();
            } catch(NullPointerException e) {
                enqPointer++;
                continue;
            }
            try {
                deqTime = resultList.get(deqPointer).getDeqTime();
            } catch(NullPointerException e) {
                deqPointer++;
                continue;
            }
            if (enqTime <= deqTime) {
                System.out.println(resultList.get(enqPointer).getEnqTime() + " enq " + resultList.get(enqPointer).getValue());
                enqPointer++;
            }
            else {
                System.out.println(resultList.get(deqPointer).getDeqTime() + " deq " + resultList.get(deqPointer).getValue());
                deqPointer++;
            }

        }
        // if there are still enqueue operations remaining
        while (enqPointer < resultList.size() && deqPointer >= resultList.size()) {
            try {
                System.out.println(resultList.get(enqPointer).getEnqTime() + " enq " + resultList.get(enqPointer).getValue());
                enqPointer++;
            } catch (NullPointerException e) {
                enqPointer++;
            }
        }
        // if there are still dequeue operations remaining
        while(enqPointer >= resultList.size() && deqPointer < resultList.size()) {
            try {
                System.out.println(resultList.get(deqPointer).getDeqTime() + " deq " + resultList.get(deqPointer).getValue());
                deqPointer++;
            } catch (NullPointerException e) {
                deqPointer++;
            }
        }
        System.out.println("[timestamp] [operation] [id]");
    }
}
