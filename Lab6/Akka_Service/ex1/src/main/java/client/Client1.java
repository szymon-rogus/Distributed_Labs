package client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import server.shop.Products;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Logger;

public class Client1 {

    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger(Client1.class.getName());

        File configFile = new File("local_app.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("local_system", config);
        final ActorRef sender = system.actorOf(Props.create(ClientSender.class), "client_sender");

        logger.info("Available products");
        logger.info(Arrays.toString(Products.class.getEnumConstants()));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println();
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }

            sender.tell(line, null);
        }

        system.terminate();
    }
}
