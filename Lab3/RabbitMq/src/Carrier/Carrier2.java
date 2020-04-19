package Carrier;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Carrier2 {

    public static void main(String[] args) throws Exception {

        System.out.println("Carrier2");
        System.out.println("My duties: \nEquipment transport,\nSending satellite to space");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // tasks channel
        Channel channel_space = connection.createChannel();
        channel_space.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);
        Channel channel_equip = connection.createChannel();
        channel_equip.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String SPACE_QUEUE = "space_queue";
        String EQUIP_QUEUE = "equip_queue";

        channel_space.queueDeclare(SPACE_QUEUE, false, false, false, null);
        channel_space.queueBind(SPACE_QUEUE, "Main", "space_queue");
        channel_equip.queueDeclare(EQUIP_QUEUE, false, false, false, null);
        channel_equip.queueBind(EQUIP_QUEUE, "Main", "equip_queue");

        // return ack msg channels
        Channel order_return_1 = connection.createChannel();
        order_return_1.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String ORDER_RETURN_1 = "first";
        order_return_1.queueDeclare(ORDER_RETURN_1, false, false, false, null);
        order_return_1.queueBind(ORDER_RETURN_1, "Main", "first");

        Channel order_return_2 = connection.createChannel();
        order_return_2.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String ORDER_RETURN_2 = "second";
        order_return_2.queueDeclare(ORDER_RETURN_2, false, false, false, null);
        order_return_2.queueBind(ORDER_RETURN_2, "Main", "second");

        Consumer consumer_people = new DefaultConsumer(channel_space) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);

                String routing_key = message.substring(6, 7);
                String msg_return = message.substring(9) + " : DONE";

                if(routing_key.equals("1")) {
                    order_return_1.basicPublish("Main", ORDER_RETURN_1, null, msg_return.getBytes(StandardCharsets.UTF_8));

                } else {
                    order_return_2.basicPublish("Main", ORDER_RETURN_2, null, msg_return.getBytes(StandardCharsets.UTF_8));
                }

                channel_space.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        Consumer consumer_equip = new DefaultConsumer(channel_equip) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received: " + message);

                String routing_key = message.substring(6, 7);
                String msg_return = message.substring(9) + " : DONE";

                if(routing_key.equals("1")) {
                    order_return_1.basicPublish("Main", ORDER_RETURN_1, null, msg_return.getBytes(StandardCharsets.UTF_8));

                } else {
                    order_return_2.basicPublish("Main", ORDER_RETURN_2, null, msg_return.getBytes(StandardCharsets.UTF_8));

                }

                channel_equip.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        System.out.println("Waiting for job to do...");

        channel_space.basicConsume(SPACE_QUEUE, false, consumer_people);
        channel_equip.basicConsume(EQUIP_QUEUE, false, consumer_equip);

    }
}
