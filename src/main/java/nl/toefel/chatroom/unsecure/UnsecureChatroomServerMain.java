package nl.toefel.chatroom.unsecure;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import nl.toefel.chatroom.ChatroomController;

import java.io.IOException;

public class UnsecureChatroomServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server service = ServerBuilder.forPort(8080)
                .addService(new ChatroomController())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 8080...");
        service.awaitTermination();
    }
}
