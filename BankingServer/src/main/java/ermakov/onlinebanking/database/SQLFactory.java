package ermakov.onlinebanking.database;

public class SQLFactory extends AbstractFactory {
    public SQLFactory() {
    }

    public SQLUsers getUsers() {
        return SQLUsers.getInstance();
    }

    public SQLCategory getCategotyes() {
        return SQLCategory.getInstance();
    }
}
