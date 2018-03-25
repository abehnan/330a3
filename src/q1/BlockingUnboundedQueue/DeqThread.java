package q1.BlockingUnboundedQueue;

import java.util.LinkedList;

public class DeqThread extends Thread {
    private static BlockingUnboundedQueue queue = BlockingUnboundedQueue.getInstance();
    private LinkedList<Node> resultList;
    private int numItems, count;

    DeqThread(int numItems) {
        this.numItems = numItems;
        count = 0;
        resultList = new LinkedList<>();
    }

    public void run() {
        Node result;
        while (count < numItems) {
            try {
                result = queue.deq();
            } catch(Exception e) {
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
        for (Node n : resultList) {
            System.out.println("id: " + n.getValue() + " enq: " + n.getEnqTime() + " deq: " + n.getDeqTime());
        }
    }
}
