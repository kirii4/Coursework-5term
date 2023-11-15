package ermakov.onlinebanking.view.ForgotPassword;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ForgotPassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonSend;
    private JButton buttonCancel;
    private JTextField textEmail;
    private JLabel textLabel;
    private JButton buttonCheckCode;
    private JTextField textCode;

    public JTextField getTextEmail(){
        return textEmail;
    }

    public JLabel getTextLabel(){
        return textLabel;
    }

    public JTextField getTextCode(){
        return textCode;
    }

    public ForgotPassword() {
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setModal(true);
        getRootPane().setDefaultButton(buttonSend);

        setMinimumSize(new Dimension(450, 200));
        setMaximumSize(new Dimension(450, 400));

        Controller.getInstance().initialize(this);
        buttonSend.setActionCommand("sendForgotPasswordCode");
        buttonSend.addActionListener(Controller.getInstance());
        buttonCheckCode.setActionCommand("checkCode");
        buttonCheckCode.addActionListener(Controller.getInstance());
        buttonCancel.setActionCommand("backToAutorizationFromForgotPassword");
        buttonCancel.addActionListener(Controller.getInstance());
    }
    public void exit(){
        setDefaultCloseOperation(ForgotPassword.HIDE_ON_CLOSE);
    }

}
