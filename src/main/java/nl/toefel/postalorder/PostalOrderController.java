package nl.toefel.postalorder;

import com.google.common.collect.Maps;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import nl.toefel.postalorder.PostalOrderServiceGrpc.PostalOrderServiceImplBase;

import java.util.Map;
import java.util.UUID;

import static nl.toefel.postalorder.PostalorderService.CreateShipmentRequest;
import static nl.toefel.postalorder.PostalorderService.Shipment;

public class PostalOrderController extends PostalOrderServiceImplBase {

    Map<String, Shipment> shipments = Maps.newHashMap();

    @Override
    public void createShipment(CreateShipmentRequest request, StreamObserver<Shipment> responseObserver) {
        var shipmentBuilder = Shipment.newBuilder()
                .setShipmentId(UUID.randomUUID().toString())
                .setType(PostalorderService.ShipmentType.PICKUP);

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
}
