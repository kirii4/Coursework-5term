package ermakov.onlinebanking.view.ForgotPassword;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;

public class ForgotPassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonSend;
    private JButton buttonCancel;
    private JTextField textEmail;
    private JPasswordField passwordField1;
    private JLabel textCode;
    private JLabel EmailLabel;

    public JTextField getTextLogin(){
        return textEmail;
    }
    public JPasswordField getPasswordField1(){
        return passwordField1;
    }

    public ForgotPassword() {
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setModal(true);
        getRootPane().setDefaultButton(buttonSend);

        Controller.getInstance().initialize(this);
        buttonSend.addActionListener(Controller.getInstance());
        buttonCancel.setActionCommand("showForgotPassword");
        buttonCancel.addActionListener(Controller.getInstance());
    }
    public void exit(){
        setDefaultCloseOperation(ForgotPassword.HIDE_ON_CLOSE);
    }


}
