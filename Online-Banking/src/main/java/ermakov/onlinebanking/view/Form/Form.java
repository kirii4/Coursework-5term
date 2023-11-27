package ermakov.onlinebanking.view.Form;

import ermakov.onlinebanking.controller.Controller;

import javax.swing.*;

public class Form extends JFrame{
    private Form dialog;
    private JPanel contentPane;
    private JTabbedPane tabbedPane1;
    private JButton buttonShowSchedule;
    private JButton buttonEdit;
    private JButton buttonCreate;
    private JPanel panelChange;
    private JButton buttonDelete;
    private JScrollPane scrollPanePassenger;
    private JButton buttonAdd;
    private JButton buttonDeletePlane;
    private JTable tableSchedule;
    private JTable tablePassenger;
    private JTable tablePlane;
    private JPanel graficPane;
    private JComboBox from_cb;
    private JComboBox to_cb;
    private JComboBox boardNumber_cb;
    private JComboBox payments_cb;
    private JButton buttonDoText;
    private JTree treePayments;

    private JTree treeEditPayments;

    private JTable tableUsers;

    public Form(){
        setContentPane(contentPane);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

        Controller.getInstance().initialize(this);
        buttonCreate.setActionCommand("createSchedule");
        buttonCreate.addActionListener(Controller.getInstance());
        buttonShowSchedule.setActionCommand("showSchedule");
        buttonShowSchedule.addActionListener(Controller.getInstance());
        buttonDelete.setActionCommand("deleteSchedule");
        buttonDelete.addActionListener(Controller.getInstance());
        buttonEdit.setActionCommand("editSchedule");
        buttonEdit.addActionListener(Controller.getInstance());
        buttonDoText.setActionCommand("doText");
        buttonDoText.addActionListener(Controller.getInstance());
    }

    public JTable getTableSchedule() {
        return tableSchedule;
    }

    public JTable getTablePassenger() {
        return tablePassenger;
    }

    public JTable getTablePlane() {
        return tablePlane;
    }

    public JPanel getGraficPane() {
        return graficPane;
    }

    public JComboBox getTo_cb() {
        return to_cb;
    }

    public JComboBox getFrom_cb() {
        return from_cb;
    }

    public JComboBox getBoardNumber_cb() {
        return boardNumber_cb;
    }

    public JComboBox getFlight_cb() {
        return payments_cb;
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
