package nl.toefel.anyoneof;

import com.google.protobuf.util.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import nl.toefel.oneof.OneOfServiceGrpc;
import nl.toefel.oneof.OneofService;

import java.io.IOException;

public class OneOfClientMain {
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9900)
                .usePlaintext()
                .build();

        var oneOfClient = OneOfServiceGrpc.newBlockingStub(channel);

        var either = OneofService.Either.newBuilder()
                .setName("baas b")
                .setRef(1234) // cancels out name due to oneOf~!
                .build();

        // the server will just echo the object we send it
        var response = oneOfClient.callWithEither(either);

        if (response.getIdentifierCase() == OneofService.Either.IdentifierCase.NAME) {
            System.out.println("name is set");
        } else if (response.getIdentifierCase() == OneofService.Either.IdentifierCase.REF) {
            System.out.println("ref is set");
        } else {
            System.out.println("Neither name or ref is set");
        }

        System.out.println("`" + response.getName() + "`"); // will be empty
        System.out.println(JsonFormat.printer().print(response));
    }
}
