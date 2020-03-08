package working;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Map;

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

                    for(Map.Entry<Integer, InetAddress> entry : dataMulticast.entrySet()) {
                        if(!(rcvPacket.getPort() == entry.getKey())){
                            DatagramPacket sendPacket = new DatagramPacket(send, send.length, entry.getValue(), entry.getKey());
                            socket.send(sendPacket);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
