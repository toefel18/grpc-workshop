# Exercise 3 - Grpc-web

For info on types etc: [Protobuf 3 docs](https://developers.google.com/protocol-buffers/docs/proto3)
Quickstart gRPC: [Quickstart GRPC](https://grpc.io/docs/quickstart/java.html)
Tutorial gRPC: [Tutorial GRPC](https://grpc.io/docs/tutorials/basic/java.html)

## Part 1
1. Use the docs or code samples for inspiration

1. Open `prorail_service.proto` and add an RPC method `CreateTrain`, it should have
   a `CreateTrainRequest` as input and `CreateTrainResponse` as output. 
    
    * The request should contain a message `PassengerTrain` which should have the fields
      - train_id (string)
      - weight_in_kg
      - capacity

1. Open ProrailController.java and implement your RPC. Store the new train in 
    ```
    private LinkedHashMap<String, PassengerTrain> trains = new LinkedHashMap<>();
    ```
    
    **AND** add it to the track using a new Train entity (not a grpc dto):
    ```
        track.addTrain(new Train(passengerTrain.getTrainId()));
    ``` 
    
    The track will update the position of the train, required for the react app stream.
    

     
1. Add some error handling, if the the train already exists, it should call onError. 
   Use the Status class to generate the exception (Status.<TYPE>.asException())
   
1. Open ProrailClientMain.java and add a static method that creates a train using the channel.
   Invoke the call twice with the same train_id, and observe the error that occurs.
   
    * Wrap the call in a try-catch block and use Status.fromThrowable to extract info.
    
# Part 2
1. Implement GetTrain, and DeleteTrain in the same way.

1. Then implement ListTrain, which should receive an empty Request and return a stream.
    
    * Add a server-side streaming RPC to the prorail_service.proto
    * Implement the server, which publishes all the trains on the responseObserver and 
      sleeps 1000ms between each publish. 
    * Implement the client and print the incoming stream.
    