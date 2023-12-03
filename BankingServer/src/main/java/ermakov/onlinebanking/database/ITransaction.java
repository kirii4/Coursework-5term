package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.User;

public interface ITransaction {
    String getCardNumber(int idUser);
}
