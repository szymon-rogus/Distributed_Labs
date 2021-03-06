package Carrier;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Carrier1 {

    public static void main(String[] args) throws Exception {

        System.out.println("Carrier1");
        System.out.println("My duties: \nPeople transport,\nEquipment transport");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // tasks channel
        Channel channel_people = connection.createChannel();
        channel_people.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);
        Channel channel_equip = connection.createChannel();
        channel_equip.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String PEOPLE_QUEUE = "people";
        String EQUIP_QUEUE = "equip";

        channel_people.queueDeclare(PEOPLE_QUEUE, false, false, false, null);
        channel_people.queueBind(PEOPLE_QUEUE, "Main", "people");
        channel_equip.queueDeclare(EQUIP_QUEUE, false, false, false, null);
        channel_equip.queueBind(EQUIP_QUEUE, "Main", "equip");

        // return ack msg channels
        Channel order_return = connection.createChannel();
        order_return.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        // channel for admin
        Channel communication = connection.createChannel();
        communication.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);
        String COMMUNICATION_QUEUE = communication.queueDeclare().getQueue();
        communication.queueBind(COMMUNICATION_QUEUE, "Main", "#.carrier");

        Consumer admin_consumer = new DefaultConsumer(communication) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String (body, StandardCharsets.UTF_8);

                System.out.println(message);

                communication.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        communication.basicConsume(COMMUNICATION_QUEUE, false, admin_consumer);

        Consumer consumer_people = new DefaultConsumer(channel_people) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String routing_key = message.substring(6, 7);
                String msg_return = message.substring(9) + " : DONE";

                if(routing_key.equals("1")) {
                    order_return.basicPublish("Main", "first", null, msg_return.getBytes(StandardCharsets.UTF_8));

                } else {
                    order_return.basicPublish("Main", "second", null, msg_return.getBytes(StandardCharsets.UTF_8));

                }

                channel_people.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        Consumer consumer_equip = new DefaultConsumer(channel_equip) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String routing_key = message.substring(6, 7);
                String msg_return = message.substring(9) + " : DONE";

                if(routing_key.equals("1")) {
                   order_return.basicPublish("Main", "first", null, msg_return.getBytes(StandardCharsets.UTF_8));

                } else {
                    order_return.basicPublish("Main", "second", null, msg_return.getBytes(StandardCharsets.UTF_8));

                }

                channel_equip.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        System.out.println("Waiting for job to do...");

        channel_people.basicConsume(PEOPLE_QUEUE, false, consumer_people);
        channel_equip.basicConsume(EQUIP_QUEUE, false, consumer_equip);
    }
}
