package working;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.Arrays;

import static working.Server.dataMulticast;

public class MulticastHandler implements Runnable {

    private MulticastSocket socket;

    public MulticastHandler (MulticastSocket socket) {
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
                if(!dataMulticast.containsKey(rcvPacket.getPort()))
                    dataMulticast.put(rcvPacket.getPort(), rcvPacket.getAddress());
                else {
                    System.out.println(msg + " : " + rcvPacket.getPort() + " : " + rcvPacket.getAddress());
                    byte[] send = msg.getBytes();

                    dataMulticast.entrySet().stream()
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
