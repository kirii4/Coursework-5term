package ermakov.onlinebanking.controller;

import ermakov.onlinebanking.model.User;
import ermakov.onlinebanking.model.client.Client;
import ermakov.onlinebanking.view.Authorization.EnterDialog;
import ermakov.onlinebanking.view.ForgotPassword.ForgotPassword;
import ermakov.onlinebanking.view.Form.Form;
import ermakov.onlinebanking.view.FormCasher.FormCasher;
import ermakov.onlinebanking.view.Registration.RegistrationUsers;
import ermakov.onlinebanking.view.UsersForm.UsersForm;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {
    private static Controller instance;
    private EnterDialog objEnterDialog;
    private RegistrationUsers objRegistrarionUsers;
    private UsersForm objUsersForm;
    private Form objForm;
    private FormCasher objFormCasher;
    private ForgotPassword objForgotPassword;
    private final Client client = new Client("127.0.0.2", "5000");

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    public void initialize(EnterDialog obj) {
        this.objEnterDialog = obj;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Вход")) {
            this.autorization();
        }

        if (e.getActionCommand().equals("Регистрация")) {
        }

        if (e.getActionCommand().equals("showRegistrationFrame")) {
            this.objEnterDialog.dispose();
            RegistrationUsers formReg = new RegistrationUsers();
            formReg.setTitle("Регистрация пользователя");
            formReg.pack();
            formReg.setLocationRelativeTo(null);
            formReg.setVisible(true);
        }

        if (e.getActionCommand().equals("backToAutorization")) {
            this.objRegistrarionUsers.dispose();
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
                } catch (NumberFormatException var9) {
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
                } catch (IllegalArgumentException var10) {
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
                    } catch (IOException var8) {
                        System.out.println("Error in reading");
                    }

                    if (mes.equals("This user is already existed")) {
                        JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Такой пользователь уже существует!", "Ошибка регистрации", 0);
                    } else {
                        JOptionPane.showMessageDialog(this.objRegistrarionUsers, "Пользователь успешно зарегистрирован", "Регистрация пользователя", -1);
                        this.objRegistrarionUsers.dispose();
                        EnterDialog dialog = new EnterDialog();
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

    }

    public void autorization() {
        try {
            String msgLogin = this.objEnterDialog.getTextLogin().getText();
            String msgPassword = this.objEnterDialog.getPasswordField1().getText();
            if (msgLogin.equals("") || msgPassword.equals("")) {
                JOptionPane.showMessageDialog(this.objEnterDialog, "Заполните все поля!", "Ошибка ввода", 0);
            }

            User user = new User();
            user.setLogin(msgLogin);
            user.setPassword(msgPassword);
            this.client.sendMessage("enter");
            this.client.sendObject(user);
            switch (this.client.readMessage()) {
                case "error":
                    JOptionPane.showMessageDialog(this.objEnterDialog, "Такой пользователь не зарегистрирован", "Ошибка авторизации", 0);
                    break;
                case "errorInput":
                    JOptionPane.showMessageDialog(this.objEnterDialog, "Проверьте введенные данные", "Ошибка ввода", 0);
                    break;
                case "ok":
                    String status = this.client.readMessage();
                    if (status.equals("admin")) {
                        this.objEnterDialog.setVisible(false);
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
                    }

                    if (status.equals("casher")) {
                        this.objEnterDialog.setVisible(false);
                        FormCasher formCasher = new FormCasher();
                        //this.client.sendMessage("getNumberOfTicket");
                        //int numberOfTicket = (Integer)this.client.readObject();
                        int numberOfTicket = 0;
                        formCasher.getNumberTicket_tf().setText("" + numberOfTicket);
                        formCasher.getNumberTicket_tf().setEnabled(false);
                        formCasher.setTitle("Меню кассира");
                        formCasher.pack();
                        formCasher.setLocationRelativeTo(null);
                        formCasher.setVisible(true);
                        JMenuBar menu = new JMenuBar();
                        JMenu item = new JMenu("Выход");
                        menu.add(item);
                        formCasher.setJMenuBar(menu);
                    }

                    if (status.equals("user")) {
                        this.objEnterDialog.setVisible(false);
                        UsersForm usersForm = new UsersForm();
                        //this.client.sendMessage("getInfAboutPassenger");
                        //this.client.sendObject(user);
                        //User infUser = (User)this.client.readObject();
                        User infUser = new User();
                        usersForm.getName_tf().setText(infUser.getName());
                        usersForm.getSecondName_tf().setText(infUser.getSecondName());
                        usersForm.getPatronymic_tf().setText(infUser.getPatronymic());
                        usersForm.getSeries_tf().setText(infUser.getPassportSeries());
                        usersForm.getNumber_tf().setText("" + infUser.getPassportNumber());
                        usersForm.setTitle("Меню пользователя");
                        usersForm.pack();
                        usersForm.setLocationRelativeTo(null);
                        usersForm.setVisible(true);
                    }
            }
        } catch (IOException var12) {
            var12.printStackTrace();
        }

    }
}