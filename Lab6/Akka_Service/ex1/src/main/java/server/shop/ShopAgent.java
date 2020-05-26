package server.shop;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Timeout;

import scala.concurrent.Future;

import java.time.Duration;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static akka.pattern.Patterns.ask;

public class ShopAgent extends AbstractActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    final String shop1 = "shop1";
    final String shop2 = "shop2";

    Integer price1 = null;
    Integer price2 = null;

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, product -> {
                    System.out.println(getSelf().path());
                    Future<Object> p1 = ask(context().actorOf(Props.create(Shop.class), shop1), product, Timeout.create(Duration.ofMillis(300)));
                    p1.onComplete(new OnComplete<Object>() {
                        @Override
                        public void onComplete(Throwable failure, Object success) {
                            if (failure == null) {
                                price1 = (int) success;
                            }
                        }
                    }, getContext().getDispatcher());

                    Future<Object> p2 = ask(context().actorOf(Props.create(Shop.class), shop2), product, Timeout.create(Duration.ofMillis(300)));
                    p2.onComplete(new OnComplete<Object>() {
                        @Override
                        public void onComplete(Throwable failure, Object success) {
                            if (failure == null) {
                                price2 = (int) success;
                            }
                        }
                    }, getContext().getDispatcher());

                    Thread.sleep(300);

                    sendFoundPrice(product);
                    closeShopActors();
                    closePrices();
                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }

    private void sendFoundPrice(String product) {

        OptionalInt minPrice = Stream.of(price1, price2)
                .filter(Objects::nonNull)
                .mapToInt(price -> price)
                .min();

        if (!minPrice.isPresent()) {
            getSender().tell("No price availabe!", getSelf());
        } else {
            getSender().tell(product + " price: " + minPrice.getAsInt(), getSelf());
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
