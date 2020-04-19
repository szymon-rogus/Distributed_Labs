package Administratory;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Admin {

    public static int id_ag1 = 1;
    public static int id_ag2 = 2;

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel sniffer = connection.createChannel();
        sniffer.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String SNIFFER = sniffer.queueDeclare().getQueue();

        sniffer.queueBind(SNIFFER, "Main", "#");

        Consumer snifferConsumer = new DefaultConsumer(sniffer) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);

                System.out.println("Sniffed: " + message);

                sniffer.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        sniffer.basicConsume(SNIFFER, false, snifferConsumer);


        //com channel
        Channel communication = connection.createChannel();
        communication.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String routing = null;

            System.out.println("Enter type [agencies | carriers | all]:");
            String type = br.readLine();

            if(type.equals("agencies")) {
                routing = "agency";
            }
            else
            if(type.equals("carriers")) {
                routing = "carrier";
            }
            else
            if(type.equals("all")) {
                routing = "agency.carrier";
            }
            else
                System.out.println("Wrong destination!");

            System.out.println("Enter message:");
            String msg = br.readLine();
            msg = "#Admin: " + msg;

            communication.basicPublish("Main", routing, null, msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}
