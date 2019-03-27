package nl.toefel.postalorder;

import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import nl.toefel.postalorder.PostalOrderServiceGrpc.PostalOrderServiceImplBase;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

import static nl.toefel.postalorder.PostalorderService.CreateShipmentRequest;
import static nl.toefel.postalorder.PostalorderService.Shipment;

public class PostalOrderController extends PostalOrderServiceImplBase {

    Map<String, Shipment> shipments = Maps.newHashMap();

    @Override
    public void createShipment(CreateShipmentRequest request, StreamObserver<Shipment> responseObserver) {
        System.out.println("Create shipment " + Constants.CTX_API_KEY.get());

        var shipmentBuilder = Shipment.newBuilder()
                .setShipmentId(UUID.randomUUID().toString())
                .setType(request.getType());

        // transform all parcels in the request
        for (PostalorderService.Parcel parcel : request.getParcelsList()) {
            if (parcel.getWeighInGrams() <= 0) {
                responseObserver.onError(Status.FAILED_PRECONDITION.withDescription("weight cannot be negative").asException());
                return;
            }

            shipmentBuilder.addParcels(parcel.toBuilder()
                    .setId(UUID.randomUUID().toString())
                    .setWeighInGrams(parcel.getWeighInGrams()));
        }

        var shipment = shipmentBuilder.build();
        shipments.put(shipment.getShipmentId(), shipment);

        responseObserver.onNext(shipment);
        responseObserver.onCompleted();
    }

    @Override
    public void getStatusUpdates(PostalorderService.ShipmentStatusRequest request, StreamObserver<PostalorderService.ParcelStatus> responseObserver) {

        responseObserver.onNext(PostalorderService.ParcelStatus.newBuilder()
                .setStatus(PostalorderService.Status.CREATED)
                .setShipmentId(request.getShipmentId())
                .setDelivery(PostalorderService.ProofOfDeliveryDetails.newBuilder()
                        .setSignedBy("Christophe")
                        .setSignature(ByteString.copyFrom("Bletmule", Charset.defaultCharset()))
                        .build())
                .build());

        responseObserver.onNext(PostalorderService.ParcelStatus.newBuilder()
                .setStatus(PostalorderService.Status.CREATED)
                .setShipmentId(request.getShipmentId())
                .setTrain(PostalorderService.OnTrainDetails.newBuilder()
                        .setTrain("1234")
                        .setGeolocation("1,2")
                        .build())
                .build());

        responseObserver.onCompleted();
    }
}
