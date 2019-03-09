package nl.toefel.trains;

import com.github.davidmoten.geo.LatLong;
import com.google.common.collect.Lists;

import java.util.List;

public class Railway {
    private final String name;
    List<LatLong> sections;

    public Railway(String name, List<LatLong> sections) {
        this.name = name;
        this.sections = sections;
    }

    public static Railway utrechtAmsterdam() {
        List<LatLong> sections = Lists.newArrayList(
                new LatLong(52.095906, 5.103602),
                new LatLong(52.097453, 5.097611),
                new LatLong(52.108228, 5.079871),
                new LatLong(52.110883, 5.075762),
                new LatLong(52.120848, 5.059099),
                new LatLong(52.131273, 5.040948),
                new LatLong(52.141510, 5.023484),
                new LatLong(52.150299, 5.008259),
                new LatLong(52.173398, 4.991046),
                new LatLong(52.187520, 4.994608),
                new LatLong(52.208692, 5.000525),
                new LatLong(52.237827, 5.007886),
                new LatLong(52.248068, 5.004634),
                new LatLong(52.258150, 4.995267),
                new LatLong(52.271235, 4.984027),
                new LatLong(52.279729, 4.976465),
                new LatLong(52.297016, 4.960820),
                new LatLong(52.307880, 4.951089),
                new LatLong(52.317440, 4.942133),
                new LatLong(52.328654, 4.931345));

        return new Railway("utrecht-amsterdam", sections);
    }

    public String getName() {
        return name;
    }

    public LatLong start() {
        return sections.get(0);
    }

    public LatLong end() {
        return sections.get(sections.size() - 1);
    }

    public LatLong next(LatLong fromLocation) {
        if (fromLocation == end()) {
            sections = Lists.reverse(sections); // going back and forth
        }
        return sections.get(sections.indexOf(fromLocation) + 1);
    }

}
