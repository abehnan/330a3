package q1.BlockingUnboundedQueue;

public class Node<T> {
    private final T value;
    private long  enqTime;
    private long deqTime;
    private Node<T> next;

    Node(T value) {
        this.value = value;
        next = null;
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

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
