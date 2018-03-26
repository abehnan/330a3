package q1.LockFreeUnboundedQueue;

import java.util.LinkedList;

class DeqThread extends Thread {
    private final LockFreeQueue<Integer> queue;
    private static final LinkedList<Node<Integer>> resultList = new LinkedList<>();
    private final int numItems;
    private int count;

    DeqThread(LockFreeQueue<Integer> queue, int numItems) {
        this.queue = queue;
        this.numItems = numItems;
        count = 0;
    }

    public void run() {
        Node<Integer> result;
        while (count < numItems) {
            try {
                result = queue.deq();
            } catch(Exception e) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    e.printStackTrace();
                }
                continue;
            }
            count++;
            resultList.add(result);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printValues() {
        int enqPointer = 0, deqPointer = 0;
        System.out.println("[timestamp] [operation] [id]");
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
        while (enqPointer < resultList.size() && deqPointer >= resultList.size()) {
            try {
                System.out.println(resultList.get(enqPointer).getEnqTime() + " enq " + resultList.get(enqPointer).getValue());
                enqPointer++;
            } catch (NullPointerException e) {
                enqPointer++;
            }
        }
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
