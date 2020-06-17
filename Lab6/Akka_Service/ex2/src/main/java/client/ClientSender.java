package client;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import db.DBUtil;
import scala.sys.Prop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ClientSender extends AbstractActor {

    private final String clientId = "akka://local_system/deadLetters";
    private final String serverId = "akka://local_system@127.0.0.1:2552/user/server_sender";
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    static int counter = 0;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, product -> {
                    System.out.println(getSender().path());
                    if(getSender().path().toString().equals(clientId)) {
                        counter++;
                        context().actorOf(Props.create(ClientSaver.class), "saver" + counter).tell(product, getSelf());
                        getContext().actorSelection(serverId).tell(product, getSelf());
                    }
                    if(getSender().path().toString().equals(serverId)) {
                        System.out.println(product);
                    }
                    if(getSender().path().toString().equals("akka://local_system/user/client_sender/saver" + counter)) {
                        System.out.println(product + "saved");
                    }
                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }
}
