package nl.toefel.prorail.model.trains;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Example track:
 * <pre>
 *   /---\
 *   |   |  /----\
 *   | /-+--+-\  |
 *   | | |  | |  |
 *   \-+-/  \-+--/
 *     \------/
 * </pre>
 *
 */
public class Track {

    private List<String> infra; // lines from the input (starting at top of the example track)
    private List<Train> trains = new ArrayList<>();

    public Track(List<String> infra) {
        this.infra = infra;
    }

    public char infraElementAt(Point pos) {
        String line = infra.get(pos.getY());
        if (pos.getX() >= line.length() || pos.getX() < 0) {
            return ' ';
        }
        return line.charAt(pos.getX());
    }

    public SortedMap<Point, Character> infraElements() {
        SortedMap<Point, Character> map = new TreeMap<>();
        Point size = size();
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                Point pos = new Point(x, y);
                char infraAt = infraElementAt(pos);
                if (infraAt != ' ') {
                    map.put(pos, infraAt);
                }
            }
        }
        return map;
    }

    public Point size() {
        int y = infra.size();
        int x = infra.stream().mapToInt(String::length).max().getAsInt();
        return new Point(x, y);
    }

    public void print() {
        List<char[]> grid= infra.stream().map(String::toCharArray).collect(Collectors.toList());
        trains.forEach(t -> {
            char[] row = grid.get(t.getPos().getY());
            row[t.getPos().getX()] = t.getDirection().getDirectionSymbol();
        });
        final String ANSI_CLS = "\033[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
        grid.forEach(row -> System.out.println(new String(row)));
        trains.forEach(t -> System.out.println(t.getTrainId() + " has pos " + t.getPos()));
    }

    public boolean addTrain(Train train){
        if (train.getPos() == null) {
            train.setPos(new Point(9, 3));
        }
        if (train.getDirection() == null) {
            train.setDirection(Direction.BOTTOM);
        }
        if (!trains.contains(train)) {
            return trains.add(train);
        }
        return false;
    }

    public void moveTrains() {
        trains.forEach(train -> train.getOperator().moveTrainToNewPosition(train, this));
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void removeTrain(String trainId) {
        trains = trains.stream().filter(t -> !trainId.equals(t.getTrainId())).collect(Collectors.toList());
    }
}
