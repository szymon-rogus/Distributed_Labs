package events.serverSide;

import events.Data;
import events.Type;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ServerMessages {

    private static final int PORT = 8080;
    private final Server server;
    private final BlockingQueue<Data> queueTime = new LinkedBlockingQueue<>();
    private final BlockingQueue<Data> queueDevice = new LinkedBlockingQueue<>();
    private final DataServiceImpl dataServiceImpl = new DataServiceImpl(queueTime, queueDevice);

    ServerMessages() {
        server = ServerBuilder.forPort(PORT)
                .addService(dataServiceImpl)
                .build();
        System.out.println("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Server has been shut down");
            this.stop();
        }));
    }

    void start() {
        try {
            new Thread(dataServiceImpl).start();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }


    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    void sendTime(Date date) {
        Type eventType = Type.TIME;
        Data eventInfo = Data.newBuilder().setType(eventType)
                .setHour(date.getHour()).setMinute(date.getMinute())
                .setSecond(date.getSecond()).build();
        queueTime.add(eventInfo);
    }

    void sendDevice(String nameOfDevice) {
        Type eventType = Type.DEVICE;
        Data eventInfo = Data.newBuilder().setType(eventType)
                .setName(nameOfDevice).build();
        queueDevice.add(eventInfo);
    }
}
