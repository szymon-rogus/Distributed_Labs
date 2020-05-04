protoc -I=. --python_out=generated_python proto/events.proto

// After generating files move it to zad1-py/generated_python
// Other option is to change dir python_out=../zad1-py/generated_python

protoc -I=. --python_out=../zad1-py/generated_python proto/events.proto