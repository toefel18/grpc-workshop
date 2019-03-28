# Exercise 3 - Grpc-web

For info on types etc: [Protobuf 3 docs](https://developers.google.com/protocol-buffers/docs/proto3)
Grpc-web github: [grpc-web](https://github.com/grpc/grpc-web)
grpc-web docs [docs](https://grpc.io/docs/tutorials/basic/web.html)

## gRPC-web prerequisites

When using java, gradle downloads the protobuf compiler for us. When using grpc-web
we need to invoke the compiler (protoc) ourselves to generate code.

1. Download the protobuf compiler (protoc) from the [protobuf releases page](https://github.com/protocolbuffers/protobuf/releases/tag/v3.7.0). 
   Choose the version for your platform, most mac users would need `protoc-3.7.0-osx-x86_64.zip`
1. Extract the archive and place the protoc executable somewhere in your path (and make sure you have execution rights `chmod +x protoc`)
1. Download protoc-gen-grpc-web from [grpc-web releases page](https://github.com/grpc/grpc-web/releases/tag/1.0.4), choose your platform
   and also place it in your path. Make sure to rename it 'protoc-gen-grpc-web'

You should now be able to generate javascript sources from the *.proto files in src/main/proto

```bash
cd front-end
./generate-proto.sh
npm install #to fetch all deps
```

Run `./generate-proto.sh` every time you changed the .proto files


## Exercise 1

1. Open `front-end/src/modules/TrainApp.js`. This file contains the react application. 

1. Add a const and instantiate a new ProrailClient, make sure that it connects to the envoy port.

1. Implement getTrack, fetch the track-layout from the client and set the result to `setTrack`

1. Implement addTrain. Create a PassengerTrain instance ()
