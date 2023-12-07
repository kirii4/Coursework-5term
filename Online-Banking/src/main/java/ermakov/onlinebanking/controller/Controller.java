package ermakov.onlinebanking.controller;

import ermakov.onlinebanking.model.Category;
import ermakov.onlinebanking.model.Helper.TableUtil;
import ermakov.onlinebanking.model.Helper.TreeUtil;
import ermakov.onlinebanking.model.Payment;
import ermakov.onlinebanking.model.Subcategory;
import ermakov.onlinebanking.model.User;
import ermakov.onlinebanking.model.client.Client;
import ermakov.onlinebanking.view.Authorization.Authorization;
import ermakov.onlinebanking.view.ForgotPassword.ForgotPassword;
import ermakov.onlinebanking.view.Form.Form;
import ermakov.onlinebanking.view.FormCasher.FormCasher;
import ermakov.onlinebanking.view.PaymentCasherForm.PaymentCasherForm;
import ermakov.onlinebanking.view.PaymentForm.PaymentForm;
import ermakov.onlinebanking.view.Registration.RegistrationUsers;
import ermakov.onlinebanking.view.UsersForm.UsersForm;
import ermakov.onlinebanking.view.ResetPassword.ResetPassword;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import java.sql.Date;

public class Controller implements ActionListener {
    private static Controller instance;
    private Authorization objAuthorization;
    private RegistrationUsers objRegistrarionUsers;
    private UsersForm objUsersForm;
    private Form objForm;
    private FormCasher objFormCasher;
    private PaymentForm objPaymentForm;
    private PaymentCasherForm objPaymentCasherForm;
    private ForgotPassword objForgotPassword;
    private ResetPassword objResetPassword;

    String msgLogin = new String();
    String role = new String();
    Map<Category, List<Subcategory>> category = new HashMap<>();
    User infUser = new User();
    String cardNumber = null;
    private final Client client = new Client("127.0.0.2", "5000");

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public void initialize(Authorization obj) {
        this.objAuthorization = obj;
    }
    public void initialize(RegistrationUsers obj) {
        this.objRegistrarionUsers = obj;
    }
    public void initialize(UsersForm obj) {
        this.objUsersForm = obj;
    }
    public void initialize(FormCasher obj) {
        this.objFormCasher = obj;
    }
    public void initialize(Form obj) {
        this.objForm = obj;
    }
    public void initialize(ForgotPassword obj) {
        this.objForgotPassword = obj;
    }
    public void initialize(ResetPassword obj) {
        this.objResetPassword = obj;
    }
    public void initialize(PaymentForm obj) {
        this.objPaymentForm = obj;
    }
    public void initialize(PaymentCasherForm obj) {
        this.objPaymentCasherForm = obj;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Вход")) {
            this.autorization();
        }

        if (e.getActionCommand().equals("Регистрация")) {
        }

        if (e.getActionCommand().equals("showRegistrationFrame")) {
            this.objAuthorization.dispose();
            RegistrationUsers formReg = new RegistrationUsers();
            formReg.setTitle("Регистрация пользователя");
            formReg.pack();
            formReg.setLocationRelativeTo(null);
            formReg.setVisible(true);
        }

        if (e.getActionCommand().equals("showForgotPasswordFrame")) {
            this.objAuthorization.dispose();
            ForgotPassword formForgot = new ForgotPassword();
            formForgot.setTitle("Восстановление пароля");
            formForgot.pack();
            formForgot.setLocationRelativeTo(null);
            formForgot.setVisible(true);
        }

