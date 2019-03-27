package nl.toefel.postalorder;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import me.dinowernli.grpc.prometheus.Configuration;
import me.dinowernli.grpc.prometheus.MonitoringServerInterceptor;

import java.io.IOException;

public class PostalOrderServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {

        MonitoringServerInterceptor monitoringInterceptor =
                MonitoringServerInterceptor.create(Configuration.cheapMetricsOnly());

        Server service = ServerBuilder.forPort(8081)
                .intercept(monitoringInterceptor)
                .intercept(new DurationInterceptor())
                .intercept(new MetadataInterceptor())
                .addService(new PostalOrderController())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 8081...");
        service.awaitTermination();
    }
}
