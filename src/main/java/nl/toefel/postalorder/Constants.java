package nl.toefel.postalorder;

import io.grpc.Context;
import io.grpc.Metadata;

public class Constants {

    public static final Metadata.Key<String> API_KEY = Metadata.Key.of("X-API-KEY", Metadata.ASCII_STRING_MARSHALLER);
    public static final Context.Key<String> CTX_API_KEY = Context.key("X-API-KEY");
}
