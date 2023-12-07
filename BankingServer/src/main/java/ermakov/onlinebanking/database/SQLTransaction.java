package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Payment;
import ermakov.onlinebanking.model.User;

import java.sql.Date;
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

    public boolean doPayment(Payment payment) {
        String getCategoryQuery = "SELECT c.CategoryId, sc.SubcategoryID " +
                "FROM Categories c " +
                "INNER JOIN Subcategories sc ON c.CategoryID = sc.CategoryID " +
                "WHERE sc.SubcategoryName = ?";

        try (PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(getCategoryQuery)) {
            preparedStatement.setString(1, payment.getPaymentType());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int categoryId = resultSet.getInt("CategoryId");
                    int subcategoryId = resultSet.getInt("SubcategoryID");

                    String getAccountNumberQuery = "SELECT AccountNumber FROM CreditCards WHERE CardNumber = ?";
                    try (PreparedStatement accountNumberStatement = dbConnection.getConnection().prepareStatement(getAccountNumberQuery)) {
                        String modifiedCardNumber = payment.getCardNumber().replace(" ", "");
                        accountNumberStatement.setString(1, modifiedCardNumber);
                        try (ResultSet accountNumberResultSet = accountNumberStatement.executeQuery()) {
                            if (accountNumberResultSet.next()) {
                                String accountNumber = accountNumberResultSet.getString("AccountNumber");

                                String insertQuery = "INSERT INTO Payments (PaymentType, Amount, Date, AccountNumber, CategoryID, SubcategoryID, CardNumber) " +
                                        "VALUES ('" + payment.getPaymentType() + "', " + payment.getAmount() + ", '" +
                                        payment.getDate() + "', '" + accountNumber + "', '"
                                        + categoryId + "', " + subcategoryId + ", '" +
                                        payment.getCardNumber() + "')";
                                this.dbConnection.execute(insertQuery);
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Payment> selectUserPayments(String email) {
        ArrayList<Payment> paymentsList = new ArrayList<>();
        try {
            String getAccountNumberQuery = "SELECT a.AccountNumber " +
                    "FROM Users u " +
                    "INNER JOIN Accounts a ON u.UserID = a.UserID " +
                    "WHERE u.Email = ?";

            try (PreparedStatement accountNumberStatement = dbConnection.getConnection().prepareStatement(getAccountNumberQuery)) {
                accountNumberStatement.setString(1, email);

                try (ResultSet accountNumberResultSet = accountNumberStatement.executeQuery()) {
                    if (accountNumberResultSet.next()) {
                        String accountNumber = accountNumberResultSet.getString("AccountNumber");

                        String getPaymentsQuery = "SELECT * FROM Payments WHERE AccountNumber = ?";
                        try (PreparedStatement paymentsStatement = dbConnection.getConnection().prepareStatement(getPaymentsQuery)) {
                            paymentsStatement.setString(1, accountNumber);

                            try (ResultSet paymentsResultSet = paymentsStatement.executeQuery()) {
                                while (paymentsResultSet.next()) {
                                    Payment payment = new Payment();
                                    payment.setIdPayment(paymentsResultSet.getInt("PaymentID"));
                                    payment.setPaymentType(paymentsResultSet.getString("PaymentType"));
                                    payment.setAmount(paymentsResultSet.getDouble("Amount"));
                                    payment.setDate(paymentsResultSet.getDate("Date"));
                                    payment.setAccountNumber(paymentsResultSet.getString("AccountNumber"));
                                    payment.setCardNumber(paymentsResultSet.getString("CardNumber"));
                                    paymentsList.add(payment);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentsList;
    }

}
