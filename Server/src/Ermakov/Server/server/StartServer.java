package Ermakov.Server.server;

public class StartServer {
    public static final int PORT_WORK = 5000;

    public StartServer() {
    }

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(5000);
        (new Thread(server)).start();
    }
}