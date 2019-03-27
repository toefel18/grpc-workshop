package nl.toefel.metadata;

import io.grpc.Context;
import io.grpc.Metadata;

/**
 * Normally, the client and server are different systems, and they each would contain a definition like
 */
public class Constants {
    public static final Metadata.Key<String> META_ACCEPT_LANG = Metadata.Key.of("accept-language", Metadata.ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> CTX_ACCEPT_LANG = Context.key("accept-language");
}
