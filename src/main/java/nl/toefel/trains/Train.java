package nl.toefel.trains;

import com.github.davidmoten.geo.LatLong;

public class Train {

    private String trainNumber;
    private Railway currentRail;
    private LatLong currentLocation;

    public Train(String trainNumber, Railway currentRail) {
        this.trainNumber = trainNumber;
        this.currentRail = currentRail;
        this.currentLocation = currentRail.start();
    }

    public LatLong moveToNewLocation() {
        currentLocation = currentRail.next(currentLocation);
        return currentLocation;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public Railway getCurrentRail() {
        return currentRail;
    }

    public LatLong getCurrentLocation() {
        return currentLocation;
    }
}
