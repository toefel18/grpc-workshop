package nl.toefel.model.trains;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class TrainMain {

    public static void main(String[] args) throws InterruptedException {
        List<String> lines = resourceAsLines("/train-track.txt");
        Track track = new Track(lines);
        Train train = new Train("toefel", new RandomTrainOperator());
        train.setPos(new Point(9,3));
        train.setDirection(Direction.BOTTOM);
        track.addTrain(train);
        while (true) {
            track.print();
            track.moveTrains();
            Thread.sleep(200);
        }
    }

    public static List<String> resourceAsLines(String resourceName) {
        InputStream trainTrackStream = TrainMain.class.getResourceAsStream(resourceName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(trainTrackStream))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
