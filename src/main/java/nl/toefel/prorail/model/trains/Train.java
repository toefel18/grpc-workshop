package nl.toefel.prorail.model.trains;

public class Train {

    private final String trainId;
    private Point pos;
    private Direction direction;
    private TrainOperator operator;

    public Train(String trainId) {
        this(trainId, new RandomTrainOperator());
    }

    public Train(String trainId, TrainOperator operator) {
        this.trainId = trainId;
        this.operator = operator;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public String getTrainId() {
        return trainId;
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
