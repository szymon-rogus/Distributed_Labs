package events.client;

import events.Data;
import events.EventsServiceGrpc;
import events.Type;
import events.TypeArg;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ClientJava {
    Type eventType = null;

    private final ManagedChannel channel;
    private final EventsServiceGrpc.EventsServiceStub chatStub;

    public ClientJava(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        chatStub = EventsServiceGrpc.newStub(channel);
    }

    public static void main(String[] args) throws Exception {
        ClientJava client = new ClientJava("localhost", 8080);
        client.start();
    }

    public void start() throws InterruptedException {
        StreamObserver<Data> responseObserver = getResponseObserver();
        try {
            String line = null;
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            StreamObserver<TypeArg> observer = null;
            do {
                try {
                    System.out.println("You can subscribe [sub]:");
                    System.out.flush();
                    line = in.readLine();
                    if (line == null) {
                        break;
                    }
                    if (line.equals("sub")) {
                        System.out.println(Arrays.toString(Type.class.getEnumConstants()));
                        line = in.readLine();

                        if(line.toLowerCase().equals("time")) {
                            eventType = Type.TIME;
                        }
                        if(line.toLowerCase().equals("device")) {
                            eventType = Type.DEVICE;
                        }


                        TypeArg request = TypeArg.newBuilder().setEventType(eventType).build();
                        if (observer == null)
                            observer = chatStub.subscribe(responseObserver);
                        observer.onNext(request);
                    }

                } catch (java.io.IOException ex) {
                    System.err.println(ex);
                }
            }
            while (!line.toLowerCase().equals("exit"));
        } finally {
            channel.shutdown().awaitTermination(3, TimeUnit.SECONDS);
        }
    }

    private StreamObserver<Data> getResponseObserver() {
        return new StreamObserver<Data>() {
            @Override
            public void onNext(Data res) {
                if(eventType == Type.TIME) {
                    System.out.println(res.getType().name()
                    + ": ActualTime: " + res.getHour() + ":" + res.getMinute() + ":" + res.getSecond());
                }
                if(eventType == Type.DEVICE) {
                    System.out.println(res.getType().name() + ":" + res.getName());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Server crashed or has been stopped...");
                System.err.println(t.getStackTrace());
                System.exit(0);
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }
        };
    }
}
