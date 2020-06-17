package server;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import db.DBUtil;
import scala.concurrent.duration.Duration;
import server.shop.ShopAgent;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
public class ServerSender extends AbstractActor {

    final String shopAgent = "shopAgent";

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
        DBUtil.createConnection().close();
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
            context().actorOf(Props.create(ShopAgent.class), shopAgent).tell(product, getSelf());
        }
        else {
            getContext().actorSelection(clientId).tell(product, getSelf());
            getContext().stop(getSender());
        }
    }
}
