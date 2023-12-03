package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLTransaction implements ITransaction{
    private static SQLTransaction instance;
    private ConnectionDB dbConnection = ConnectionDB.getInstance();

    public SQLTransaction(){

    }

    public static synchronized SQLTransaction getInstance() {
        if (instance == null) {
            instance = new SQLTransaction();
        }
        return instance;
    }

    public String getCardNumber(int idUser) {
        String cardNumber = null;
        String query = "SELECT cc.CardNumber " +
                "FROM creditcards cc " +
                "INNER JOIN accounts a ON cc.AccountNumber = a.AccountNumber " +
                "INNER JOIN users u ON a.UserID = u.UserID " +
                "WHERE u.UserID = '" + idUser + "'";

        ArrayList<String[]> result = dbConnection.getArrayResult(query);
        if (!result.isEmpty()) {
            cardNumber = result.get(0)[0];
        }
        return cardNumber;
    }
}
