package working;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    // Vector to store active clients
    static Vector<ClientHandler> allClients = new Vector<>();

    // Vector to store data needed for UDP messages
    static Map<Integer, InetAddress> data = new HashMap<>();

    // Vector to store data needed for Multicast messages
    static Map<Integer, InetAddress> dataMulticast = new HashMap<>();

    // counter for clients
    protected static int clientID = 1;

    public static void main(String[] args) throws IOException {
        // Some necessary sockets
        ServerSocket serverSocket = new ServerSocket(8000);
        DatagramSocket udpSocket = new DatagramSocket(8000);
        MulticastSocket multicastSocket = new MulticastSocket(8008);
        Socket clientSocket;

        InetAddress group = null;
        try {
            group = InetAddress.getByName("233.0.0.1");
            multicastSocket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // One to rule them all :)
        UdpHandler udpHanldler = new UdpHandler(udpSocket);
        Thread udpThread = new Thread(udpHanldler);
        udpThread.start();

        // Multicast handler:

        MulticastHandler multicastHandler = new MulticastHandler(multicastSocket);
        Thread multicastThread = new Thread(multicastHandler);
        multicastThread.start();

        try {
            while (true) {
                // Accept the incoming request
                clientSocket = serverSocket.accept();

                System.out.println("New client request received : " + clientSocket);

                // obtain input and output streams
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                System.out.println("Creating a new handler for this client...");

                // Create a new handler object for handling this request.
                ClientHandler clientHandler = new ClientHandler("client " + clientID, out, in);
                out.println("Connected to Chat as: " + clientHandler.getName());

                // Create a new Thread with this object.
                Thread clientThread = new Thread(clientHandler);

                System.out.println("New client is active!");

                // add this client to active clients list
                allClients.add(clientHandler);

                // start the thread.
                clientThread.start();

                // just for naming next possible client
                clientID++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
            udpSocket.close();
            multicastSocket.close();
        }
    }
}
