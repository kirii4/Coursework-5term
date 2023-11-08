package Ermakov.Server.database;

import Ermakov.Server.model.User;

public interface IUsers {
    String findUser(User var1);

    void insert(User var1);

    User selectUsers(User var1);
}
