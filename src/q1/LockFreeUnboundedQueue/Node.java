package q1.LockFreeUnboundedQueue;

import java.util.concurrent.atomic.AtomicReference;

public class Node<T> {
    private final T value;
    private long  enqTime;
    private long deqTime;
    private final AtomicReference<Node> next;

    Node(T value) {
        this.value = value;
        next = new AtomicReference<>(null);
    }

    public long getEnqTime() {
        return enqTime;
    }

    public void setEnqTime(long enqTime) {
        this.enqTime = enqTime;
    }

    public long getDeqTime() {
        return deqTime;
    }

    public void setDeqTime(long deqTime) {
        this.deqTime = deqTime;
    }

    public T getValue() {
        return value;
    }

    public AtomicReference<Node> getNext() {
        return next;
    }

}
