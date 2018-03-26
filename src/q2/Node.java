package q2;

class Node {
    private final int id;
    private int color;

    Node(int id) {
        this.id = id;
        this.color = 0;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
