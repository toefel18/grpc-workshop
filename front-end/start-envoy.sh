sudo docker run -it --rm --name envoy --network="host"\
             -v "$(pwd)/envoy.yaml:/etc/envoy/envoy.yaml:ro" \
             envoyproxy/envoy