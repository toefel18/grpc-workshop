package nl.toefel.prorail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import nl.toefel.prorail.model.trains.Direction;
import nl.toefel.prorail.model.trains.Point;
import nl.toefel.prorail.model.trains.Track;
import nl.toefel.prorail.model.trains.Train;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.ProrailService;

import java.util.ArrayList;
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

    // maps trainId -> train
    private LinkedHashMap<String, PassengerTrain> trains = new LinkedHashMap<>();

    public ProrailController(Track track) {
        this.track = track;
        // should not really be here, but for the sake of the example.
        executor.scheduleAtFixedRate(this::moveTrains, 0, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void createTrain(CreateTrainRequest request, StreamObserver<CreateTrainResponse> responseObserver) {
        var passengerTrain = request.getPassengerTrain();
        if (passengerTrain == null) {
            responseObserver.onError(Status.INVALID_ARGUMENT.augmentDescription("no passenger train in message").asException());
        } else if (trains.containsKey(passengerTrain.getTrainId())) {
            responseObserver.onError(Status.ALREADY_EXISTS.asException());
        } else {
            trains.put(passengerTrain.getTrainId(), passengerTrain);
            // add it to the track, the track will track will update it's position
            track.addTrain(new Train(passengerTrain.getTrainId()));

            responseObserver.onNext(CreateTrainResponse.newBuilder().setTrainId(passengerTrain.getTrainId()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getTrain(GetTrainRequest request, StreamObserver<GetTrainResponse> responseObserver) {
        PassengerTrain passengerTrain = trains.get(request.getTrainId());
        if (passengerTrain == null) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        } else {
            responseObserver.onNext(GetTrainResponse.newBuilder().setTrain(passengerTrain).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteTrain(DeleteTrainRequest request, StreamObserver<DeleteTrainResponse> responseObserver) {
        PassengerTrain removedTrain = trains.remove(request.getTrainId());
        track.removeTrain(request.getTrainId());
        responseObserver.onNext(DeleteTrainResponse.newBuilder()
                .setSuccess(true)
                .setTrainExisted(removedTrain != null)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void listTrains(ListTrainsRequest request, StreamObserver<PassengerTrain> responseObserver) {
        ArrayList<PassengerTrain> trainsCopy = new ArrayList(trains.values());
        trainsCopy.forEach(train -> {
            // simulate slow connection
            sleepSafe(1000);
            responseObserver.onNext(train);
        });
        responseObserver.onCompleted();
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
