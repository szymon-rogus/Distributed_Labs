package events.serverSide;

import java.time.LocalDateTime;

public class Server {
    static final ServerMessages server = new ServerMessages();

    public static void main(String[] args) throws InterruptedException {
        server.start();
        sendTime().start();
        Thread.sleep(2000);
        sendDeviceInfo().start();
        server.blockUntilShutdown();
    }

    public static Date getDate() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        return new Date(hour, minute, second);
    }


    static Thread sendTime() {
        return new Thread(() -> {
            Date message;
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    message = getDate();
                    server.sendTime(message);
                } catch (Exception ignored) {
                }
            }
        });
    }

    static Thread sendDeviceInfo() {
        return new Thread(() ->{
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    server.sendDevice(System.getProperty("os.name")
                            + " : " + System.getProperty("os.version"));
                } catch (Exception ignored) {
                }
            }
        });
    }
}
