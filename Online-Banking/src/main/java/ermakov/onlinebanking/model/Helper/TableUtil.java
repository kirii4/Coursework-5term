package ermakov.onlinebanking.model.Helper;

import ermakov.onlinebanking.model.User;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableUtil {
    public static void populateTable(JTable table, ArrayList<User> userList) {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Почта", "Логин", "Пароль", "Роль"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (User user : userList) {
            Object[] rowData = {
                    user.getEmail(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getStatus()
            };
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
    }
}