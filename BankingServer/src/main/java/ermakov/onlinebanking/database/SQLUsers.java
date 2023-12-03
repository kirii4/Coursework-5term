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
        String userQuery = "INSERT INTO Users (login, password, status, email, name, secondName, patronymic, passportSeries, passportNumber, bankid) " +
                "VALUES ('" + obj.getLogin() + "', '" + obj.getPassword() + "', '" + obj.getStatus() + "', '" +
                obj.getEmail() + "', '" + obj.getName() + "', '" + obj.getSecondName() + "', '" +
                obj.getPatronymic() + "', '" + obj.getPassportSeries() + "', '" + obj.getPassportNumber() + "', '" + 1 + "')";
        this.dbConnection.execute(userQuery);

        String getUserIdQuery = "SELECT UserID FROM Users WHERE email = '" + obj.getEmail() + "'";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(getUserIdQuery);
        if (!result.isEmpty()) {
            String userId = result.get(0)[0];
            String accountNumber = generateAccountNumber();
            String accountQuery = "INSERT INTO Accounts (AccountNumber, AccountType, Balance, UserID) " +
                    "VALUES ('" + accountNumber + "', 'Default', 1500, " + userId + ")";
            this.dbConnection.execute(accountQuery);

            String creditCardQuery = "INSERT INTO CreditCards (CardNumber, CreditLimit, AvailableCredit, AccountNumber) " +
                    "VALUES ('" + generateCreditCardNumber() + "', 0, 0, '" + accountNumber + "')";
            this.dbConnection.execute(creditCardQuery);

            String balanceQuery = "INSERT INTO Balances (Amount, Date, AccountNumber) " +
                    "VALUES (1500, NOW(), '" + accountNumber + "')";
            this.dbConnection.execute(balanceQuery);
        } else {
            System.out.println("Ошибка при получении ID пользователя.");
        }
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    private String generateCreditCardNumber() {
        String prefix = "1305";
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String suffix = currentTimeMillis.substring(currentTimeMillis.length() - 12);
        return prefix + suffix;
    }

    private String generateSavingsAccountNumber() {
        return "SAV" + System.currentTimeMillis();
    }

    public User selectUsers(User obj) {
        String str = "SELECT * From users Where login = '" + obj.getLogin() + "' and password = '" + obj.getPassword() + "'";
        ArrayList<String[]> result = this.dbConnection.getArrayResult(str);
        User user = new User();
        Iterator it = result.iterator();

        while(it.hasNext()) {
            String[] items = (String[])it.next();
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
            e.printStackTrace();
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

    public boolean editUser(String email, String newValue, String type) {
        String updateQuery = "";
        switch (type) {
            case "Почта":
                updateQuery = "UPDATE users SET email = '" + newValue + "' WHERE email = '" + email + "'";
                break;
            case "Логин":
                updateQuery = "UPDATE users SET login = '" + newValue + "' WHERE email = '" + email + "'";
                break;
            case "Роль":
                updateQuery = "UPDATE users SET status = '" + newValue + "' WHERE email = '" + email + "'";
                break;
            default:
                System.out.println("Неверно указан тип редактируемого поля.");
                return false;
        }
        this.dbConnection.execute(updateQuery);
        return true;
    }

    public boolean deleteUser(String email) {
        try {
            String checkUserQuery = "SELECT * FROM users WHERE email = '" + email + "'";
            ArrayList<String[]> resultUser = this.dbConnection.getArrayResult(checkUserQuery);

            if (!resultUser.isEmpty()) {
                String userId = resultUser.get(0)[0];

                String deleteAccountsQuery = "DELETE FROM accounts WHERE UserID = " + userId;
                String deleteTransactionsQuery = "DELETE FROM transactions WHERE AccountNumber IN (SELECT AccountNumber FROM accounts WHERE UserID = " + userId + ")";
                String deletePaymentsQuery = "DELETE FROM payments WHERE AccountNumber IN (SELECT AccountNumber FROM accounts WHERE UserID = " + userId + ")";
                String deleteCreditCardsQuery = "DELETE FROM creditcards WHERE AccountNumber IN (SELECT AccountNumber FROM accounts WHERE UserID = " + userId + ")";
                String deleteSavingsAccountsQuery = "DELETE FROM savingsaccounts WHERE AccountNumber IN (SELECT AccountNumber FROM accounts WHERE UserID = " + userId + ")";
                String deleteBalancesQuery = "DELETE FROM balances WHERE AccountNumber IN (SELECT AccountNumber FROM accounts WHERE UserID = " + userId + ")";

                this.dbConnection.execute(deleteTransactionsQuery);
                this.dbConnection.execute(deletePaymentsQuery);
                this.dbConnection.execute(deleteCreditCardsQuery);
                this.dbConnection.execute(deleteSavingsAccountsQuery);
                this.dbConnection.execute(deleteBalancesQuery);
                this.dbConnection.execute(deleteAccountsQuery);

                String deleteUserQuery = "DELETE FROM users WHERE email = '" + email + "'";
                this.dbConnection.execute(deleteUserQuery);

                return true;
            } else {
                System.out.println("Пользователь с email '" + email + "' не найден.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

