package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.Payment;
import ermakov.onlinebanking.model.User;

import java.util.ArrayList;

public interface ITransaction {
    String getCardNumber(int idUser);

    boolean doPayment(Payment payment);

    ArrayList<Payment> selectUserPayments(String email);
}
