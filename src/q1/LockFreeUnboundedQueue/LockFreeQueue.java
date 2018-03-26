package q1.LockFreeUnboundedQueue;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {
    private final AtomicReference<Node<T>> head;
    private final AtomicReference<Node<T>> tail;

    LockFreeQueue() {
        Node<T> sentinel = new Node<>(null);
        head = new AtomicReference<>(sentinel);
        tail = new AtomicReference<>(sentinel);
    }

    public void enq(T value) {
        Node<T> node = new Node<>(value);
        while (true) {
            Node<T> last = tail.get();
            Node<T> next = (Node<T>) last.getNext().get();
            if (last == tail.get()) {
                if (next == null) {
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

    public Node<T> deq() throws Exception {
        while(true) {
            Node<T> first = head.get();
            Node<T> last = tail.get();
            Node<T> next = (Node<T>) first.getNext().get();
            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
                        throw new Exception();
                    }
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
