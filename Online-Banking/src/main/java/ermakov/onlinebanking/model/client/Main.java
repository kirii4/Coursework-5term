package ermakov.onlinebanking.model.client;

import ermakov.onlinebanking.view.Authorization.Authorization;
import java.awt.Component;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Authorization dialog = new Authorization();
        dialog.setTitle("Авторизация");
        dialog.pack();
        dialog.setLocationRelativeTo((Component)null);
        dialog.setVisible(true);
    }
}
