package q1.BlockingUnboundedQueue;

import java.util.concurrent.locks.ReentrantLock;

class BlockingQueue<T> {
    private final ReentrantLock enqLock;
    private final ReentrantLock deqLock;
    private Node<T> head, tail;

    BlockingQueue() {
        head = new Node<>(null);
        tail = head;
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
    }

    public void enq(T value) {
        enqLock.lock();
        try {
            Node<T> e = new Node<>(value);
            e.setEnqTime(System.currentTimeMillis());
            tail.setNext(e);
            tail = e;
        } finally {
            enqLock.unlock();
        }
    }

    public Node<T> deq() throws Exception {
        Node<T> result;
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
