from __future__ import print_function

import sys
import grpc

sys.path.insert(1, '../generated_python')

from generated_python.events_pb2_grpc import EventsServiceStub
from generated_python.events_pb2 import *


def subscribe_some():
    type = input("Choose your subscribtion [TIME, DEVICE]: ")
    if type == "time":
        message = [
            TypeArg(eventType=Type.TIME)
        ]
    if type == "device":
        message = [
            TypeArg(eventType=Type.DEVICE)
        ]
    for msg in message:
        print("Sending %s" % (msg))
        yield msg


def show_stream_messages(stub):
    try:
        responses = stub.Subscribe(subscribe_some())
        for response in responses:
            print(response)
    except grpc.RpcError as rpc_error:
        print("Server crashed or has been stopped...")
        code = rpc_error.code()
        print(code)



def start():
    with grpc.insecure_channel('localhost:8080') as channel:
        stub = EventsServiceStub(channel)
        print("-------------- events_listen --------------")
        show_stream_messages(stub)

start()
