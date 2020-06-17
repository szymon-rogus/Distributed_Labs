package client;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import db.DBUtil;
import server.shop.Products;
import server.shop.ShopChecker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientSaver  extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, product -> {
                    if(ShopChecker.getProductList().contains(Products.valueOf(product.toUpperCase()))) {

                        saveInDb(product);
                    }
                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }

    private synchronized void saveInDb(String product) throws SQLException {
        String checkH2 = "select REQUEST_COUNTER from REQUEST where REQUEST_NAME = ?";

        Connection conn = DBUtil.getConnection();

        PreparedStatement state = conn.prepareStatement(checkH2);
        state.setString(1, product);
        ResultSet rs = state.executeQuery();

        if(!rs.isClosed()) {
            while(rs.next()) {
                int i = rs.getInt("REQUEST_COUNTER");
                String updateH2 = "update REQUEST set REQUEST_COUNTER = ? where REQUEST_NAME = ?";
                PreparedStatement updateState = conn.prepareStatement(updateH2);
                updateState.setInt(1, i+1);
                updateState.setString(1, product);
                System.out.println("no closed");
                updateState.executeUpdate();
                getSender().tell("", getSelf());
            }
        } else {
            String postH2 = "insert into REQUEST (REQUEST_NAME, REQUEST_COUNTER) values (?, 1)";
            PreparedStatement createState = conn.prepareStatement(postH2);
            createState.setString(1, product);
            System.out.println("closed");
            createState.executeUpdate();
            getSender().tell("", getSelf());
        }
    }
}
