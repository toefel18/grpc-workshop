package nl.toefel.model.trains;

public class Train {

    private final String name;
    private Point pos;
    private Direction direction;
    private TrainOperator operator;

    public Train(String name, TrainOperator operator) {
        this.name = name;
        this.operator = operator;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public Point getPos() {
        return pos;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public TrainOperator getOperator() {
        return operator;
    }
}
