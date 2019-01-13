# gRPC workshop

 `src/main/proto` contains the .proto files 
 
 each package below `nl.toefel` contains an example. For example `nl.toefel.chatroom`
 
 Runnable classes end with Main.

## Getting started

Open the project in your favourite IDE 

 * run `./gradlew idea` or `./gradlew eclipse` to configure your IDE to detect the generated code directories.
     
```bash
./gradlew fatjar
cd build/libs
java -cp grpc-chat-1.0-SNAPSHOT-all.jar nl.toefel.chatroom.unsecure.UnsecureChatroomClientMain <your name>
```
