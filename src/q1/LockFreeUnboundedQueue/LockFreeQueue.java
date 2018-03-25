package q1.LockFreeUnboundedQueue;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue {
    private final AtomicReference<Node> head;
    private final AtomicReference<Node> tail;
    private static LockFreeQueue instance;

    private LockFreeQueue() {
        Node<Integer> sentinel = new Node<>(null);
        head = new AtomicReference<>(sentinel);
        tail = new AtomicReference<>(sentinel);
    }

    public static LockFreeQueue getInstance() {
        if (instance == null)
            instance = new LockFreeQueue();
        return instance;
    }

    public void enq(int value) {
        Node<Integer> node = new Node<>(value);
        while (true) {
            Node last = tail.get();
            Node next = (Node) last.getNext().get();
            if (last == tail.get()) {
                if (next == null) {
                    //noinspection unchecked
                    if (last.getNext().compareAndSet(next, node)) {
                        tail.compareAndSet(last, node);
                        node.setEnqTime(System.currentTimeMillis());
                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    public Node deq() throws Exception {
        while(true) {
            Node first = head.get();
            Node last = tail.get();
            Node next = (Node) first.getNext().get();
            if (first == head.get()) {
                if (first == last) {
                    if (next == null)
                        throw new Exception();

                    tail.compareAndSet(last, next);
                } else {
                    if (head.compareAndSet(first, next)) {
                        next.setDeqTime(System.currentTimeMillis());
                        return next;
                    }
                }
            }
        }
    }
}
