package q1.BlockingUnboundedQueue;

// represents a node contained in a BlockingQueue
class Node<T> {
    private final T value;
    private long  enqTime;
    private long deqTime;
    private Node<T> next;

    Node(T value) {
        this.value = value;
        next = null;
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

    Node<T> getNext() {
        return next;
    }

    void setNext(Node<T> next) {
        this.next = next;
    }
}
