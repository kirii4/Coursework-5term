package ermakov.onlinebanking.database;

import ermakov.onlinebanking.model.User;

import java.util.ArrayList;

public interface IUsers {
    String findUser(User user);
    ArrayList<User> selectAllUsers();
    void insert(User user);
    User selectUsers(User user);
    boolean isEmailExists(String email);
    boolean updatePassword(String email, String password);
    boolean editUser(String email, String newValue, String type);
    boolean deleteUser(String email);
}
