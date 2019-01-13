package nl.toefel.postalorder;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static nl.toefel.postalorder.PostalorderService.*;
import static nl.toefel.postalorder.PostalorderService.CreateShipmentRequest;

@SuppressWarnings("Duplicates")
public class PostalOrderClientMain {
    public static void main(String[] args) throws InterruptedException, IOException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        var parcelClient = PostalOrderServiceGrpc.newBlockingStub(channel)
                .withDeadlineAfter(1000, TimeUnit.MILLISECONDS);

        var shipment = parcelClient.createShipment(CreateShipmentRequest.newBuilder()
                .setType(ShipmentType.DELIVERY)
                .addParcels(Parcel.newBuilder().setWeighInGrams(100))
                .addParcels(Parcel.newBuilder().setWeighInGrams(-200))
                .build());

        System.out.println(shipment);
    }
}
