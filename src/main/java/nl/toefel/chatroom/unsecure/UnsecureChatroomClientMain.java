package nl.toefel.chatroom.unsecure;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.chatroom.ChatroomCommandLineClient;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class UnsecureChatroomClientMain {
    public static void main(String[] args) throws InterruptedException, IOException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        var nickname = "unknown";
        if (args.length > 0) {
            nickname = args[0];
        }

        new ChatroomCommandLineClient().start(channel, nickname);
    }
}
