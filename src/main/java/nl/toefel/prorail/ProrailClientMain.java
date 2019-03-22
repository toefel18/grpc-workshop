package nl.toefel.prorail;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.ProrailGrpc.ProrailBlockingStub;

import static nl.toefel.trains.TrainService.*;

public class ProrailClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        ProrailBlockingStub prorailBlockingStub = ProrailGrpc.newBlockingStub(channel);

        GetTrackLayoutRequest request = GetTrackLayoutRequest.newBuilder().build();
        GetTrackLayoutResponse trackLayout = prorailBlockingStub.getTrackLayout(request);
        System.out.println("width: " + trackLayout.getWidth());
        System.out.println("height: " + trackLayout.getHeight());

        trackLayout.getComponentsList().forEach(c -> System.out.println(c.getX() + "," + c.getY() + ": " + c.getChar()));

        AddTrainResponse addedJaap = prorailBlockingStub.addTrain(AddTrainRequest.newBuilder()
                .setTrainId("jaap")
                .build());

        if (addedJaap.getOk()) {
            System.out.println("Added train jaap");
        } else {
            System.out.println("Failed to add train jaap " + addedJaap.getErr());
        }
    }
}
