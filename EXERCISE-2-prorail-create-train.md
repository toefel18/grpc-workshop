# Exercise 2 - Prorail Create Train

For info on types etc: [Protobuf 3 docs](https://developers.google.com/protocol-buffers/docs/proto3)
Quickstart gRPC: [Quickstart GRPC](https://grpc.io/docs/quickstart/java.html)
Tutorial gRPC: [Tutorial GRPC](https://grpc.io/docs/tutorials/basic/java.html)

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
    
At this point, all the preliminaries are ready for [Excercise 3 - grpc-web](EXERCISE-3-grpc-web.md)

## Unit test

1. Create a unit test, use PostalOrderControllerTest.java as inspiration

Next, if you want to skip grpc-web, goto [Exercise 4 - prorail-list-trains](EXERCISE-4-prorail-list-trains.md)