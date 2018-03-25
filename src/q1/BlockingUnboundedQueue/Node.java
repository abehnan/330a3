package q1.BlockingUnboundedQueue;

public class Node<T> {
    private T value;
    private long  enqTime;
    private long deqTime;
    private Node next;

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

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
