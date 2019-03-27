package nl.toefel.metadata;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import nl.toefel.simple.SimpleServiceGrpc;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleRequest;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleResponse;

import java.io.IOException;

public class InterceptorExampleServerMain {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(8831)
                .intercept(new AcceptLanguageServerInterceptor())
                .addService(new InterceptorExampleController())
                .build();

        server.start();

        System.out.println("Started listening for rpcs at 8831");
        server.awaitTermination();
    }
}

class InterceptorExampleController extends SimpleServiceGrpc.SimpleServiceImplBase {
    @SuppressWarnings("Duplicates")
    @Override
    public void exampleCall(SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
        var response = SimpleResponse.newBuilder()
                .setResult("the accept language received on the server via metadata was: " + Constants.CTX_ACCEPT_LANG.get())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
