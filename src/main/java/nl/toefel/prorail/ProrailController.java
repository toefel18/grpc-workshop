package nl.toefel.prorail;

import io.grpc.stub.StreamObserver;
import nl.toefel.model.trains.Direction;
import nl.toefel.model.trains.Point;
import nl.toefel.model.trains.Track;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.TrainService;

import java.util.Map;
import java.util.SortedMap;

public class ProrailController extends ProrailGrpc.ProrailImplBase {

    private final Track track;

    public ProrailController(Track track) {
        this.track = track;
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
            } else {
                infraComponentBuilder.setTurnType("top-to-left");
            }
            if (infra == '\\' && track.infraElementAt(point.moveTo(Direction.RIGHT)) == '-') {
                infraComponentBuilder.setTurnType("top-to-right");
            } else {
                infraComponentBuilder.setTurnType("bottom-to-left");
            }
            trackLayoutResponse.addComponents(infraComponentBuilder.build());
        });

        responseObserver.onNext(trackLayoutResponse.build());
        responseObserver.onCompleted();
    }
}
