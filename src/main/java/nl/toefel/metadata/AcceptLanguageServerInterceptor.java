package nl.toefel.metadata;

import io.grpc.*;

public class AcceptLanguageServerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String acceptLangugeValue = headers.get(Constants.META_ACCEPT_LANG);
        System.out.println("interceptor received accept-language: " + acceptLangugeValue);

        // add field to the Context so that the Controller can retrieve it
        Context newContext = Context.current().withValue(Constants.CTX_ACCEPT_LANG, acceptLangugeValue);
        return Contexts.interceptCall(newContext,call,headers, next);
    }
}
