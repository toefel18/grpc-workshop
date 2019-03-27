# gRPC workshop

 `src/main/proto` contains the .proto files 
 
 each package below `nl.toefel` contains an example. For example `nl.toefel.chatroom`
 
 Runnable classes end with Main.
 
 

## Getting started

Open the project in your favourite IDE 

 * run `./gradlew idea` or `./gradlew eclipse` to configure your IDE to detect the generated code directories.
     
```bash
./gradlew fatJar
cd build/libs
java -cp grpc-chat-1.0-SNAPSHOT-all.jar nl.toefel.chatroom.unsecure.UnsecureChatroomClientMain <your trainId>
```

#gprc web

download 
 proto, and proto-gen-grpc-web and place in path

```
cd /home/hestersco/Documents/projects/personal/grpc-workshop/front-end/src
mkdir grpc
protoc -I=../../src/main/proto train_service.proto --grpc-web_out=import_style=commonjs,mode=grpcwebtext:grpc
```