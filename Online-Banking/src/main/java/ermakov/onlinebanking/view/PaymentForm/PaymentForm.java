package ermakov.onlinebanking.view.PaymentForm;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class PaymentForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonPayment;
    private JButton buttonCancel;
    private JTextField textPassword;
    private JLabel textLabel;
    private JComboBox cardBox;
    private JLabel paymentType;
    private JTextField paymentAmount;

    public JLabel getTextLabel(){
        return textLabel;
    }

    public PaymentForm() {
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        setModal(true);
        getRootPane().setDefaultButton(buttonPayment);

        setMinimumSize(new Dimension(450, 200));
        setMaximumSize(new Dimension(450, 400));

        Controller.getInstance().initialize(this);
        buttonPayment.setActionCommand("doPayment");
        buttonPayment.addActionListener(Controller.getInstance());
        buttonCancel.setActionCommand("backToClientForm");
        buttonCancel.addActionListener(Controller.getInstance());
    }

    public JTextField getPaymentAmount(){
        return paymentAmount;
    }

    public JLabel getPaymentType(){
        return paymentType;
    }

    public void exit(){
        setDefaultCloseOperation(PaymentForm.HIDE_ON_CLOSE);
    }


    public JComboBox getCardBox() {
        return cardBox;
    }
}
