package ermakov.onlinebanking.model.client;

import ermakov.onlinebanking.view.Authorization.EnterDialog;
import java.awt.Component;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        EnterDialog dialog = new EnterDialog();
        dialog.setTitle("Авторизация");
        dialog.pack();
        dialog.setLocationRelativeTo((Component)null);
        dialog.setVisible(true);
    }
}
