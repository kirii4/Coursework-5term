package ermakov.onlinebanking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer implements Runnable {
    protected int serverPort = 5000;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;

    public MultiThreadedServer(int port) {
        this.serverPort = port;
    }

    public void run() {
        this.openServerSocket();

        Socket clientSocket;
        for(; !this.isStopped(); (new Thread(new Worker(clientSocket))).start()) {
            clientSocket = null;

            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException socketException) {
                if (this.isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }

                throw new RuntimeException("Error accepting client connection", socketException);
            }
        }

        System.out.println("Server Stopped.");
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;

        try {
            this.serverSocket.close();
        } catch (IOException socketException) {
            throw new RuntimeException("Error closing server", socketException);
        }
    }

    private void openServerSocket() {
        System.out.println("Opening server socket...");

        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException socketException) {
            throw new RuntimeException("Cannot open port " + this.serverPort, socketException);
        }
    }
}