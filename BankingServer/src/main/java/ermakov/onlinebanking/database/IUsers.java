package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.User;

import java.util.ArrayList;

public interface IUsers {
    String findUser(User var1);

    ArrayList<User> selectAllUsers();

    void insert(User var1);

    User selectUsers(User var1);

    boolean isEmailExists(String email);
}
