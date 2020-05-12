# gRPC

Client - Server using grpc\
Server in Java\
First client in Java, second in python

Generate java source files from proto for python and java client (run this in zad1 dir)

> protoc -I=. --java_out=generated_java proto/events.proto\
> protoc -I=. --python_out=../zad1-py/generated_python proto/events.proto

Then you can use both clients in the same time.\
After the server is down, clients will be disconnected from streaming
