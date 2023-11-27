package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String str = "SELECT * From users Where login = '" + obj.getLogin() + "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(str);
        String status = "";

        String[] item;
        for(Iterator<String[]> it = result.iterator(); it.hasNext(); status = item[3]) {
            item = (String[])it.next();
        }

        return status;
    }

    public void insert(User obj) {
        String query = "INSERT INTO users (login, password, status, email, name, secondName, patronymic, passportSeries, passportNumber, bankid) " +
                "VALUES ('" + obj.getLogin() + "', '" + obj.getPassword() + "', '" + obj.getStatus() + "', '" +
                obj.getEmail() + "', '" + obj.getName() + "', '" + obj.getSecondName() + "', '" +
                obj.getPatronymic() + "', '" + obj.getPassportSeries() + "', '" + obj.getPassportNumber() + "', '" + 1 + "')";
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User selectUsers(User obj) {
        String str = "SELECT * From users Where login = '" + obj.getLogin() + "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(str);
        User user = new User();
        Iterator var5 = result.iterator();

        while(var5.hasNext()) {
            String[] items = (String[])var5.next();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setEmail(items[4]);
            user.setName(items[5]);
            user.setSecondName(items[6]);
            user.setPatronymic(items[7]);
            user.setPassportSeries(items[8]);
            user.setPassportNumber(Integer.parseInt(items[9]));
        }
        return user;
    }

    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        int count = 0;

        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработка ошибок, например, логирование
        }

        return count > 0;
    }

    public boolean updatePassword(String email, String password){
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<User> selectAllUsers() {
        String str = "SELECT * FROM users";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(str);
        ArrayList<User> userList = new ArrayList<>();

        for (String[] items : result) {
            User user = new User();
            user.setIdUser(Integer.parseInt(items[0]));
            user.setLogin(items[1]);
            user.setPassword(items[2]);
            user.setStatus(items[3]);
            user.setEmail(items[4]);
            user.setName(items[5]);
            user.setSecondName(items[6]);
            user.setPatronymic(items[7]);
            user.setPassportSeries(items[8]);
            user.setPassportNumber(Integer.parseInt(items[9]));
            userList.add(user);
        }
        return userList;
    }
}

