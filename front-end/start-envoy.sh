docker run -it --rm --name grpw-web-envoy --network="host" \
           -v "$(pwd)/envoy.yaml:/etc/envoy/envoy.yaml:ro" \
           envoyproxy/envoy