# gRPC workshop

Some quick guidelines
* `src/main/proto` contains the .proto files   
* Run `./gradlew generateProto` to generate client, server and message classes, run this command after you  
* Each package below `nl.toefel` contains an example. For example `nl.toefel.chatroom`
* Runnable classes end with Main.
* The server stubs are implemented with classnames ending witn 'Controller'
* Run `./gradlew idea` or `./gradlew eclipse` to configure your IDE to detect the generated code directories.
     
The react frontend-application is located in the root directory `front-end`
     
## Running main classes on the command line

```bash
# 1 create a fat jar
./gradlew fatJar 

# 2 run the server variant in one terminal 
java -cp build/libs/grpc-chat-1.0-SNAPSHOT-all.jar nl.toefel.chatroom.unsecure.UnsecureChatroomClientMain

# 3 run the client variant in another terminal
java -cp build/libs/grpc-chat-1.0-SNAPSHOT-all.jar nl.toefel.chatroom.unsecure.UnsecureChatroomClientMain

```
     
#Excercises

 * [EXERCISE-1-protobuf.md](EXERCISE-1-protobuf.md)
 * [EXERCISE-2-prorail-create-train.md](EXERCISE-2-prorail-create-train.md)
 * [EXERCISE-3-grpc-web.md](EXERCISE-3-grpc-web.md)
 * [EXERCISE-4-prorail-list-trains.md](EXERCISE-4-prorail-list-trains.md)
 * [EXERCISE-5-add-api-key-authentication.md](EXERCISE-5-add-api-key-authentication.md)
 * [EXERCISE-6-transcoding-to-http.md](EXERCISE-6-transcoding-to-http.md)

 