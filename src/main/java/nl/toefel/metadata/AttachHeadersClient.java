package nl.toefel.metadata;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import nl.toefel.simple.SimpleServiceGrpc;
import nl.toefel.simple.SimpleServiceOuterClass;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleRequest;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleResponse;

/**
 * Metadata is comparable to Headers in HTTP. Metadata in grpc is meant for things like
 * api keys, tracing, etc. metadata is available in
 */
public class AttachHeadersClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8831)
                .usePlaintext()
                .build();

        var client = SimpleServiceGrpc.newBlockingStub(channel);

        var metadata = new Metadata();
        metadata.put(Constants.META_ACCEPT_LANG, "excuse-my-french");
        var clientWithMetadata = MetadataUtils.attachHeaders(client, metadata);

        var request = SimpleRequest.newBuilder().build();
        SimpleResponse response = clientWithMetadata.exampleCall(request);

        System.out.println(response.getResult());
    }
}
