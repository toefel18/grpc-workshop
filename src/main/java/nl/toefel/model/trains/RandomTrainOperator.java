package nl.toefel.model.trains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomTrainOperator extends TrainOperator {
    private Random random = new Random();

    @Override
    protected Direction decideDirectionOnSwitch(Train train, Track track) {
        List<Direction> availableNewDirections = new ArrayList<>(Arrays.asList(Direction.values()));
        availableNewDirections.remove(train.getDirection().opposite());
        int idx = Math.abs(random.nextInt()) % availableNewDirections.size();
        return availableNewDirections.get(idx);
    }
}
