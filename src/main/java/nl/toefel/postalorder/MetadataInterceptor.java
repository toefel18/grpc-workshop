package nl.toefel.postalorder;

import io.grpc.*;

public class MetadataInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("Metadata Interceptor Headers " + headers.toString());
        System.out.println(Constants.API_KEY.originalName() + headers.get(Constants.API_KEY));
        Context contextWithApiKey = Context.current().withValue(Constants.CTX_API_KEY, headers.get(Constants.API_KEY));
        return Contexts.interceptCall(contextWithApiKey, call, headers, next);
    }
}

