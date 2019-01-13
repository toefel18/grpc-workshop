# Enabling TLS

A certificate and private key are required to run a gRPC server and client.

You can generate these using `openssl` or `mkcert`. We will use mkcert

## Using mkcert

 1. Download pre-built binary [github.com/FiloSottile/mkcert/releases](https://github.com/FiloSottile/mkcert/releases) (win/mac/linux)
 1. Create a local CA (Certificate Authority) and install it on your system.
 
        # probably not required, but useful for HTTPS connections to localhost
        ./mkcert -install
              
 1. Create a certificate valid for all localhost identifiers
 
        ./mkcert localhost 127.0.0.1 ::1
        
        output:
        localhost+1.pem
        localhost+1-key.pem  
        
 1. I copied these files inside `/tmp/certs`, you need to enter the path in the code.


Use `mkcert -uninstall` to remove the CA from your system.