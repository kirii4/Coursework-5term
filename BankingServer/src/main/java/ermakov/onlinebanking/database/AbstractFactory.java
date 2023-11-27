package ermakov.onlinebanking.database;

public abstract class AbstractFactory {
    public AbstractFactory() {
    }

    public abstract SQLUsers getUsers();
    public abstract SQLCategory getCategotyes();
}