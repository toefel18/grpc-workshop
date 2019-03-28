# Exercise 5 - Api key authentication

For info on types etc: [Protobuf 3 docs](https://developers.google.com/protocol-buffers/docs/proto3)
Quickstart gRPC: [Quickstart GRPC](https://grpc.io/docs/quickstart/java.html)
Tutorial gRPC: [Tutorial GRPC](https://grpc.io/docs/tutorials/basic/java.html)

1. Create a class that implements a server interceptor that extracts a "api-key" header from the metadata. 
   you can use `AcceptLanguageServerInterceptor.java` for inspiration. If the api is not set or does not match
   `koekkoek`, the call should fail with satus UNAUTHENTICATED.
   
1. Add the api key to the stub, you can use: use MetadataUtils or CallCredentials. Pick your poison.

1. Test it.  