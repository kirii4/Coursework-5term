package Ermakov.Server.database;

public class SQLFactory extends AbstractFactory {
    public SQLFactory() {
    }

    public SQLUsers getUsers() {
        return SQLUsers.getInstance();
    }
}
