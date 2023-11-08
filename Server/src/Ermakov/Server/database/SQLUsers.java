package Ermakov.Server.database;

import Ermakov.Server.model.User;
import java.util.ArrayList;
import java.util.Iterator;

public class SQLUsers implements IUsers {
    private static SQLUsers instance;
    private ConnectionDB dbConnection = ConnectionDB.getInstance();

    private SQLUsers() {
    }

    public static synchronized SQLUsers getInstance() {
        if (instance == null) {
            instance = new SQLUsers();
        }

        return instance;
    }

    public String findUser(User obj) {
        String var10000 = obj.getLogin();
        String str = "SELECT * From users Where login = '" + var10000 + "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(str);
        String status = "";

        String[] item;
        for(Iterator var5 = result.iterator(); var5.hasNext(); status = item[3]) {
            item = (String[])var5.next();
        }

        return status;
    }

    public void insert(User obj) {
        String var10000 = obj.getLogin();
        String str = "INSERT INTO users (login, password, status, name, secondName, patronymic, passportSeries, passportNumber, role) VALUES('" + var10000 + "', '" + obj.getPassword() + "', '" + obj.getStatus() + "', '" + obj.getName() + "', '" + obj.getSecondName() + "', '" + obj.getPatronymic() + "', '" + obj.getPassportSeries() + "', " + obj.getPassportNumber() + "', " + obj.getRole() + ")";
        this.dbConnection.execute(str);
    }

    public User selectUsers(User obj) {
        String var10000 = obj.getLogin();
        String str = "SELECT * From users Where login = '" + var10000 + "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(str);
        User user = new User();
        Iterator var5 = result.iterator();

        while(var5.hasNext()) {
            String[] items = (String[])var5.next();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setName(items[4]);
            user.setSecondName(items[5]);
            user.setPatronymic(items[6]);
            user.setPassportSeries(items[7]);
            user.setPassportNumber(Integer.parseInt(items[8]));
            user.setRole(items[9]);
        }

        return user;
    }
}

