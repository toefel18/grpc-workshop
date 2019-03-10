package nl.toefel.util;

import nl.toefel.model.trains.TrainMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Resource {

    public static List<String> asLines(String resourceName) {
        InputStream trainTrackStream = TrainMain.class.getResourceAsStream(resourceName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(trainTrackStream))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
