package working;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static working.Server.allClients;

// ClientHandler class
// Controll all active clients and streams between them
public class ClientHandler implements Runnable
{
    private String name;
    private final PrintWriter out;
    private final BufferedReader in;

    String getName() {
        return name;
    }

    // constructor
    public ClientHandler(String name, PrintWriter out, BufferedReader in) {
        this.name = name;
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        String received;
        while (true) {

            try {
                // receive the string and show message on Server
                received = in.readLine();
                System.out.println(this.name + " : " + received);

                // send the message for all other active clients

                String finalReceived = received;
                allClients.stream()
                        .filter(element -> !this.name.equals(element.getName()))
                        .forEach(element -> {
                            element.out.println(this.name + " : " + finalReceived);
                        });

            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }
}