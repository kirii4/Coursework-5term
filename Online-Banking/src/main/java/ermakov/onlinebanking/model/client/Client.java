package ermakov.onlinebanking.model.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String message;

    public Client(String ipAddress, String port) {
        try {
            this.clientSocket = new Socket(ipAddress, Integer.parseInt(port));
            this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.inStream = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (IOException socketException) {
            socketException.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            this.outStream.writeObject(message);
        } catch (IOException MessageException) {
            MessageException.printStackTrace();
        }
    }

    public void sendObject(Object object) {
        try {
            this.outStream.writeObject(object);
        } catch (IOException sendObjectException) {
            sendObjectException.printStackTrace();
        }
    }

    public String readMessage() throws IOException {
        try {
            this.message = (String)this.inStream.readObject();
        } catch (IOException | ClassNotFoundException readMessageException) {
            readMessageException.printStackTrace();
        }
        return this.message;
    }

    public Object readObject() {
        Object object = new Object();
        try {
            object = this.inStream.readObject();
        } catch (IOException | ClassNotFoundException readObjectException) {
            readObjectException.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            this.clientSocket.close();
            this.inStream.close();
            this.outStream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}