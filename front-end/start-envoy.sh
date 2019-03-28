#!/usr/bin/env bash

echo "if envsubst is not found on mac, run: 'brew install gettext'"
echo "docker is required"

DERIVED_IP_ADDRESS=$(hostname -I | awk '{print $1}')

IP_ADDRESS=${IP_ADDRESS:-$DERIVED_IP_ADDRESS}
GRPC_PORT=${GRPC_PORT:-8082}
ENVOY_LISTEN_PORT=${ENVOY_LISTEN_PORT:-8083}

echo "Configuring envoy to target grpc backend at ${IP_ADDRESS}:${GRPC_PORT} while listening at ${ENVOY_LISTEN_PORT}"
echo "use env vars IP_ADDRESS, GRPC_PORT and ENVOY_LISTEN_PORT to override"

echo "starting container..."
sleep 2

IP_ADDRESS=${IP_ADDRESS} GRPC_PORT=${GRPC_PORT:-8082} ENVOY_LISTEN_PORT=${ENVOY_LISTEN_PORT} envsubst < envoy-config-template.yaml > envoy.yaml

docker run -it --rm --name grpw-web-envoy-${ENVOY_LISTEN_PORT} \
           -v "$(pwd)/envoy.yaml:/etc/envoy/envoy.yaml:ro" \
           -p ${ENVOY_LISTEN_PORT}:${ENVOY_LISTEN_PORT} \
           envoyproxy/envoy