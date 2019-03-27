package nl.toefel.prorail;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import nl.toefel.prorail.model.trains.*;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.TrainService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProrailController extends ProrailGrpc.ProrailImplBase {

    private final Track track;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private SetMultimap<String, StreamObserver<TrainService.TrainPositionUpdate>> trainObservers = HashMultimap.create();

    public ProrailController(Track track) {
        this.track = track;
        // should not really be here, but for the sake of the example.
        executor.scheduleAtFixedRate(this::moveTrains, 0, 500, TimeUnit.MILLISECONDS);
    }

    private synchronized void moveTrains() {
        track.moveTrains();
        System.out.println("Move train tick");
        track.getTrains().forEach(train -> {
            TrainService.TrainPositionUpdate positionUpdateMessage = TrainService.TrainPositionUpdate.newBuilder()
                    .setX(train.getPos().getX())
                    .setY(train.getPos().getY())
                    .setTrainId(train.getName())
                    .setDirection(TrainService.Direction.valueOf(train.getDirection().name()))
                    .build();

            Set<StreamObserver<TrainService.TrainPositionUpdate>> observers = trainObservers.get(train.getName());
            Set<StreamObserver<TrainService.TrainPositionUpdate>> failedConnections = new HashSet<>();

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


    @Override
    public synchronized void addTrain(TrainService.AddTrainRequest request, StreamObserver<TrainService.AddTrainResponse> responseObserver) {
        Train train = new Train(request.getTrainId(), new RandomTrainOperator());
        train.setPos(new Point(9,3));
        train.setDirection(Direction.BOTTOM);
        if (track.addTrain(train)) {
            responseObserver.onNext(TrainService.AddTrainResponse.newBuilder().setOk(true).build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.FAILED_PRECONDITION
                    .withDescription("Train with id " + request.getTrainId() + " already exists")
                    .asException());
        }
    }

    @Override
    public void getPositionUpdates(TrainService.GetTrainPositionsRequest request, StreamObserver<TrainService.TrainPositionUpdate> responseObserver) {
        if (!track.getTrains().stream().anyMatch(t -> t.getName().equalsIgnoreCase(request.getTrainId()))) {
            responseObserver.onError(Status.FAILED_PRECONDITION
                    .withDescription("Train with id " + request.getTrainId() + " does not exists, create it first")
                    .asException());
        } else {
            trainObservers.get(request.getTrainId()).add(responseObserver);
        }
    }

    @Override
    public void getTrackLayout(TrainService.GetTrackLayoutRequest request, StreamObserver<TrainService.GetTrackLayoutResponse> responseObserver) {
        Point size = track.size();

        TrainService.GetTrackLayoutResponse.Builder trackLayoutResponse = TrainService.GetTrackLayoutResponse.newBuilder()
                .setWidth(size.getX())
                .setHeight(size.getY());

        track.infraElements().forEach((point, infra) -> {
            TrainService.InfraComponent.Builder infraComponentBuilder = TrainService.InfraComponent.newBuilder()
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
}
