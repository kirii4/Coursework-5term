package ermakov.onlinebanking.server;

public class StartServer {
    public static final int PORT_WORK = 5000;

    public StartServer() {
    }

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        (new Thread(server)).start();
    }
}