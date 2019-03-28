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
     
## gRPC-web requirements

When using java, gradle downloads the protobuf compiler for us, when using grpc-web
we need to invoke the compiler (protoc) ourselves to generate code.
1. Download the protobuf compiler (protoc) from the [protobuf releases page](https://github.com/protocolbuffers/protobuf/releases/tag/v3.7.0). 
Choose the version for your platform, most mac users would need `protoc-3.7.0-osx-x86_64.zip`
2. Extract the archive and place the protoc executable somewhere in your path (and make sure you have execution rights `chmod +x protoc`)
3. Download protoc-gen-grpc-web from [grpc-web releases page](https://github.com/grpc/grpc-web/releases/tag/1.0.4), choose your platform
 and also place it in your path. Make sure to rename it 'protoc-gen-grpc-web'

You should now be able to generate javascript sources from the *.proto files in src/main/proto

```bash
cd front-end
./generate-proto.sh
npm install #to fetch all deps
```

Run `./generate-proto.sh` every time you changed the .proto files


#Excercises

 * [Exercise 1 - protobuf](EXERCISE-1-protobuf.md)