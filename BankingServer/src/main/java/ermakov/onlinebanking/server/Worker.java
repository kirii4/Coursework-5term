package ermakov.onlinebanking.server;

import ermakov.onlinebanking.database.SQLCategory;
import ermakov.onlinebanking.database.SQLFactory;
import ermakov.onlinebanking.gmail.GMailer;
import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Subcategory;
import ermakov.onlinebanking.model.User;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ermakov.onlinebanking.gmail.GMailer.*;

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
            String email = new String();

            System.out.println("Client " + this.clientSocket.getInetAddress().toString() + " connected.");

            while(true) {
                switch (this.sois.readObject().toString()) {
                    case "enter":{
                        User user = (User)sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        String status = sqlFactory.getUsers().findUser(user);
                        if (status == "" || sqlFactory.getUsers().isEmailExists(user.getEmail()))
                            soos.writeObject("error");
                        else {
                            soos.writeObject("ok");
                            soos.writeObject(status);
                        }
                    }break;
                    case "registrationUser":{
                        System.out.println("Запрос к БД на проверку пользователя(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        User user = (User)sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getUsers().findUser(user).equals("") || sqlFactory.getUsers().isEmailExists(user.getEmail())) {
                            soos.writeObject("OK");
                            sqlFactory.getUsers().insert(user);
                        }
                        else{
                            soos.writeObject("This user is already existed");
                        }
                    }break;
                    case "forgotPassword": {
                        System.out.println("Запрос к БД на проверку почты пользователя(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        email = (String) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getUsers().isEmailExists(email)){
                            GMailer gMailer = new GMailer();
                            gMailer.sendMessage(email);
                            soos.writeObject("ok");
                        } else {
                            soos.writeObject("This email will not find");
                        }
                    }break;
                    case "forgotPasswordCode": {
                        String sendCode = (String) sois.readObject();
                        String _code = String.valueOf(GMailer.getCode());
                        if (_code.equals(sendCode)){
                            soos.writeObject("OK");
                        }else{
                            soos.writeObject("This code is wrong");
                        }
                    }break;
                    case "resetPassword":{
                        String password = (String) sois.readObject();
                        SQLFactory sqlFactory = new SQLFactory();
                        if (sqlFactory.getUsers().updatePassword(email, password)){
                            soos.writeObject("ok");
                        } else {
                            soos.writeObject("Error");
                        }
                    }break;
                    case "getInfAboutUser":{
                        System.out.println("Запрос к БД на получение информации о пользователе(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        User user = (User)sois.readObject();
                        User newUser = sqlFactory.getUsers().selectUsers(user);
                        soos.writeObject(newUser);
                    }break;
                    case "getPayments":{
                        System.out.println("Запрос к БД на получение информации о платежах(таблица Category), клиент: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        Map<Category, List<Subcategory>> category = sqlFactory.getCategotyes().getCategotyes();
                        soos.writeObject(category);
                    }break;
                    case "getUsers":{
                        System.out.println("Запрос к БД на получение информации о пользователях(таблица User), клиент: " + clientSocket.getInetAddress().toString());
                        SQLFactory sqlFactory = new SQLFactory();
                        ArrayList<User> users = sqlFactory.getUsers().selectAllUsers();
                        soos.writeObject(users);
                    }break;
                    case "exit": {
                        this.soos.writeObject("OK");
                        this.soos.close();
                        this.sois.close();
                        System.out.println("Client " + this.clientSocket.getInetAddress().toString() + "disconnected.");
                    }break;
                }
            }
        } catch (IOException var7) {
            System.out.println("Client disconnected (" + this.clientSocket.getInetAddress().toString() + ").");
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}