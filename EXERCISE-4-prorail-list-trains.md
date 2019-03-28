# Exercise 4 - Prorail List Trains

For info on types etc: [Protobuf 3 docs](https://developers.google.com/protocol-buffers/docs/proto3)
Quickstart gRPC: [Quickstart GRPC](https://grpc.io/docs/quickstart/java.html)
Tutorial gRPC: [Tutorial GRPC](https://grpc.io/docs/tutorials/basic/java.html)

1. You can implement GetTrain, and DeleteTrain in a similar fashion. This simulates a Create Read Delete system, 
   as you would do in a REST resource. If you feel this is more of the same, skip to the next step.

1. Add an rpc called ListTrains, which should receive an empty Request and return a stream of PassengerTrain.

1. Add a server-side streaming RPC to the prorail_service.proto
    * Implement the server, which publishes all the trains on the responseObserver and 
      sleeps 1000ms between each publish to simulate slow streaming. 
      
1. Implement the client and print the incoming streamed trains, one by one.
    