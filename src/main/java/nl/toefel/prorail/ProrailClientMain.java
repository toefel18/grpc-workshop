package nl.toefel.prorail;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.trains.ProrailGrpc;
import static nl.toefel.trains.TrainService.*;

public class ProrailClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        ProrailGrpc.ProrailBlockingStub prorailBlockingStub = ProrailGrpc.newBlockingStub(channel);

        GetTrackLayoutRequest request = GetTrackLayoutRequest.newBuilder().build();
        GetTrackLayoutResponse trackLayout = prorailBlockingStub.getTrackLayout(request);
        System.out.println("width: " + trackLayout.getWidth());
        System.out.println("height: " + trackLayout.getHeight());

        trackLayout.getComponentsList().forEach(c -> System.out.println(c.getX() + "," + c.getY() + ": " + c.getChar()));
    }
}
