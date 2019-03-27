package nl.toefel.chatroom.unsecure;

import io.grpc.ManagedChannel;
import io.grpc.okhttp.OkHttpChannelBuilder;
import nl.toefel.chatroom.ChatroomCommandLineClient;

import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class UnsecureChatroomClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = OkHttpChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        System.out.print("Enter user name: ");
        var nickname = new Scanner(System.in).nextLine();

        new ChatroomCommandLineClient().start(channel, nickname);
    }
}
