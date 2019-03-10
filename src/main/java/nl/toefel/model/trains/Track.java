package nl.toefel.model.trains;

import java.util.*;
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
        trains.forEach(t -> System.out.println(t.getName() + " has pos " + t.getPos()));
    }

    public void addTrain(Train train){
        if (!trains.contains(train)) {
            trains.add(train);
        }
    }

    public void moveTrains() {
        trains.forEach(train -> train.getOperator().moveTrainToNewPosition(train, this));
    }
}
