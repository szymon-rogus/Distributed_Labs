package working;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

import static working.Server.data;

public class UdpHandler implements Runnable {

    private DatagramSocket socket;

    public UdpHandler(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String rcv;
        byte[] rcvBuff = new byte[1024];

        while(true) {
            try {
                // receive UDP data from anyone
                Arrays.fill(rcvBuff, (byte)0);
                DatagramPacket rcvPacket = new DatagramPacket(rcvBuff, rcvBuff.length);
                socket.receive(rcvPacket);
                String msg = new String(rcvPacket.getData(), 0, rcvPacket.getLength());

                // check if this is a new client or one already known
                if(!data.containsKey(rcvPacket.getPort()))
                    data.put(rcvPacket.getPort(), rcvPacket.getAddress());
                else {
                    System.out.println(msg + " : " + rcvPacket.getPort() + " : " + rcvPacket.getAddress());
                    msg = msg + " : " + rcvPacket.getPort();
                    byte[] send = msg.getBytes();

                    data.entrySet().stream()
                            .filter(entry -> !entry.getKey().equals(rcvPacket.getPort()))
                            .forEach(entry -> {
                                DatagramPacket sendPacket = new DatagramPacket(send, send.length, entry.getValue(), entry.getKey());
                                try {
                                    socket.send(sendPacket);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
