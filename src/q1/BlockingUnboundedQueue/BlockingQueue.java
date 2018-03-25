package q1.BlockingUnboundedQueue;

import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    private final ReentrantLock enqLock;
    private final ReentrantLock deqLock;
    private Node head, tail;
    private static BlockingQueue instance;

    private BlockingQueue() {
        //noinspection unchecked
        head = new Node(null);
        tail = head;
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
    }

    public static BlockingQueue getInstance() {
        if (instance == null)
            instance = new BlockingQueue();
        return instance;
    }

    public void enq(int value) {
        enqLock.lock();
        try {
            Node<Integer> e = new Node<>(value);
            e.setEnqTime(System.currentTimeMillis());
            tail.setNext(e);
            tail = e;
        } finally {
            enqLock.unlock();
        }
    }

    public Node deq() throws Exception {
        Node result;
        deqLock.lock();
        try {
            if (head.getNext() == null) {
                throw new Exception();
            }
            result = head.getNext();
            result.setDeqTime(System.currentTimeMillis());
            head = head.getNext();
        } finally {
            deqLock.unlock();
        }
        return result;

    }
}
