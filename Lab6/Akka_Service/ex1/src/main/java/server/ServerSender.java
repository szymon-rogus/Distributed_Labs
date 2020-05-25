package server;

import akka.actor.*;
import akka.dispatch.OnComplete;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import server.shop.Shop;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.pattern.Patterns.ask;

public class ServerSender extends AbstractActor {

    Integer price1 = null;
    Integer price2 = null;

    private final static String clientId = "akka://local_system@127.0.0.1:2551/user/client_sender";

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
                .match(IllegalArgumentException.class, e -> resume())
                .matchAny(o -> restart())
                .build());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::apply)
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private void apply(String product) throws InterruptedException {
        if (getSender().path().toString().equals(clientId)) {

            Future<Object> p1 = ask(context().actorOf(Props.create(Shop.class)), product, Timeout.create(java.time.Duration.ofMillis(300)));
            p1.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) {
                    if (failure == null) {
                        price1 = (int) success;
                    } else {
                        price1 = null;
                    }
                }
            }, getContext().getDispatcher());

            Future<Object> p2 = ask(context().actorOf(Props.create(Shop.class)), product, Timeout.create(java.time.Duration.ofMillis(300)));
            p2.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) {
                    if (failure == null) {
                        price1 = (int) success;
                    } else {
                        price2 = null;
                    }
                }
            }, getContext().getDispatcher());

            Thread.sleep(300);

            sendFoundPrice(product);
        }
    }

    private void sendFoundPrice(String product) {

        int minPrice = Stream.of(price1, price2)
                .filter(Objects::nonNull)
                .mapToInt(price -> price)
                .max().orElse(-1);

        if (minPrice == -1) {
            getSender().tell("No price availabe!", getSelf());
        } else {
            getSender().tell(product + " price: " + minPrice, getSelf());
        }
    }
}
