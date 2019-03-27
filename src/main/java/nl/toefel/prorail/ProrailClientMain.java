package nl.toefel.prorail;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.ProrailGrpc.ProrailBlockingStub;
import nl.toefel.trains.ProrailGrpc.ProrailStub;

import java.util.concurrent.CountDownLatch;

import static nl.toefel.trains.TrainService.*;

public class ProrailClientMain {
    public static void main(String[] args) throws InterruptedException {
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

        ProrailStub prorailStub = ProrailGrpc.newStub(channel);

        GetTrainPositionsRequest positionRequest = GetTrainPositionsRequest.newBuilder()
                .setTrainId("jaap")
                .build();

        if (addedJaap.getOk()) {
            System.out.println("Added train jaap");
        } else {
            System.out.println("Failed to add train jaap " + addedJaap.getErr());
        }

        CountDownLatch latch = new CountDownLatch(1);

        prorailStub.getPositionUpdates(positionRequest, new StreamObserver<>() {
            @Override
            public void onNext(TrainPositionUpdate value) {
                System.out.println(value.getX() + ", " + value.getY());
            }

            @Override
            public void onError(Throwable t) {
                Status status = Status.fromThrowable(t);
                System.out.println("name: " + status);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Done gracefully");
                latch.countDown();
            }
        });

        latch.await();
        System.out.println("Done!");
    }
}
