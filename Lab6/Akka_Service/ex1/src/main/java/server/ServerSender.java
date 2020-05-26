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
import java.util.stream.Stream;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import static akka.pattern.Patterns.ask;

public class ServerSender extends AbstractActor {

    Integer price1 = null;
    Integer price2 = null;

    final String shop1 = "shop1";
    final String shop2 = "shop2";

    private final static String clientId = "akka://local_system@127.0.0.1:2551/user/client_sender";

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new AllForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
                .match(IllegalArgumentException.class, e -> resume())
                .matchAny(o -> restart())
                .build());
    }

    @Override
    public void postStop() throws Exception {
        context().stop(self());
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

            Future<Object> p1 = ask(context().actorOf(Props.create(Shop.class), shop1), product, Timeout.create(java.time.Duration.ofMillis(300)));
            p1.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) {
                    if (failure == null) {
                        price1 = (int) success;
                    }
                }
            }, getContext().getDispatcher());

            Future<Object> p2 = ask(context().actorOf(Props.create(Shop.class), shop2), product, Timeout.create(java.time.Duration.ofMillis(300)));
            p2.onComplete(new OnComplete<Object>() {
                @Override
                public void onComplete(Throwable failure, Object success) {
                    if (failure == null) {
                        price2 = (int) success;
                    }
                }
            }, getContext().getDispatcher());

            sendFoundPrice(product);
        }

        closeShopActors();
        closePrices();
    }

    private void sendFoundPrice(String product) {

        int minPrice = Stream.of(price1, price2)
                .filter(Objects::nonNull)
                .mapToInt(price -> price)
                .min().orElse(-1);

        if (minPrice == -1) {
            getSender().tell("No price availabe!", getSelf());
        } else {
            getSender().tell(product + " price: " + minPrice, getSelf());
        }
    }

    private void closeShopActors() {
        context().stop(context().child(shop1).get());
        context().stop(context().child(shop2).get());
    }

    private void closePrices() {
        price1 = null;
        price2 = null;
    }
}
