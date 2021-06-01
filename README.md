#Demo mTLS via NGINX

## Quick start

1. Start nginx

а. copy default.conf.sample to default.conf
b. add your local IP address instead of <local IP address>, for example 192.168.1.100
c. docker-compose up   

2. Start sample-jwt
3. Start api-gateway
4. Request URL

a. http://localhost:8081/sample-jwt/untrust
a. http://localhost:8081/sample-jwt/trust

##Generate Server TLS 

docker run -it --rm -v "C:\your_path:/openssl-certs" rnix/openssl-gost

cd openssl-certs/keys
openssl req -x509 -sha256 -days 3650 -newkey rsa:4096 -keyout rootCA.key -out rootCA.crt

Password: changeit
Country Name (2 letter code): RU
Organization Name: n/a
Common Name: RootSampleCa

openssl req -new -newkey rsa:4096 -keyout localhost.key –out localhost.csr

Password: changeit
Country Name (2 letter code): RU
Organization Name: n/a
Common Name: Server

openssl x509 -req -CA rootCA.crt -CAkey rootCA.key -in localhost.csr -out localhost.crt -days 365 -CAcreateserial -extfile localhost.params

openssl x509 -in localhost.crt -text

openssl pkcs12 -export -out localhost.p12 -name "localhost" -inkey localhost.key -in localhost.crt

keytool -importkeystore -srckeystore localhost.p12 -srcstoretype PKCS12 -destkeystore keystore.jks -deststoretype JKS

##Create Truststore

### Need only CA Root

keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:localhost,ip:127.0.0.1 -file rootCA.crt -keystore truststore.jks

##Create Client TLS 

### Test API Gateway

curl -v -s -k --key client.key --cert client.crt https://<your IP-address>:8443/sample-jwt/untrust
