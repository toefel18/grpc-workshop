package nl.toefel.chatroom.tls;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import nl.toefel.chatroom.ChatroomCommandLineClient;

import java.io.File;
import java.io.IOException;

/**
 * See README.md for certificate generation instructions
 */
@SuppressWarnings("Duplicates")
public class TlsChatroomClientMain {
    public static void main(String[] args) throws IOException {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress("localhost", 8443)
                .useTransportSecurity()
                // sslContext is required because we generated our own certificates
                .sslContext(GrpcSslContexts.forClient().trustManager(new File("/tmp/certs/localhost+1.pem")).build())
                .build();

        var nickname = "unknown";
        if (args.length > 0) {
            nickname = args[0];
        }

        new ChatroomCommandLineClient().start(channel, nickname);
    }
}
