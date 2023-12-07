package ermakov.onlinebanking.model.Helper;

import ermakov.onlinebanking.model.Payment;
import ermakov.onlinebanking.model.User;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableUtil {
    public static void populateUserTable(JTable table, ArrayList<User> userList) {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Почта", "Логин", "Роль"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (User user : userList) {
            Object[] rowData = {
                    user.getEmail(),
                    user.getLogin(),
                    user.getStatus()
            };
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
    }

    public static void populatePaymentTable(JTable table, ArrayList<Payment> userList) {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Платеж", "Сумма", "Дата", "Карта"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Payment payment : userList) {
            Object[] rowData = {
                    payment.getPaymentType(),
                    payment.getAmount(),
                    payment.getDate(),
                    payment.getCardNumber(),
            };
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
    }
}