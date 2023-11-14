package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.User;

public interface IUsers {
    String findUser(User var1);

    void insert(User var1);

    User selectUsers(User var1);

    boolean isEmailExists(String email);
}
