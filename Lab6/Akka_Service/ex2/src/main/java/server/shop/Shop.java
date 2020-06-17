package server.shop;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import db.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Shop extends AbstractActor{

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, product -> {
                    if(ShopChecker.productList.contains(Products.valueOf(product.toUpperCase()))) {

                        Thread.sleep(ShopChecker.getCheckingTime());
                        int value = ShopChecker.getValueOfProduct();
                        System.out.println(value);
                        getSender().tell(value, getSelf());
                    }

                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }
}
