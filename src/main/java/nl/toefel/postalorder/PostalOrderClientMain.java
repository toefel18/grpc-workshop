package nl.toefel.postalorder;

import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.Status;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static io.grpc.stub.MetadataUtils.attachHeaders;
import static nl.toefel.postalorder.PostalorderService.*;

@SuppressWarnings("Duplicates")
public class PostalOrderClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        var parcelClient = PostalOrderServiceGrpc.newBlockingStub(channel)
                .withDeadlineAfter(1000, TimeUnit.MILLISECONDS);

        try {
            var createShipmentRequest = CreateShipmentRequest.newBuilder()
                    .setType(ShipmentType.DELIVERY)
                    .addParcels(Parcel.newBuilder().setWeighInGrams(100))
                    .build();

            Metadata meta = new Metadata();
            meta.put(Constants.API_KEY, "apikey-value");

            var shipment = attachHeaders(parcelClient, meta).createShipment(createShipmentRequest);
            System.out.println(shipment);
            System.out.println(JsonFormat.printer().print(shipment));
//
//            Iterator<ParcelStatus> statusUpdatesStream = parcelClient.getStatusUpdates(ShipmentStatusRequest.newBuilder()
//                    .setShipmentId(shipment.getShipmentId())
//                    .build());
//            System.out.println(statusUpdatesStream.next().getDetailsCase());
//            System.out.println(statusUpdatesStream.next().getDetailsCase());

        } catch (Exception e) {
            var status = Status.fromThrowable(e);
            System.out.println(status.getCode());
            System.out.println(status.getCause());
            System.out.println(status.getDescription());
        }
    }
}
