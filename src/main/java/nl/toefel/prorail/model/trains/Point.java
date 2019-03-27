package nl.toefel.prorail.model.trains;

import java.util.Objects;

public final class Point implements Comparable<Point>{

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ')';
    }

    @Override
    public int compareTo(Point o) {
        if (y < o.y) {
           return -1;
        } else if (y > o.y) {
            return 1;
        } else {
            return x - o.x;
        }
    }

    public Point moveTo(Direction direction) {
        switch (direction) {
            case TOP: return new Point(x, y - 1);
            case BOTTOM: return new Point(x, y + 1);
            case LEFT: return new Point(x - 1, y);
            case RIGHT: return new Point(x + 1, y);
            default: throw new IllegalStateException("unknown direction " + direction);
        }
    }
}
