package nl.toefel.chatroom.tls;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import nl.toefel.chatroom.ChatroomController;

import java.io.File;
import java.io.IOException;

/**
 * See README.md for certificate generation instructions
 */
@SuppressWarnings("Duplicates")
public class TlsChatroomServerMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server service = ServerBuilder.forPort(8443)
                .addService(new ChatroomController())
                // TODO update with the path where your generated certificates are located
                .useTransportSecurity(new File("/tmp/certs/localhost+1.pem"), new File("/tmp/certs/localhost+1-key.pem"))
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(service::shutdownNow));
        System.out.println("Started listening for rpc calls on 8443...");
        service.awaitTermination();
    }
}
