package nl.toefel.prorail;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import nl.toefel.trains.ProrailGrpc;
import nl.toefel.trains.ProrailGrpc.ProrailBlockingStub;

import java.util.Iterator;

import static nl.toefel.trains.ProrailService.*;

public class ProrailClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        doGetTrackLayout(channel);

        tryCreateTrain(channel, "1234");
        tryCreateTrain(channel, "6789");
        tryCreateTrain(channel, "1234");
        tryCreateTrain(channel, "2345");
        tryCreateTrain(channel, "6589");

        tryGetTrain(channel, "1234");
        tryDeleteTrain(channel, "1234");
        tryDeleteTrain(channel, "1234");
        tryGetTrain(channel, "1234");

        tryListTrains(channel);

//
//
//        AddTrainResponse addedJaap = prorailBlockingStub.addTrain(AddTrainRequest.newBuilder()
//                .setTrainId("jaap")
//                .build());
//
//        ProrailStub prorailStub = ProrailGrpc.newStub(channel);
//
//        GetTrainPositionsRequest positionRequest = GetTrainPositionsRequest.newBuilder()
//                .setTrainId("jaap")
//                .build();
//
//        if (addedJaap.getOk()) {
//            System.out.println("Added train jaap");
//        } else {
//            System.out.println("Failed to add train jaap " + addedJaap.getErr());
//        }
//
//        CountDownLatch latch = new CountDownLatch(1);
//
//        prorailStub.getPositionUpdates(positionRequest, new StreamObserver<>() {
//            @Override
//            public void onNext(TrainPositionUpdate value) {
//                System.out.println(value.getX() + ", " + value.getY());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Status status = Status.fromThrowable(t);
//                System.out.println("name: " + status);
//                latch.countDown();
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("Done gracefully");
//                latch.countDown();
//            }
//        });
//
//        latch.await();
//        System.out.println("Done!");
    }

    private static void tryCreateTrain(ManagedChannel channel, String trainId) {
        CreateTrainRequest createTrainRequest = CreateTrainRequest.newBuilder()
                .setPassengerTrain(PassengerTrain.newBuilder()
                        .setTrainId(trainId)
                        .setWeightInKg(20000)
                        .setMaxCapacity(200)
                        .build())
                .build();


        ProrailBlockingStub blockingClient = ProrailGrpc.newBlockingStub(channel);
        try {
            CreateTrainResponse train = blockingClient.createTrain(createTrainRequest);

            if (trainId.equalsIgnoreCase(train.getTrainId())) {
                System.out.println("train created with id " + trainId);
            }
        } catch (Exception e) {
            Status status = Status.fromThrowable(e);
            System.out.println("Error while creating train " + trainId + ": " + status);
        }
    }

    private static void tryGetTrain(ManagedChannel channel, String trainId) {
        ProrailBlockingStub blockingClient = ProrailGrpc.newBlockingStub(channel);
        GetTrainRequest request = GetTrainRequest.newBuilder().setTrainId(trainId).build();
        try {
            GetTrainResponse train = blockingClient.getTrain(request);
            System.out.println("train " + trainId + " as json: \n" + JsonFormat.printer().print(train));
        } catch (Exception e) {
            Status status = Status.fromThrowable(e);
            System.out.println("failed to get train " + trainId + ": " + status);
        }
    }

    private static void tryDeleteTrain(ManagedChannel channel, String trainId) {
        ProrailBlockingStub blockingClient = ProrailGrpc.newBlockingStub(channel);
        DeleteTrainRequest request = DeleteTrainRequest.newBuilder().setTrainId(trainId).build();
        DeleteTrainResponse response = blockingClient.deleteTrain(request);
        try {
            System.out.println("delete train " + trainId + " result: " + JsonFormat.printer().omittingInsignificantWhitespace().print(response));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    private static void tryListTrains(ManagedChannel channel) {
        ProrailBlockingStub blockingClient = ProrailGrpc.newBlockingStub(channel);
        System.out.println("listing trains (streaming)");
        Iterator<PassengerTrain> trains = blockingClient.listTrains(ListTrainsRequest.newBuilder().build());
        trains.forEachRemaining(System.out::println);
        System.out.println("done streaming trains");
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
