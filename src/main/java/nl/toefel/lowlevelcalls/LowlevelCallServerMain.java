package nl.toefel.lowlevelcalls;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import nl.toefel.simple.SimpleServiceGrpc;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleRequest;
import nl.toefel.simple.SimpleServiceOuterClass.SimpleResponse;

import java.io.IOException;

public class LowlevelCallServerMain {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(8830)
                .addService(new LowlevelCallController())
                .build();

        server.start();
        System.out.println("Started listening for rpcs at 8830");
        server.awaitTermination();
    }
}

class LowlevelCallController extends SimpleServiceGrpc.SimpleServiceImplBase {
    @Override
    public void exampleCall(SimpleRequest request, StreamObserver<SimpleResponse> responseObserver) {
        try {
            System.out.println("received " + JsonFormat.printer().print(request));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        SimpleResponse response = SimpleResponse.newBuilder()
                .setResult("im done")
                .build();
        System.out.println("returning " + response);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
