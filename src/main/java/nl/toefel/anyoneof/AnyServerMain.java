package nl.toefel.anyoneof;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import nl.toefel.postalorder.AnyServiceGrpc;
import nl.toefel.postalorder.AnyServiceOuterClass;

import java.io.IOException;

public class AnyServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server service = ServerBuilder.forPort(8082)
                .addService(new AnyService())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 8082...");
        service.awaitTermination();
    }

    public static class AnyService extends AnyServiceGrpc.AnyServiceImplBase {
        @Override
        public void callWithAny(AnyServiceOuterClass.Payload request, StreamObserver<AnyServiceOuterClass.Payload> responseObserver) {
            responseObserver.onNext(request);
            responseObserver.onCompleted();
        }

        @Override
        public void callWithEither(AnyServiceOuterClass.Either request, StreamObserver<AnyServiceOuterClass.Either> responseObserver) {
            responseObserver.onNext(request);
            responseObserver.onCompleted();
        }
    }
}
