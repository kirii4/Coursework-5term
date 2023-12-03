package ermakov.onlinebanking.view.Form;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;

public class Form extends JFrame{
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JPanel panelChange;
    private JScrollPane scrollPaneReporting;
    private JButton buttonEditUsers;
    private JButton buttonDeleteUsers;
    private JButton buttonReporting;
    private JTree treePayments;
    private JTree treeEditPayments;
    private JTable tableUsers;
    private JTable tablePassenger;
    private JButton buttonDeletePayments;
    private JComboBox payments_cb;
    private JButton buttonCreatePayments;
    private JButton buttonConfigurationPayments;

    public Form(){
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        buttonDeleteUsers.setActionCommand("deleteUser");
        buttonDeleteUsers.addActionListener(Controller.getInstance());
        buttonEditUsers.setActionCommand("editUser");
        buttonEditUsers.addActionListener(Controller.getInstance());
        buttonReporting.setActionCommand("doReporting");
        buttonReporting.addActionListener(Controller.getInstance());
        buttonCreatePayments.setActionCommand("createPayment");
        buttonCreatePayments.addActionListener(Controller.getInstance());
        buttonDeletePayments.setActionCommand("deletePayment");
        buttonDeletePayments.addActionListener(Controller.getInstance());
        buttonConfigurationPayments.setActionCommand("editPayment");
        buttonConfigurationPayments.addActionListener(Controller.getInstance());
    }

    public JTree getTreePayments() {
        return treePayments;
    }

    public JTree getTreeEditPayments() {
        return treeEditPayments;
    }

    public JTable getTableUsers() {
        return tableUsers;
    }

}
