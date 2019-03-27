package nl.toefel.anyoneof;

import com.google.protobuf.Any;
import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.any.AnyServiceGrpc;
import nl.toefel.any.AnyServiceOuterClass.Payload;
import nl.toefel.chatroom.ChatroomService;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class AnyClientMain {
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9901)
                .usePlaintext()
                .build();


        var client = AnyServiceGrpc.newBlockingStub(channel);

        // we use the chatroom chat DTO and pack it into an any field
        var payload = Payload.newBuilder()
                .setObj(Any.pack(ChatroomService.Chat.newBuilder()
                        .setMessage("Hallo")
                        .setName("Chefke")
                        .build()))
                .build();

        // the server will just echo the object we send it
        var response = client.callWithAny(payload);

        System.out.println("Protobuf representation");
        System.out.println(response);

        // If you want to print this as JSON, you need to provide a type registry including the type contained in any.
        System.out.println("JSON representation");
        JsonFormat.TypeRegistry typeRegistry = JsonFormat.TypeRegistry.newBuilder().add(ChatroomService.Chat.getDescriptor()).build();
        System.out.println(JsonFormat.printer().usingTypeRegistry(typeRegistry).print(response));

        // get type url
        var type = response.getObj().getTypeUrl().split("/")[1]; //typeUrl = type.googleapis.com/nl.toefel.chatroom.Chat

        System.out.println("any type = " + type);

        // unpack anyoneof type
        if (response.getObj().is(ChatroomService.Chat.class)) {
            var unpackedChat = response.getObj().unpack(ChatroomService.Chat.class);
            System.out.println("We received a chat: " + unpackedChat.getName() + " " + unpackedChat.getMessage());
        }
    }
}
