package ermakov.onlinebanking.view.Authorization;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;

public class Authorization extends JDialog {
    private JPanel contentPane;
    private JButton buttonEnter;
    private JButton buttonRegistration;
    private JTextField textLogin;
    private JPasswordField passwordField1;
    private JButton buttonForgotPassword;

    public JTextField getTextLogin(){
        return textLogin;
    }
    public JPasswordField getPasswordField1(){
        return passwordField1;
    }

    public Authorization() {
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setModal(true);
        getRootPane().setDefaultButton(buttonEnter);

        Controller.getInstance().initialize(this);
        buttonEnter.addActionListener(Controller.getInstance());
        buttonRegistration.setActionCommand("showRegistrationFrame");
        buttonRegistration.addActionListener(Controller.getInstance());
        buttonForgotPassword.setActionCommand("showForgotPasswordFrame");
        buttonForgotPassword.addActionListener(Controller.getInstance());
    }
    public void exit(){
        setDefaultCloseOperation(Authorization.HIDE_ON_CLOSE);
    }


}
