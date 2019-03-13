# Make sure protoc and protoc-gen-grpc-web are in your classpath

mkdir -p src/grpc

OUT_DIR=src/grpc
INCLUDE_PATH=../src/main/proto

protoc -I=$INCLUDE_PATH train_service.proto \
 --grpc-web_out=import_style=commonjs+dts,mode=grpcwebtext:$OUT_DIR \
 --js_out=import_style=commonjs+dts:$OUT_DIR

for generated_file in $OUT_DIR/*
do
    echo "adding ./*eslint-disable*/ to $generated_file"
    sed -i '1s|^|/* eslint-disable */\n|' $generated_file
done


 # next we need to disable eslint for each file

