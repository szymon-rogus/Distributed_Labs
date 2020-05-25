package client;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientSender extends AbstractActor {

    private final String clientId = "akka://local_system/deadLetters";
    private final String serverId = "akka://local_system@127.0.0.1:2552/user/server_sender";
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, product -> {
                    System.out.println(getSender().path());
                    if(getSender().path().toString().equals(clientId)) {
                        getContext().actorSelection(serverId).tell(product, getSelf());
                    }
                    if(getSender().path().toString().equals(serverId)) {
                        System.out.println(product);
                    }
                })
                .matchAny(o -> log.info("Product no available"))
                .build();
    }
}
