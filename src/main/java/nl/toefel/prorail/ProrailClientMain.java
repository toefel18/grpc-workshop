package nl.toefel.prorail;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.ProrailGrpc.ProrailBlockingStub;

import static nl.toefel.trains.ProrailService.GetTrackLayoutRequest;
import static nl.toefel.trains.ProrailService.GetTrackLayoutResponse;

public class ProrailClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        doGetTrackLayout(channel);

        tryCreateTrain(channel, "1234");
    }

    private static void tryCreateTrain(ManagedChannel channel, String trainId) {
        // IMPLEMENT...
    }

    private static void doGetTrackLayout(ManagedChannel channel) {
        ProrailBlockingStub blockingClient = ProrailGrpc.newBlockingStub(channel);

        GetTrackLayoutRequest request = GetTrackLayoutRequest.newBuilder().build();
        GetTrackLayoutResponse trackLayout = blockingClient.getTrackLayout(request);
        System.out.println("width: " + trackLayout.getWidth());
        System.out.println("height: " + trackLayout.getHeight());

        trackLayout.getComponentsList().forEach(c -> System.out.println(c.getX() + "," + c.getY() + ": " + c.getChar()));
    }
}