        if (e.getActionCommand().equals("backToAutorizationFromRegistration")) {
            this.objRegistrarionUsers.dispose();
            Authorization dialog = new Authorization();
            dialog.setTitle("Авторизация");
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        if (e.getActionCommand().equals("backToAutorizationFromForgotPassword")) {
            this.objForgotPassword.dispose();
            Authorization dialog = new Authorization();
            dialog.setTitle("Авторизация");
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        if (e.getActionCommand().equals("sendForgotPasswordCode")){
            try {
                if (this.objForgotPassword.getTextEmail().getText().isEmpty()){
                    throw new IllegalArgumentException();
                }
                String email = this.objForgotPassword.getTextEmail().getText();
                String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(email);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException RegexEmailException) {
                this.objForgotPassword.getTextEmail().setBorder(BorderFactory.createLineBorder(Color.red));
                this.objForgotPassword.getTextLabel().setText("Некорректный адрес электронной почты!");
                return;
            }

            this.client.sendMessage("forgotPassword");
            this.client.sendMessage(this.objForgotPassword.getTextEmail().getText());
            String mes = "";

            try {
                mes = this.client.readMessage();
            } catch (IOException ServerException) {
                System.out.println("Error in reading");
            }

            if (mes.equals("This email will not find")) {
                this.objForgotPassword.getTextLabel().setText("Такой почты не найдено!");
            }else{
                this.objForgotPassword.getTextLabel().setText("Введите код, отправленный на почту.");

            }
        }

        if (e.getActionCommand().equals("checkCode")) {
            try {
                if (this.objForgotPassword.getTextCode().getText().isEmpty()) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException CodeException) {
                this.objForgotPassword.getTextCode().setBorder(BorderFactory.createLineBorder(Color.red));
                this.objForgotPassword.getTextLabel().setText("Некорректный код!");
                return;
            }

            this.client.sendMessage("forgotPasswordCode");
            String code = String.valueOf(this.objForgotPassword.getTextCode().getText());
            this.client.sendMessage(code);

            try {
                String mes = this.client.readMessage();

                if (mes.equals("This code is wrong")) {
                    this.objForgotPassword.getTextLabel().setText("Неправильный код.");
                } else if (mes.equals("OK")) {
                    this.objForgotPassword.dispose();
                    ResetPassword reset = new ResetPassword();
                    reset.setTitle("Авторизация");
                    reset.pack();
                    reset.setLocationRelativeTo(null);
                    reset.setVisible(true);
                } else {
                    this.objForgotPassword.getTextLabel().setText("Ошибка.");
                }
            } catch (Exception ex) {
                System.out.println("Error in communication with the server: " + ex.getMessage());
            }
        }

        if (e.getActionCommand().equals("resetPassword")) {
            try {
                if (this.objResetPassword.getTextPassword().getText().isEmpty() &&
                        this.objResetPassword.getTextSecondPassword().getText().isEmpty()){
                    throw new IllegalArgumentException("Некорректный ввод пароля!");
                }
                if (!Objects.equals(this.objResetPassword.getTextPassword().getText(),
                        this.objResetPassword.getTextSecondPassword().getText())){
                    throw new IllegalArgumentException("Введенные пароли не совпадают!");
                }else{
                    this.client.sendMessage("resetPassword");
                    this.client.sendMessage(this.objResetPassword.getTextPassword().getText());
                }
            } catch (IllegalArgumentException ResetPasswordException) {
                this.objResetPassword.getTextPassword().setBorder(BorderFactory.createLineBorder(Color.red));
                this.objResetPassword.getTextSecondPassword().setBorder(BorderFactory.createLineBorder(Color.red));
                this.objResetPassword.getTextLabel().setText(ResetPasswordException.getMessage());
                return;
            }

            try {
                String mes = this.client.readMessage();

                if (mes.equals("Error")) {
                    this.objResetPassword.getTextLabel().setText("Ошибка изменения пароля.");
                } else if (mes.equals("ok")) {
                    JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Пароль изменен.", "Успешно", -1);
                    this.objResetPassword.dispose();
                    Authorization dialog = new Authorization();
                    dialog.setTitle("Авторизация");
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                } else {
                    this.objResetPassword.getTextLabel().setText("Ошибка.");
                }
            } catch (Exception ex) {
                System.out.println("Error in communication with the server: " + ex.getMessage());
            }
        }

        if (e.getActionCommand().equals("registrationUsers")) {
            if (!this.objRegistrarionUsers.getName_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getSecondName_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getPatronymic_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getLogin_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getPassword_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getPassportSeries_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getPassportNumber_tf().getText().isEmpty() &&
                    !this.objRegistrarionUsers.getTextEmail().getText().isEmpty()) {
                User user = new User();
                user.setName(this.objRegistrarionUsers.getName_tf().getText());
                user.setSecondName(this.objRegistrarionUsers.getSecondName_tf().getText());
                user.setPatronymic(this.objRegistrarionUsers.getPatronymic_tf().getText());
                user.setPassportSeries(this.objRegistrarionUsers.getPassportSeries_tf().getText());
                this.objRegistrarionUsers.getPassportNumber_tf().setBorder(BorderFactory.createLineBorder(Color.gray));
                int error = 0;

                try {
                    user.setPassportNumber(Integer.parseInt(this.objRegistrarionUsers.getPassportNumber_tf().getText()));
                } catch (NumberFormatException FormatException) {
                    this.objRegistrarionUsers.getPassportNumber_tf().setBorder(BorderFactory.createLineBorder(Color.red));
                    JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Поле должно быть целым числом", "Ошибка ввода", 0);
                    ++error;
                }

                try {
                    String email = this.objRegistrarionUsers.getTextEmail().getText();
                    String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    Pattern pattern = Pattern.compile(emailRegex);
                    Matcher matcher = pattern.matcher(email);
                    if (!matcher.matches()) {
                        throw new IllegalArgumentException("Некорректный адрес электронной почты");
                    }
                } catch (IllegalArgumentException RegexException) {
                    this.objRegistrarionUsers.getTextEmail().setBorder(BorderFactory.createLineBorder(Color.red));
                    JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Некорректный адрес электронной почты", "Ошибка ввода", 0);
                    ++error;
                }

                user.setLogin(this.objRegistrarionUsers.getLogin_tf().getText());
                user.setPassword(this.objRegistrarionUsers.getPassword_tf().getText());
                user.setEmail(this.objRegistrarionUsers.getTextEmail().getText());

                user.setStatus("user");
                if (error == 0) {
                    this.client.sendMessage("registrationUser");
                    this.client.sendObject(user);
                    String mes = "";

                    try {
                        mes = this.client.readMessage();
                    } catch (IOException ServerException) {
                        System.out.println("Error in reading");
                    }

                    if (mes.equals("This user is already existed")) {
                        JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Такой пользователь уже существует!", "Ошибка регистрации", 0);
                    } else {
                        JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Пользователь успешно зарегистрирован", "Регистрация пользователя", -1);
                        this.objRegistrarionUsers.dispose();
                        Authorization dialog = new Authorization();
                        dialog.setTitle("Авторизация");
                        dialog.pack();
                        dialog.setLocationRelativeTo(null);
                        dialog.setVisible(true);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Заполните поля!", "Ошибка ввода", 0);
            }
        }

        if (e.getActionCommand().equals("editUser")) {
            JTable tableUsers = objForm.getTableUsers();
            int selectedRow = tableUsers.getSelectedRow();
            if (selectedRow != -1) {
                int selectedColumn = tableUsers.getSelectedColumn();
                String[] options;
                if (selectedColumn == 0) {
                    options = new String[]{"Почта"};
                } else if (selectedColumn == 1) {
                    options = new String[]{"Логин"};
                } else if (selectedColumn == 3) {
                    options = new String[]{"Роль"};
                } else {
                    JOptionPane.showMessageDialog(null, "Данный столбик нельзя редактировать.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int result = JOptionPane.showOptionDialog(null, "Выберите тип редактирования:", "Редактирование пользователя",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (result != JOptionPane.CLOSED_OPTION) {
                    String newValue = JOptionPane.showInputDialog(null, "Введите новое значение:", "Редактирование значения",
                            JOptionPane.PLAIN_MESSAGE);
                    if (newValue != null && !newValue.trim().isEmpty()) {
                        this.client.sendMessage("editUser");
                        this.client.sendObject(tableUsers.getValueAt(selectedRow, 0));
                        this.client.sendMessage(options[0]);
                        this.client.sendMessage(newValue);
                        String mes = "";
                        try {
                            mes = this.client.readMessage();
                        } catch (IOException ServerException) {
                            System.out.println("Error in reading");
                        }
                        if (Objects.equals(mes, "OK")){
                            JOptionPane.showMessageDialog(null, "Успешно добавлено!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Ошибка при добавлении.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                        tableUsers.setValueAt(newValue, selectedRow, selectedColumn);;
                    } else {
                        JOptionPane.showMessageDialog(null, "Введите корректное значение.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        if (e.getActionCommand().equals("deleteUser")) {
            JTable tableUsers = objForm.getTableUsers();
            int selectedRow = tableUsers.getSelectedRow();
            if (selectedRow != -1) {
                int choice = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить выбранного пользователя?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    DefaultTableModel tableModel = (DefaultTableModel) tableUsers.getModel();
                    String email = (String) tableModel.getValueAt(selectedRow, 0);
                    if (!msgLogin.equals(tableModel.getValueAt(selectedRow, 1))) {
                        tableModel.removeRow(selectedRow);
                        this.client.sendMessage("deleteUser");
                        this.client.sendObject(email);
                        String mes = "";
                        try {
                            mes = this.client.readMessage();
                        } catch (IOException ServerException) {
                            System.out.println("Error in reading");
                        }
                        if (Objects.equals(mes, "OK")) {
                            JOptionPane.showMessageDialog(null, "Успешно удалено!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Ошибка при удалении.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Вы не можете удалить сами себя.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Выберите пользователя для удаления.");
            }
        }

        if (e.getActionCommand().equals("doReporting")) {

        }

        if (e.getActionCommand().equals("createPayment")) {
            String[] options = {"Категория", "Подкатегория"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Выберите тип добавления:",
                    "Добавление элемента",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            JTree treeEditPayments = objForm.getTreeEditPayments();
            if (choice == 0) {
                String categoryName = JOptionPane.showInputDialog(null, "Введите название категории:", "Добавление категории", JOptionPane.PLAIN_MESSAGE);
                if (categoryName != null && !categoryName.trim().isEmpty()) {
                    this.client.sendMessage("createPayment");
                    this.client.sendObject(categoryName);
                    this.client.sendObject("Категория");
                    String mes = "";
                    try {
                        mes = this.client.readMessage();
                    } catch (IOException ServerException) {
                        System.out.println("Error in reading");
                    }
                    if (Objects.equals(mes, "OK")){
                        JOptionPane.showMessageDialog(null, "Успешно добавлено!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ошибка при добавлении.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                    DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(categoryName);
                    ((DefaultTreeModel) treeEditPayments.getModel()).insertNodeInto(categoryNode, (MutableTreeNode) treeEditPayments.getModel().getRoot(), treeEditPayments.getModel().getChildCount(treeEditPayments.getModel().getRoot()));
                } else {
                    JOptionPane.showMessageDialog(null, "Название категории не может быть пустым. Добавление отменено.");
                }
            } else if (choice == 1) {
                TreePath selectedPath = treeEditPayments.getSelectionPath();

                if (selectedPath != null) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    String selectedCategory = selectedNode.getUserObject().toString();
                    String subcategoryName = JOptionPane.showInputDialog(null, "Введите название подкатегории:", "Добавление подкатегории", JOptionPane.PLAIN_MESSAGE);
                    if (subcategoryName != null && !subcategoryName.trim().isEmpty()) {
                        this.client.sendMessage("createPayment");
                        this.client.sendObject(subcategoryName);
                        this.client.sendObject("Подкатегория");
                        this.client.sendObject(selectedCategory);
                        String mes = "";
                        try {
                            mes = this.client.readMessage();
                        } catch (IOException ServerException) {
                            System.out.println("Error in reading");
                        }
                        if (Objects.equals(mes, "OK")){
                            JOptionPane.showMessageDialog(null, "Успешно добавлено!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Ошибка при добавлении.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                        DefaultMutableTreeNode subcategoryNode = new DefaultMutableTreeNode(subcategoryName);
                        ((DefaultTreeModel) treeEditPayments.getModel()).insertNodeInto(subcategoryNode, selectedNode, selectedNode.getChildCount());
                    } else {
                        JOptionPane.showMessageDialog(null, "Название подкатегории не может быть пустым. Добавление отменено.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Выберите категорию для добавления подкатегории.");
                }
            }
        }

        if (e.getActionCommand().equals("editPayment")) {
            JTree treePayments = objForm.getTreeEditPayments();
            TreePath selectedPath = treePayments.getSelectionPath();

            if (selectedPath != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

                if (!selectedNode.isRoot()) {
                    Object selectedObject = selectedNode.getUserObject();
                    String currentName = selectedObject.toString();
                    String newName = JOptionPane.showInputDialog(null, "Введите новое имя:", "Редактирование элемента", JOptionPane.PLAIN_MESSAGE);
                    if (newName != null && !newName.trim().isEmpty()) {
                        selectedNode.setUserObject(newName);
                        DefaultTreeModel treeModel = (DefaultTreeModel) treePayments.getModel();
                        treeModel.nodeChanged(selectedNode);
                        this.client.sendMessage("editPayment");
                        this.client.sendObject(currentName);
                        this.client.sendObject(newName);
                        String mes = "";
                        try {
                            mes = this.client.readMessage();
                        } catch (IOException ServerException) {
                            System.out.println("Error in reading");
                        }
                        if (Objects.equals(mes, "OK")){
                            JOptionPane.showMessageDialog(null, "Успешно изменено!");
                        }else{
                            JOptionPane.showMessageDialog(null, "Ошибка при изменении.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Имя не может быть пустым. Редактирование отменено.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Выберите элемент для редактирования, не являющийся корневым узлом.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Выберите элемент для редактирования.");
            }
        }

        if (e.getActionCommand().equals("deletePayment")) {
            JTree treePayments = objForm.getTreeEditPayments();
            TreePath selectedPath = treePayments.getSelectionPath();
            if (selectedPath != null) {
                int choice = JOptionPane.showConfirmDialog(null, "Вы уверены, что хотите удалить выбранный элемент?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    if (!selectedNode.isRoot()) {
                        DefaultTreeModel treeModel = (DefaultTreeModel) treePayments.getModel();
                        TreePath parentPath = selectedPath.getParentPath();
                        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
                        if (parentNode != null) {
                            parentNode.remove(selectedNode);
                            Object selectedObject = selectedNode.getUserObject();
                            this.client.sendMessage("deletePayment");
                            this.client.sendObject(selectedObject);
                            String mes = "";
                            try {
                                mes = this.client.readMessage();
                            } catch (IOException ServerException) {
                                System.out.println("Error in reading");
                            }
                            if (Objects.equals(mes, "OK")){
                                JOptionPane.showMessageDialog(null, "Успешно удалено!");
                            }else{
                                JOptionPane.showMessageDialog(null, "Ошибка при удалении.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                            treeModel.reload();
                            treePayments.expandPath(parentPath);
                        } else {
                            JOptionPane.showMessageDialog(null, "Нельзя удалить корневой узел.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите элемент для удаления, не являющийся корневым узлом.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Выберите элемент для удаления.");
            }
        }

        if (e.getActionCommand().equals("casherPayment")) {
            JTree treePayments = objFormCasher.getPaymentTree();
            TreePath selectedPath = treePayments.getSelectionPath();
            if (selectedPath != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                if (selectedNode.isLeaf()) {
                    this.objFormCasher.dispose();
                    PaymentCasherForm formPayment = new PaymentCasherForm();
                    Object selectedObject = selectedNode.getUserObject();
                    formPayment.getPaymentType().setText((String) selectedObject);
                    formPayment.setTitle("Оплата");
                    formPayment.pack();
                    formPayment.setLocationRelativeTo(null);
                    formPayment.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Вы не можете оплатить раздел.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getActionCommand().equals("userPayment")) {
            JTree treePayments = objUsersForm.getTreePayments();
            TreePath selectedPath = treePayments.getSelectionPath();
            if (selectedPath != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                if (selectedNode.isLeaf()) {
                    PaymentForm formPayment = new PaymentForm();
                    formPayment.getCardBox().addItem(cardNumber);
                    this.objUsersForm.dispose();
                    Object selectedObject = selectedNode.getUserObject();
                    formPayment.getPaymentType().setText((String) selectedObject);
                    formPayment.setTitle("Оплата");
                    formPayment.pack();
                    formPayment.setLocationRelativeTo(null);
                    formPayment.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Вы не можете оплатить раздел.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (e.getActionCommand().equals("backToClientForm")) {
            if (role.equals("casher")){
                this.objPaymentForm.setVisible(false);
                FormCasher formCasher = new FormCasher();
                formCasher.setTitle("Меню кассира");
                formCasher.pack();
                formCasher.setLocationRelativeTo(null);
                formCasher.setVisible(true);
                JMenuBar menu = new JMenuBar();
                JMenu item = new JMenu("Выход");
                menu.add(item);
                formCasher.setJMenuBar(menu);
                TreeUtil.fillTree(formCasher.getPaymentTree(), category);
            }else if(role.equals("user")){
                this.objPaymentForm.setVisible(false);
                UsersForm usersForm = new UsersForm();
                usersForm.getAccountName().setText(infUser.getName());
                usersForm.getAccountSurname().setText(infUser.getSecondName());
                usersForm.getAccountPatronymic().setText(infUser.getPatronymic());
                usersForm.getAccountNumberCard().setText(infUser.getPatronymic());
                usersForm.setTitle("Меню пользователя");
                usersForm.pack();
                usersForm.setLocationRelativeTo(null);
                usersForm.setVisible(true);
                TreeUtil.fillTree(usersForm.getTreePayments(), category);
                usersForm.getAccountNumberCard().setText(cardNumber);
            }
        }

        if (e.getActionCommand().equals("doPayment")) {
            String paymentType = objPaymentForm.getPaymentType().getText();
            double amount;

            try {
                amount = Double.parseDouble(objPaymentForm.getPaymentAmount().getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Некорректный формат суммы", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            Date date = new Date(currentTimeMillis);
            String cardNumber = objPaymentForm.getCardBox().getSelectedItem().toString();

            Payment payment = new Payment();
            payment.setPaymentType(paymentType);
            payment.setAmount(amount);
            payment.setDate(date);
            payment.setCardNumber(cardNumber);

            this.client.sendObject("doPayment");
            this.client.sendObject(payment);

            objPaymentForm.getPaymentType().setText("");
            objPaymentForm.getPaymentAmount().setText("");
            objPaymentForm.getCardBox().setSelectedItem(null);

            JOptionPane.showMessageDialog(null, "Оплата выполнена успешно");
        }

        if (e.getActionCommand().equals("exitUser")) {
            client.sendMessage("exit");
            try {
                if (client.readMessage().equals("OK")) {
                    client.close();
                    objUsersForm.dispose();
                    System.exit(0);
                }
            }
            catch(IOException ex){
                System.out.println("Error in reading");
            }
        }
    }

    public void autorization() {
        try {
            msgLogin = this.objAuthorization.getTextLogin().getText();
            String msgPassword = this.objAuthorization.getPasswordField1().getText();
            if (msgLogin.equals("") || msgPassword.equals("")) {
                JOptionPane.showMessageDialog(this.objAuthorization, "Заполните все поля!", "Ошибка ввода", 0);
            }
            User user = new User();
            user.setLogin(msgLogin);
            user.setPassword(msgPassword);
            this.client.sendMessage("enter");
            this.client.sendObject(user);
            switch (this.client.readMessage()) {
                case "error":
                    JOptionPane.showMessageDialog(this.objAuthorization, "Такой пользователь не зарегистрирован", "Ошибка авторизации", 0);
                    break;
                case "errorInput":
                    JOptionPane.showMessageDialog(this.objAuthorization, "Проверьте введенные данные", "Ошибка ввода", 0);
                    break;
                case "ok":
                    String status = this.client.readMessage();
                    role = status;
                    this.client.sendMessage("getPayments");
                    Object receivedObject = this.client.readObject();
                    category = (Map<Category, List<Subcategory>>) receivedObject;
                    if (status.equals("admin")) {
                        this.objAuthorization.setVisible(false);
                        Form form = new Form();
                        form.setTitle("Меню администратора");
                        form.pack();
                        form.setLocationRelativeTo(null);
                        form.setVisible(true);
                        JMenuBar menu = new JMenuBar();
                        JMenu item = new JMenu("Выход");
                        item.setActionCommand("exit");
                        item.addActionListener(getInstance());
                        menu.add(item);
                        form.setJMenuBar(menu);
                        TreeUtil.fillTree(form.getTreePayments(), category);
                        TreeUtil.fillTree(form.getTreeEditPayments(), category);
                        this.client.sendMessage("getUsers");
                        Object receivedUsers = this.client.readObject();
                        ArrayList<User> users = (ArrayList<User>) receivedUsers;
                        TableUtil.populateUserTable(form.getTableUsers(), users);
                    }
                    if (status.equals("casher")) {
                        this.objAuthorization.setVisible(false);
                        FormCasher formCasher = new FormCasher();
                        formCasher.setTitle("Меню кассира");
                        formCasher.pack();
                        formCasher.setLocationRelativeTo(null);
                        formCasher.setVisible(true);
                        JMenuBar menu = new JMenuBar();
                        JMenu item = new JMenu("Выход");
                        menu.add(item);
                        formCasher.setJMenuBar(menu);
                        TreeUtil.fillTree(formCasher.getPaymentTree(), category);
                    }

                    if (status.equals("user")) {
                        this.objAuthorization.setVisible(false);
                        UsersForm usersForm = new UsersForm();
                        this.client.sendMessage("getInfAboutUser");
                        this.client.sendObject(user);
                        infUser = (User)this.client.readObject();
                        cardNumber = (String)this.client.readObject();
                        usersForm.getAccountName().setText(infUser.getName());
                        usersForm.getAccountSurname().setText(infUser.getSecondName());
                        usersForm.getAccountPatronymic().setText(infUser.getPatronymic());
                        usersForm.getAccountNumberCard().setText(infUser.getPatronymic());
                        infUser.getEmail();
                        usersForm.setTitle("Меню пользователя");
                        usersForm.pack();
                        usersForm.setLocationRelativeTo(null);
                        usersForm.setVisible(true);
                        TreeUtil.fillTree(usersForm.getTreePayments(), category);
                        String block1 = cardNumber.substring(0, 4);
                        String block2 = cardNumber.substring(4, 8);
                        String block3 = cardNumber.substring(8, 12);
                        String block4 = cardNumber.substring(12);
                        cardNumber = String.format("%s %s %s %s", block1, block2, block3, block4);
                        usersForm.getAccountNumberCard().setText(cardNumber);
                        this.client.sendMessage("getPaymentsUser");
                        this.client.sendMessage(infUser.getEmail());
                        Object receivedPayments = this.client.readObject();
                        ArrayList<Payment> payments = (ArrayList<Payment>) receivedPayments;
                        TableUtil.populatePaymentTable(usersForm.getPaymentTable(), payments);
                    }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}