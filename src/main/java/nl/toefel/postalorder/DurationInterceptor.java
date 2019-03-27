package nl.toefel.postalorder;

import io.grpc.*;

public class DurationInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("Duration interceptor " + call + headers);
        return next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
            @Override
            public void sendHeaders(Metadata headers) {
                System.out.println("Duration interceptor - sending headers");
                super.sendHeaders(headers);
            }

        }, headers);
    }
}
