package q1.LockFreeUnboundedQueue;

import java.util.concurrent.atomic.AtomicReference;

// represents a node contained in a LockFreeQueue
class Node<T> {
    private final T value;
    private long  enqTime;
    private long deqTime;
    private final AtomicReference<Node<T>> next;

    Node(T value) {
        this.value = value;
        next = new AtomicReference<>(null);
    }

    long getEnqTime() {
        return enqTime;
    }

    void setEnqTime(long enqTime) {
        this.enqTime = enqTime;
    }

    long getDeqTime() {
        return deqTime;
    }

    void setDeqTime(long deqTime) {
        this.deqTime = deqTime;
    }

    T getValue() {
        return value;
    }

    AtomicReference<Node<T>> getNext() {
        return next;
    }

}
