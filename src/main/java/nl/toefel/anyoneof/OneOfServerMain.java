package nl.toefel.anyoneof;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import nl.toefel.oneof.OneOfServiceGrpc;
import nl.toefel.oneof.OneofService;

import java.io.IOException;

public class OneOfServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server service = ServerBuilder.forPort(9900)
                .addService(new OneOfService())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 9900...");
        service.awaitTermination();
    }

    public static class OneOfService extends OneOfServiceGrpc.OneOfServiceImplBase {
        @Override
        public void callWithEither(OneofService.Either request, StreamObserver<OneofService.Either> responseObserver) {
            responseObserver.onNext(request);
            responseObserver.onCompleted();
        }
    }
}
