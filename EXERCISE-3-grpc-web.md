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

Next, we need to run a proxy to translate grpc-web to grpc. This is required because the browser does not 
expose all the HTTTP2 details used by the grpc protocol. 

We are going to use envoy which runs inside a docker container. Run this script to start envoy:

```bash
./start-envoy.sh
``` 

Envoy is configured by default to listen on 8083 and forward grpc-web requests to 8082 on your host.
When you see this log line, envoy is ready:

`[2019-03-28 11:40:46.663][1][info][main] [source/server/server.cc:464] starting main dispatch loop` 

## Exercise 1

1. Open `front-end/src/modules/TrainApp.js`. This file contains the react application. 

1. import the generated client, right now, you need to use the require syntax. for example:

    ```js
    const { ProrailClient } = require("../grpc/prorail_service_grpc_web_pb.js")
    ```

1. Add a const in `TrainApp.js` and instantiate a new ProrailClient, make sure that it connects to envoy to the envoy port.

1. Implement `TrainApp.getTrack`, fetch the track-layout from the client and store the result to `setTrack`. 
   When an error occurs, call `setError()`

1. Run `ProrailServerMain.java` (which runs on port 8082) and make sure envoy is running `front-end/start-envoy.sh` 
   (which runs on port 8083)

1. Start the application:

    ```bash
    npm start
    ```

1. Implement `TrainApp.addTrain` (you must have finished [Exercise 2 - prorail-create-train](EXERCISE-2-prorail-create-train.md)). 
   Create a PassengerTrain instance, and send the call to the server. Alert on success, call setError on errors. 
   
1  Test the code by adding a `trainId` in the text-box next to the "Add Train" button, and click the button. 

1. Implement `TrainApp.getPositionUpdates`. You can call the streaming method on the prorail client. 
   A stream object is returned (similar to websockets). Implement the handlers `data`, `status`, `end`, and `error`:
   on the stream, and update the trains using `setTrains(...)`
   
1. Test it by entering a `trainId` in the field next to the "GetPositionUpdates", and click the button. You should see
   the train moving.
   

## Optional:

1. Keep track of open streams, add a button to cancel an active stream. 

1. Implement api key security, this should also be configured on the grpc-backend, and possibly also in envoy. This is 
   an extension on [Exercise 5 - add-api-key-authentication](EXERCISE-5-add-api-key-authentication.md)
      
   
