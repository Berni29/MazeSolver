public class Cell {
    private boolean top = false;
    private boolean right = false;
    private boolean bottom = false;
    private boolean left = false;
    private boolean visited = false;
    private boolean revisited = false;

    public boolean isRevisited() {
        return revisited;
    }

    public void setRevisited(boolean revisited) {
        this.revisited = revisited;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}