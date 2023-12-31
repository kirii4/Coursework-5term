package ermakov.onlinebanking.view.UsersForm;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;

public class UsersForm extends JFrame{
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JButton buttonShow;
    private JButton buttonPayment;
    private JButton buttonExit;
    private JTextField accountSurname;
    private JTextField accountName;
    private JScrollPane scrollPaneTicket;
    private JTextField accountPatronymic;
    private JTextField accountNumberCard;
    private JTree treePayments;
    private JTable paymentTable;
    private JScrollPane Scroll;

    public UsersForm() {
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        buttonPayment.setActionCommand("userPayment");
        buttonPayment.addActionListener(Controller.getInstance());
        buttonExit.setActionCommand("exitUser");
        buttonExit.addActionListener(Controller.getInstance());
    }
    public JTree getTreePayments() {
        return treePayments;
    }

    public JTextField getAccountSurname() {
        return accountSurname;
    }

    public JTextField getAccountName() {
        return accountName;
    }

    public JTextField getAccountPatronymic() {
        return accountPatronymic;
    }

    public JTextField getAccountNumberCard() {
        return accountNumberCard;
    }

    public JTable getPaymentTable(){ return paymentTable; }

}
