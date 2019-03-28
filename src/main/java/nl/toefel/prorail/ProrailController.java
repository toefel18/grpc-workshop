package nl.toefel.prorail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import nl.toefel.prorail.model.trains.Direction;
import nl.toefel.prorail.model.trains.Point;
import nl.toefel.prorail.model.trains.Track;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.ProrailService;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static nl.toefel.trains.ProrailService.*;

public class ProrailController extends ProrailGrpc.ProrailImplBase {

    // next three lines required for
    private final Track track;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
    private SetMultimap<String, StreamObserver<TrainPositionUpdate>> trainObservers = HashMultimap.create();

    // maps trainId -> train //Change object to PassengerTrain
    private LinkedHashMap<String, Object> trains = new LinkedHashMap<>();

    public ProrailController(Track track) {
        this.track = track;
        // should not really be here, but for the sake of the example.
        executor.scheduleAtFixedRate(this::moveTrains, 0, 500, TimeUnit.MILLISECONDS);
    }

    private void sleepSafe(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPositionUpdates(GetTrainPositionsRequest request, StreamObserver<TrainPositionUpdate> responseObserver) {
        if (!track.getTrains().stream().anyMatch(t -> t.getTrainId().equalsIgnoreCase(request.getTrainId()))) {
            responseObserver.onError(Status.FAILED_PRECONDITION
                    .withDescription("Train with id " + request.getTrainId() + " does not exists, create it first")
                    .asException());
        } else {
            trainObservers.get(request.getTrainId()).add(responseObserver);
        }
    }

    @Override
    public void getTrackLayout(GetTrackLayoutRequest request, StreamObserver<GetTrackLayoutResponse> responseObserver) {
        Point size = track.size();

        GetTrackLayoutResponse.Builder trackLayoutResponse = GetTrackLayoutResponse.newBuilder()
                .setWidth(size.getX())
                .setHeight(size.getY());

        track.infraElements().forEach((point, infra) -> {
            InfraComponent.Builder infraComponentBuilder = InfraComponent.newBuilder()
                    .setX(point.getX())
                    .setY(point.getY())
                    .setChar(infra.toString());

            if (infra == '/' && track.infraElementAt(point.moveTo(Direction.RIGHT)) == '-') {
                infraComponentBuilder.setTurnType("bottom-to-right");
            } else if (infra == '/') {
                infraComponentBuilder.setTurnType("top-to-left");
            } else if (infra == '\\' && track.infraElementAt(point.moveTo(Direction.RIGHT)) == '-') {
                infraComponentBuilder.setTurnType("top-to-right");
            } else if (infra == '\\') {
                infraComponentBuilder.setTurnType("bottom-to-left");
            }
            trackLayoutResponse.addComponents(infraComponentBuilder.build());
        });

        responseObserver.onNext(trackLayoutResponse.build());
        responseObserver.onCompleted();
    }


    private synchronized void moveTrains() {
        track.moveTrains();
        track.getTrains().forEach(train -> {
            TrainPositionUpdate positionUpdateMessage = TrainPositionUpdate.newBuilder()
                    .setX(train.getPos().getX())
                    .setY(train.getPos().getY())
                    .setTrainId(train.getTrainId())
                    .setDirection(ProrailService.Direction.valueOf(train.getDirection().name()))
                    .build();

            Set<StreamObserver<TrainPositionUpdate>> observers = trainObservers.get(train.getTrainId());
            Set<StreamObserver<TrainPositionUpdate>> failedConnections = new HashSet<>();

            observers.forEach(observer -> {
                try {
                    System.out.println("Pushing to observer");
                    observer.onNext(positionUpdateMessage);
                } catch (Exception e) {
                    failedConnections.add(observer);
                }
            });

            if (!failedConnections.isEmpty()) {
                System.out.println("Removing failed connections count: " + failedConnections.size());
                observers.removeAll(failedConnections);
            }
        });
    }


}
