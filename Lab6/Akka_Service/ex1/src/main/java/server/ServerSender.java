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

import java.util.concurrent.atomic.AtomicInteger;

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

    private void apply(String s) throws InterruptedException {
        System.out.println(getSender().path());
        if (getSender().path().toString().equals(clientId)) {

            String productName = s.trim();
            AtomicInteger counter = new AtomicInteger();

            Future<Object> p1 = ask(context().actorOf(Props.create(Shop.class)), s, Timeout.create(java.time.Duration.ofMillis(300)));
            p1.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) {
                    if (failure == null) {
                        price1 = (int) success;
                        counter.getAndIncrement();
                    } else {
                        price1 = null;
                    }
                }
            }, getContext().getDispatcher());

            Future<Object> p2 = ask(context().actorOf(Props.create(Shop.class)), s, Timeout.create(java.time.Duration.ofMillis(300)));
            p2.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) {
                    if (failure == null) {
                        price1 = (int) success;
                        counter.getAndIncrement();
                    } else {
                        price2 = null;
                    }
                }
            }, getContext().getDispatcher());

            Thread.sleep(300);

            if (price1 == null && price2 == null) {
                getSender().tell("No price availabe!", getSelf());
            } else if (price1 == null) {
                getSender().tell(productName + "price: " + price2, getSelf());
            } else if (price2 == null) {
                getSender().tell(productName + "price: " + price1, getSelf());
            } else {
                getSender().tell(productName + "price: " + Integer.min(price1, price2), getSelf());
            }
        }
    }
}
