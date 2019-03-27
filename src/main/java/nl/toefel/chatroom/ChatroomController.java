package nl.toefel.chatroom;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

@SuppressWarnings("ALL")
public class ChatroomController extends ChatroomGrpc.ChatroomImplBase {

    List<StreamObserver<ChatroomService.Chat>> clients = Collections.synchronizedList(new ArrayList<>());

    @Override
    public StreamObserver<ChatroomService.NewChat> openChat(StreamObserver<ChatroomService.Chat> responseObserver) {
        clients.add(responseObserver);

        Context.current().addListener(new Context.CancellationListener() {
            @Override
            public void cancelled(Context context) {
                System.out.println("Cancelled ");
                clients.remove(responseObserver);
            }
        }, Executors.newSingleThreadExecutor());

        return new StreamObserver<>() {
            @Override
            public void onNext(ChatroomService.NewChat value) {
                System.out.println("Received " + value);
                broadcastMessage(value);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Chatroom controller OnError");
                clients.remove(responseObserver);
            }

            @Override
            public void onCompleted() {
                System.out.println("Chatroom controller OnCompleted");
                clients.remove(responseObserver);
            }
        };
    }

    private void broadcastMessage(ChatroomService.NewChat value) {
        ChatroomService.Chat newMessage = ChatroomService.Chat.newBuilder()
                .setName(value.getName())
                .setMessage(value.getMessage())
                .setTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES).toString())
                .build();

        var clientsCopy = new ArrayList<>(clients);

        for (StreamObserver<ChatroomService.Chat> observer : clientsCopy) {
            try {
                observer.onNext(newMessage);
            } catch (Throwable t) {
                System.out.println("Removing observer");
                clients.remove(observer); // something went wrong, remove observer
            }
        }
    }
}