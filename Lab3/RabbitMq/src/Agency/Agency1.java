package Agency;

import Administratory.Admin;
import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Agency1 {

    private static String name = "Agency1";

    public static void main(String[] args) throws Exception {

        System.out.println(name);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        // channel for tasks
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        // channel for order return ack
        Channel order_return_1 = connection.createChannel();
        order_return_1.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);

        String ORDER_RETURN_1 = "first";
        order_return_1.queueDeclare(ORDER_RETURN_1, false, false, false, null);
        order_return_1.queueBind(ORDER_RETURN_1, "Main", "first");

        // channel for admin
        Channel communication = connection.createChannel();
        communication.exchangeDeclare("Main", BuiltinExchangeType.TOPIC);
        String COMMUNICATION_QUEUE = communication.queueDeclare().getQueue();
        communication.queueBind(COMMUNICATION_QUEUE, "Main", "agency.#");

        Consumer admin_consumer = new DefaultConsumer(communication) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String (body, StandardCharsets.UTF_8);

                System.out.println(message);

                communication.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        communication.basicConsume(COMMUNICATION_QUEUE, false, admin_consumer);

        Consumer order_return_consumer = new DefaultConsumer(order_return_1) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String (body, StandardCharsets.UTF_8);

                System.out.println(message);

                order_return_1.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        order_return_1.basicConsume(ORDER_RETURN_1, false, order_return_consumer);

        BufferedReader br = new BufferedReader(new
                InputStreamReader(System.in));

        while (true) {
            String msg = br.readLine();
            String message = name + " : " + Admin.id_ag1 + " : " + msg;
            Admin.id_ag1+=2;

            if(msg.equals("exit")) {
                break;
            }
            else
            if(msg.equals("people")){
                channel.basicPublish("Main", "people", null ,message.getBytes());
                System.out.println("Sending task to carriers!");
            }
            else
            if(msg.equals("equip")){
                channel.basicPublish("Main", "equip", null, message.getBytes());
                System.out.println("Sending task to carriers!");
            }
            else
            if(msg.equals("space")){
                channel.basicPublish("Main", "space", null, message.getBytes());
                System.out.println("Sending task to carriers!");
            }
            else {
                System.out.println("Not a correct order!");
            }
        }

        channel.close();
        connection.close();
    }
}
