package q1.BlockingUnboundedQueue;

import java.util.LinkedList;

class DeqThread extends Thread {
    private final BlockingQueue<Integer> queue;
    private final LinkedList<Node<Integer>> resultList;
    private final int numItems;
    private int count;

    DeqThread(BlockingQueue<Integer> queue, int numItems) {
        this.numItems = numItems;
        this.queue = queue;
        count = 0;
        resultList = new LinkedList<>();
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

    public void printValues() {
        for (Node<Integer> n : resultList) {
            System.out.println("id: " + n.getValue() + " enq: " + n.getEnqTime() + " deq: " + n.getDeqTime());
        }
    }
}
