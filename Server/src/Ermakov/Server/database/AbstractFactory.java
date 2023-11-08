package Ermakov.Server.database;

public abstract class AbstractFactory {
    public AbstractFactory() {
    }

    public abstract SQLUsers getUsers();
}