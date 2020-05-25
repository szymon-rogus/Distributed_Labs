package server.shop;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Shop extends AbstractActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, product -> {
                    Thread.sleep(ShopChecker.getCheckingTime());
                    int value = ShopChecker.getValueOfProduct();
                    getSender().tell(value, getSelf());
                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }
}
