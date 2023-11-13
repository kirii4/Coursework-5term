package ermakov.onlinebanking.server;

import ermakov.onlinebanking.database.SQLFactory;
import ermakov.onlinebanking.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
                    case "enter":{
                        User user = (User)sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        String status = sqlFactory.getUsers().findUser(user);
                        if (status == "")
                            soos.writeObject("error");
                        else {
                            soos.writeObject("ok");
                            soos.writeObject(status);
                        }
                    }break;
                    case "registrationUser":{
                        System.out.println("Запрос к БД на проверку пользователя(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        System.out.println(sois.readObject());
                        User user = (User)sois.readObject();

                        SQLFactory sqlFactory = new SQLFactory();

                        if (sqlFactory.getUsers().findUser(user).equals("")) {
                            soos.writeObject("OK");
                            sqlFactory.getUsers().insert(user);
                        }
                        else{
                            soos.writeObject("This user is already existed");
                        }
                    }break;
                    case "exit": {
                        this.soos.writeObject("OK");
                        this.soos.close();
                        this.sois.close();
                        System.out.println("Client " + this.clientSocket.getInetAddress().toString() + "disconnected.");
                    }
                }
            }
        } catch (IOException var7) {
            System.out.println("Client disconnected (" + this.clientSocket.getInetAddress().toString() + ").");
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
        }

    }
}