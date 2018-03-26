package q2;

import java.util.concurrent.atomic.AtomicInteger;

class Node {
    private final AtomicInteger color;

    Node() {
        color = new AtomicInteger(0);
    }

    public int getColor() {
        return color.get();
    }

    public void setColor(int color) {
        this.color.set(color);
    }
}
