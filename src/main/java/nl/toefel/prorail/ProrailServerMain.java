package nl.toefel.prorail;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import nl.toefel.prorail.model.trains.Track;
import nl.toefel.util.Resource;

import java.io.IOException;
import java.util.List;

public class ProrailServerMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> lines = Resource.asLines("/train-track.txt");
        Track track = new Track(lines);

        Server service = ServerBuilder.forPort(8082)
                .addService(new ProrailController(track))
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 8082...");
        service.awaitTermination();
    }
}
