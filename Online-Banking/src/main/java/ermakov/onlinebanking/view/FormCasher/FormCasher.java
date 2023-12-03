package ermakov.onlinebanking.view.FormCasher;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;

public class FormCasher extends JFrame{
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JTextField numberTicket_tf;
    private JScrollPane scrollPaneReturnTicket;
    private JTextField numTic_tf;
    private JButton buttonSearch;
    private JButton buttonReturnTicket;

    private JTable table;
    private JTree paymentTree;
    private JButton buttonPayment;

    public FormCasher(){
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        Controller.getInstance().initialize(this);
        buttonPayment.setActionCommand("casherPayment");
        buttonPayment.addActionListener(Controller.getInstance());
    }

    public JTree getPaymentTree(){
        return paymentTree;
    }
}
