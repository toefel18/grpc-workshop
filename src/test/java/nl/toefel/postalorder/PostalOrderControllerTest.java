package nl.toefel.postalorder;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static nl.toefel.postalorder.PostalorderService.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class PostalOrderControllerTest {

    // Will automatically cleanup grpc resources after test
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private String serverName;

    @Before
    public void startServer() throws IOException {
        serverName = InProcessServerBuilder.generateName();

        // Create an in-process instance of the server which exposes the PostalOrderController.
        var grpcServer = grpcCleanup.register(InProcessServerBuilder.forName(serverName)
                .directExecutor()
                .addService(new PostalOrderController())
                .build());

        grpcServer.start();
    }

    @Test
    public void testCreateShipment() {
        var channel = grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build());
        var postalOrderClient = PostalOrderServiceGrpc.newBlockingStub(channel);

        CreateShipmentRequest createShipmentRequest = createShipmentRequestWithWeight(100);

        var shipment = postalOrderClient.createShipment(createShipmentRequest);

        assertThat(shipment.getShipmentId(), containsString("-")); // UUID contains -
        assertThat(shipment.getParcelsList().get(0).getWeighInGrams(), equalTo(100));
    }

    @Test
    public void testCreateShipmentInvalidWeight() {
        var channel = grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build());
        var postalOrderClient = PostalOrderServiceGrpc.newBlockingStub(channel);

        CreateShipmentRequest createShipmentRequest = createShipmentRequestWithWeight(-100);
        try {
            //noinspection ResultOfMethodCallIgnored
            postalOrderClient.createShipment(createShipmentRequest);
            fail("Negative weight should throw an exception");
        } catch (StatusRuntimeException e) {
            assertThat(e.getStatus().getCode(), equalTo(Status.FAILED_PRECONDITION.getCode()));
            assertThat(e.getStatus().getDescription(), equalTo("weight cannot be negative"));
        }
    }

    private CreateShipmentRequest createShipmentRequestWithWeight(int weight) {
        return CreateShipmentRequest.newBuilder()
                .addParcels(Parcel.newBuilder().setWeighInGrams(weight).build())
                .setType(ShipmentType.DELIVERY)
                .build();
    }
}