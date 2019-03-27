package nl.toefel.prorail.model.trains;

abstract class TrainOperator {

    public void moveTrainToNewPosition(Train train, Track track) {
        char currentInfra = track.infraElementAt(train.getPos());

        if (currentInfra == '/' && train.getDirection() == Direction.TOP) {
            train.setDirection(Direction.RIGHT);
        } else if (currentInfra == '/' && train.getDirection() == Direction.LEFT) {
            train.setDirection(Direction.BOTTOM);
        } else if (currentInfra == '/' && train.getDirection() == Direction.BOTTOM) {
            train.setDirection(Direction.LEFT);
        } else if (currentInfra == '/' && train.getDirection() == Direction.RIGHT) {
            train.setDirection(Direction.TOP);
        } else if (currentInfra == '\\' && train.getDirection() == Direction.BOTTOM) {
            train.setDirection(Direction.RIGHT);
        } else if (currentInfra == '\\' && train.getDirection() == Direction.LEFT) {
            train.setDirection(Direction.TOP);
        } else if (currentInfra == '\\' && train.getDirection() == Direction.RIGHT) {
            train.setDirection(Direction.BOTTOM);
        } else if (currentInfra == '\\' && train.getDirection() == Direction.TOP) {
            train.setDirection(Direction.LEFT);
        } else if (currentInfra == '+') {
            train.setDirection(decideDirectionOnSwitch(train, track));
        }
        // when | or -, just follow direction
        train.setPos(train.getPos().moveTo(train.getDirection()));
    }

    protected abstract Direction decideDirectionOnSwitch(Train train, Track track);
}
