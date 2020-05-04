package events.serverSide;

import events.Data;
import events.EventsServiceGrpc;
import events.Type;
import events.TypeArg;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class DataServiceImpl extends EventsServiceGrpc.EventsServiceImplBase implements Runnable {

    private final BlockingQueue<Data> queueTime;
    private final BlockingQueue<Data> queueDevice;

    private final Map<Type, Set<StreamObserver<Data>>> observers = new ConcurrentHashMap<>();

    public DataServiceImpl(BlockingQueue<Data> queueTime, BlockingQueue<Data> queueDevice) {
        this.queueTime = queueTime;
        this.queueDevice = queueDevice;
    }

    @Override
    public StreamObserver<events.TypeArg> subscribe(StreamObserver<Data> responseObserver) {
        return new StreamObserver<TypeArg>() {
            @Override
            public void onNext(TypeArg eventTypeArgument) {
                Type eventType = eventTypeArgument.getEventType();
                System.out.println("New subscriber for type: " + eventType);

                observers.computeIfAbsent(eventType, k -> new HashSet<>()).add(responseObserver);
                Context.current().addListener(context -> System.out.println("listening..."),
                        command -> {
                            System.out.println("Removing redundant ->");
                            Optional.ofNullable(observers.get(eventType))
                                    .ifPresent(streamObservers -> streamObservers.remove(responseObserver));
                        });
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("Something went terribly wrong");;
            }

            @Override
            public void onCompleted() {
            }
        };
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {

                Data timeInfo = queueTime.take();
                System.out.println("Sending... \n" + timeInfo);

                Optional.ofNullable(observers.get(timeInfo.getType()))
                        .ifPresent(streamObservers -> streamObservers.forEach(o -> o.onNext(timeInfo)));

                Thread.sleep(2000);

                Data deviceInfo = queueDevice.take();
                System.out.println("Sending... \n" + deviceInfo.getName());

                Optional.ofNullable(observers.get(deviceInfo.getType()))
                        .ifPresent(streamObservers -> streamObservers.forEach(o -> o.onNext(deviceInfo)));

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
