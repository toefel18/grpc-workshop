package nl.toefel.chatroom;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;

import java.util.Scanner;

public class ChatroomCommandLineClient {

    public void start(ManagedChannel channel, String nickname) {

        var stub = ChatroomGrpc.newStub(channel);
        var chatStream = stub.openChat(new StreamObserver<>() {
            @Override
            public void onNext(ChatroomService.Chat value) {
                // new chat received from server
                System.out.println(value.getTime() + " " + value.getName() + "# " + value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Client error" + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed");
            }
        });

        var scanner = new Scanner(System.in);

        while (true) {
            chatStream.onNext(ChatroomService.NewChat.newBuilder()
                    .setName(nickname)
                    .setMessage(scanner.nextLine())
                    .build());
        }
    }
}
