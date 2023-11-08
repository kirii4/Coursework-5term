package Ermakov.Server.server;

import Ermakov.Server.database.SQLFactory;
import Ermakov.Server.model.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class Worker implements Runnable {
    protected Socket clientSocket = null;
    ObjectInputStream sois;
    ObjectOutputStream soos;

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            this.sois = new ObjectInputStream(this.clientSocket.getInputStream());
            this.soos = new ObjectOutputStream(this.clientSocket.getOutputStream());
            System.out.println("Client " + this.clientSocket.getInetAddress().toString() + " connected.");

            while(true) {
                switch (this.sois.readObject().toString()) {
                    case "enter":
                        System.out.println("Пришел запрос на вход. Перед десериализацией User.");
                        User user = (User)this.sois.readObject();
                        System.out.println("Десериализован объект User: {" + user.toString() + "}");
                        SQLFactory sqlFactory = new SQLFactory();
                        String status = sqlFactory.getUsers().findUser(user);
                        if (Objects.equals(status, "")) {
                            this.soos.writeObject("error");
                        } else {
                            this.soos.writeObject("ok");
                            this.soos.writeObject(status);
                        }
                        break;
                    case "exit":
                        this.soos.writeObject("OK");
                        this.soos.close();
                        this.sois.close();
                        System.out.println("Client " + this.clientSocket.getInetAddress().toString() + "disconnected.");
                }
            }
        } catch (IOException var7) {
            System.out.println("Client disconnected (" + this.clientSocket.getInetAddress().toString() + ").");
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
        }

    }
}