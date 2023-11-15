package ermakov.onlinebanking.view.ResetPassword;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ResetPassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonReset;
    private JButton buttonCancel;
    private JTextField textPassword;
    private JLabel textLabel;
    private JButton buttonCheckCode;
    private JTextField textSecondPassword;

    public JTextField getTextPassword(){
        return textPassword;
    }

    public JTextField getTextSecondPassword(){
        return textSecondPassword;
    }

    public JLabel getTextLabel(){
        return textLabel;
    }

    public ResetPassword() {
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setModal(true);
        getRootPane().setDefaultButton(buttonReset);

        setMinimumSize(new Dimension(450, 200));
        setMaximumSize(new Dimension(450, 400));

        Controller.getInstance().initialize(this);
        buttonReset.setActionCommand("resetPassword");
        buttonReset.addActionListener(Controller.getInstance());
        buttonCancel.setActionCommand("backToAutorizationFromForgotPassword");
        buttonCancel.addActionListener(Controller.getInstance());
    }
    public void exit(){
        setDefaultCloseOperation(ResetPassword.HIDE_ON_CLOSE);
    }

}
