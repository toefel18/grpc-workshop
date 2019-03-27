package nl.toefel.prorail.model.trains;

import nl.toefel.util.Resource;

import java.util.List;

public class TrainMain {

    public static void main(String[] args) throws InterruptedException {
        // Load train track
        List<String> lines = Resource.asLines("/train-track.txt");
        Track track = new Track(lines);

        Train train = new Train("toefel", new RandomTrainOperator());
        train.setPos(new Point(9, 3));
        train.setDirection(Direction.BOTTOM);

        track.addTrain(train);

        while (true) {
            track.print();
            track.moveTrains();
            Thread.sleep(200);
        }
    }
}
