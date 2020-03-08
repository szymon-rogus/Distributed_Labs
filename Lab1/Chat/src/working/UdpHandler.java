package working;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;
import java.util.Map;

import static working.Server.data;
import static working.Server.dataMulticast;

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
                System.out.println(data.size() + ": " + dataMulticast.size());
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
                    byte[] send = msg.getBytes();

                    for(Map.Entry<Integer, InetAddress> entry : data.entrySet()) {
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
