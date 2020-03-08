package working;


// Java implementation for multithreaded chat client
// Save file as Client.java

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

import static working.Server.clientID;

public class Client {

    private final static int ServerPort = 8000;

    public static void main(String args[]) throws IOException {

        Scanner scn = new Scanner(System.in);

        InetAddress adress = InetAddress.getByName("127.0.0.1");
        InetAddress group = InetAddress.getByName("233.0.0.1");

        //sockets
        Socket serverSocket = new Socket(adress, ServerPort);
        DatagramSocket udpSocket = new DatagramSocket();
        MulticastSocket multicastSocket = new MulticastSocket();
        try {
            multicastSocket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
        udpSocket.setBroadcast(true);

        // input and out streams
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

        udpSend(udpSocket, adress);

        multicastSend(multicastSocket, group);

        // sendMessage thread
        Thread sendMessage = new Thread(() -> {
            while (true) {

                // read the message to deliver.
                String msg = scn.nextLine();

                switch (msg) {
                    case "U": {
                        System.out.println("UDP message:");
                        msg = scn.nextLine();
                        byte[] send = msg.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(send, send.length, adress, ServerPort);
                        try {
                            udpSocket.send(sendPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "M": {
                        System.out.println("Multicast UDP message:");
                        msg = scn.nextLine();
                        byte[] send = msg.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(send, send.length, group, 8008);
                        try {
                            multicastSocket.send(sendPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    default:
                        // write on the output stream
                        out.println(msg);
                        break;
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(() -> {

            while (true) {
                try {
                    // read the message sent from anyone
                    String msg = in.readLine();
                    System.out.println(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // readMessage thread
        Thread readUDPMessage = new Thread(() -> {

            byte[] rcvBuffer = new byte[1024];
            while (true) {
                try {
                    // read the message sent from anyone
                    Arrays.fill(rcvBuffer, (byte)0);
                    DatagramPacket receivePacket = new DatagramPacket(rcvBuffer, rcvBuffer.length);
                    udpSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println(msg + " by UDP");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        sendMessage.start();
        readMessage.start();
        readUDPMessage.start();
    }

    private static void udpSend(DatagramSocket udpSocket, InetAddress adress) throws IOException {
        byte[] sendBuff = "UDP".getBytes();

        DatagramPacket sendTrial = new DatagramPacket(sendBuff, sendBuff.length, adress, ServerPort);
        udpSocket.send(sendTrial);
    }

    private static void multicastSend(MulticastSocket socket, InetAddress group) throws IOException {
        byte[] sendBuff = "Multicast".getBytes();

        DatagramPacket sendTrial = new DatagramPacket(sendBuff, sendBuff.length, group, ServerPort);
        socket.send(sendTrial);
    }
}
