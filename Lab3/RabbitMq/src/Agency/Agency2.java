package Agency;

import Administratory.Admin;
import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Agency2 {

    private static String name = "Agency2";

    public static void main(String[] args) throws Exception {

        System.out.println(name);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel_people = connection.createChannel();
        channel_people.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);
        Channel channel_equip = connection.createChannel();
        channel_equip.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);
        Channel channel_space = connection.createChannel();
        channel_space.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String PEOPLE_QUEUE = "people_queue";
        String EQUIP_QUEUE = "equip_queue";
        String SPACE_QUEUE = "space_queue";

        channel_people.queueDeclare(PEOPLE_QUEUE, false, false, false, null);
        channel_people.queueBind(PEOPLE_QUEUE, "Main", "people_queue");
        channel_equip.queueDeclare(EQUIP_QUEUE, false, false, false, null);
        channel_equip.queueBind(EQUIP_QUEUE, "Main", "equip_queue");
        channel_space.queueDeclare(SPACE_QUEUE, false, false, false, null);
        channel_space.queueBind(SPACE_QUEUE, "Main", "space_queue");

        // channel for order return ack
        Channel order_return_2 = connection.createChannel();
        order_return_2.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String ORDER_RETURN_2 = "second";
        order_return_2.queueDeclare(ORDER_RETURN_2, false, false, false, null);
        order_return_2.queueBind(ORDER_RETURN_2, "Main", "second");

        BufferedReader br = new BufferedReader(new
                InputStreamReader(System.in));

        Consumer order_return_consumer = new DefaultConsumer(order_return_2) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String (body, StandardCharsets.UTF_8);

                System.out.println(message);

                order_return_2.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        order_return_2.basicConsume(ORDER_RETURN_2, false, order_return_consumer);

        while (true) {
            String msg = br.readLine();
            String message = name + " : " + Admin.id_ag2 + " : " + msg;
            Admin.id_ag2+=2;

            if(msg.equals("exit")) {
                break;
            }
            else
            if(msg.equals("people")){
                channel_people.basicPublish("Main", PEOPLE_QUEUE, null ,message.getBytes());
                System.out.println("Sending task to carriers!");
            }
            else
            if(msg.equals("equip")){
                channel_equip.basicPublish("Main", EQUIP_QUEUE, null, message.getBytes());
                System.out.println("Sending task to carriers!");
            }
            else
            if(msg.equals("space")){
                channel_space.basicPublish("Main", SPACE_QUEUE, null, message.getBytes());
                System.out.println("Sending task to carriers!");
            }
            else {
                System.out.println("Not a correct order!");
            }
        }

        channel_people.close();
        channel_equip.close();
        channel_space.close();
        connection.close();
    }
}
