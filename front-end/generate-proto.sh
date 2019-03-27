#!/usr/bin/env bash
# Make sure protoc and protoc-gen-grpc-web are in your classpath

mkdir -p src/grpc

OUT_DIR=src/grpc
INCLUDE_PATH=../src/main/proto
PROTOS_TO_PROCESS="prorail_service.proto postalorder_service.proto"


echo "clearing $OUT_DIR/*"
rm -rf $OUT_DIR/*

# generate normal JS files
protoc -I=$INCLUDE_PATH $(ls $INCLUDE_PATH) \
 --grpc-web_out=import_style=commonjs+dts,mode=grpcwebtext:$OUT_DIR \
 --js_out=import_style=commonjs:$OUT_DIR

# generate typings js and typings (somehow this command does not generate train_service_pdb.js)
# protoc -I=$INCLUDE_PATH train_service.proto \
#  --grpc-web_out=import_style=commonjs+dts,mode=grpcwebtext:$OUT_DIR \
#  --js_out=import_style=commonjs+dts:$OUT_DIR

# next we need to disable eslint for each file with generated code
# TODO maybe add dir to .eslintignore
for generated_file in $OUT_DIR/*
do
    echo "adding ./*eslint-disable*/ to $generated_file"
    sed -i '1s|^|/* eslint-disable */\n|' $generated_file
done

echo "Do not edit files in this directory, directory will be cleared by generate-proto" > $OUT_DIR/GENERATED_DIRECTORY
