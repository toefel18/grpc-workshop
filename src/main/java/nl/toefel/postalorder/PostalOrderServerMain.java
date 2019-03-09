package nl.toefel.postalorder;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PostalOrderServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server service = ServerBuilder.forPort(8081)
                .intercept(new DurationInterceptor())
                .addService(new PostalOrderController())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 8081...");
        service.awaitTermination();
    }
}
