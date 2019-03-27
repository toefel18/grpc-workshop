package nl.toefel.lowlevelcalls;

import io.grpc.*;
import nl.toefel.simple.SimpleServiceGrpc;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleRequest;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleResponse;

/**
 * Uses simple_service.proto
 *
 * This example uses the lowlevel API
 * <pre>
 *    call = channel.newCall(unaryMethod, callOptions);
 *    call.start(listener, headers);
 *    call.sendMessage(message);
 *    call.halfClose();
 *    call.request(1);
 *    // wait for listener.onMessage()
 *  </pre>
 */
public class LowlevelCallClientMain {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8830)
                .usePlaintext()
                .build();

        System.out.println("Initial Connection State" + channel.getState(true));

        // creates a new call for the method SomeCall() on the channel
        ClientCall<SimpleRequest, SimpleResponse> call = channel.newCall(SimpleServiceGrpc.getExampleCallMethod(), CallOptions.DEFAULT);

        System.out.println("1 Connection state = " + channel.getState(true));

        // Starts the call, must be called before anything else, receives a listener and metadata
        call.start(new ClientCall.Listener<>() {
            @Override
            public void onHeaders(Metadata headers) {
                System.out.println("onHeaders " + headers);
            }

            @Override
            public void onMessage(SimpleResponse message) {
                System.out.println("onMessage " + message);
            }

            @Override
            public void onClose(Status status, Metadata trailers) {
                System.out.println("onClose status: " + status);
                System.out.println("onClose trailers: " + trailers);
            }

            @Override
            public void onReady() {
                System.out.println("onReady");
            }
        }, new Metadata());

        System.out.println("2 Connection state = " + channel.getState(true));

        // then send a message
        call.sendMessage(SimpleRequest.newBuilder()
                .setSomeNumber(123)
                .putAttributes("sjakie", "in de chocoladefabriek")
                .putAttributes("sjonnie", "bij ma flodder")
                .build());

        System.out.println("3 Connection state = " + channel.getState(true));

        // signal that no more messages will be sent to the server
        call.halfClose();

        System.out.println("4 Connection state = " + channel.getState(true));

        // request 1 response message, this actually kicks-off the call
        call.request(1);

        System.out.println("5 Connection state = " + channel.getState(true));

        Thread.sleep(1000);
        System.out.println("6 Connection state = " + channel.getState(true));
        System.out.println("shutting down");
        channel.shutdownNow();
    }
}
