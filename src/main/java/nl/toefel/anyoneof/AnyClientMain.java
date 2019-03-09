package nl.toefel.anyoneof;

import com.google.protobuf.Any;
import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.chatroom.ChatroomService;
import nl.toefel.postalorder.AnyServiceGrpc;
import nl.toefel.postalorder.AnyServiceOuterClass;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class AnyClientMain {
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8082)
                .usePlaintext()
                .build();

        var anyOneOfService = AnyServiceGrpc.newBlockingStub(channel)
                .withDeadlineAfter(1000, TimeUnit.MILLISECONDS);

        var payload = AnyServiceOuterClass.Payload.newBuilder()
                .setObj(Any.pack(ChatroomService.Chat.newBuilder()
                        .setMessage("Hallo")
                        .setName("Chefke")
                        .build()))
                .build();

        var returned = anyOneOfService.callWithAny(payload);
        System.out.println(returned);

        // get type url
        var type = returned.getObj().getTypeUrl().split("/")[1]; //typeUrl = type.googleapis.com/nl.toefel.chatroom.Chat

        // unpack anyoneof type
        if (returned.getObj().is(ChatroomService.Chat.class)) {
            var unpackedChat = returned.getObj().unpack(ChatroomService.Chat.class);
            System.out.println("We received a chat: " + unpackedChat.getName() + " " + unpackedChat.getMessage());
        }

        var either = AnyServiceOuterClass.Either.newBuilder()
                .setName("baas b")
                .setRef(1234) // cancels out name due to oneOf~!
                .build();

        var resp = anyOneOfService.callWithEither(either);

        System.out.println("`" + resp.getName() + "`"); // will be empty
        System.out.println(JsonFormat.printer().print(resp));
    }
}
