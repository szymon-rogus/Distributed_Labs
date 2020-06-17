package server.shop;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Timeout;

import db.DBUtil;
import scala.concurrent.Future;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                    int counter_requests = 0;
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

                    String request = "select REQUEST_COUNTER from REQUEST where REQUEST_NAME = ?";
                    Connection conn = DBUtil.createConnection();
                    PreparedStatement state = conn.prepareStatement(request);
                    state.setString(1, product);
                    ResultSet rs = state.executeQuery();

                    if(!rs.isClosed()) {
                        counter_requests = rs.getInt("REQUEST_COUNTER");
                    }

                    Thread.sleep(300);

                    sendFoundPrice(product, counter_requests);
                    closeShopActors();
                    closePrices();
                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }

    private void sendFoundPrice(String product, int counter) {

        OptionalInt minPrice = Stream.of(price1, price2)
                .filter(Objects::nonNull)
                .mapToInt(price -> price)
                .min();

        if (!minPrice.isPresent()) {
            getSender().tell("No price availabe! " + ", request counter: " + counter, getSelf());
        } else {
            getSender().tell(product + " price: " + minPrice.getAsInt()  + ", request counter: " + counter, getSelf());
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
