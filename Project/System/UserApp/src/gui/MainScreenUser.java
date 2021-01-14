package gui;

import model.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainScreenUser extends JFrame {

    // Just Panels
    private JPanel backPanel;
    private JPanel mainPanel;
    private JPanel mainPanelCard;
    private JPanel buttonPanel;
    private JPanel rightPanel;
    private JPanel personalInfoPanel;
    private JPanel companyInfoPanel;
    private JPanel loginPanel;
    private JPanel workTimePanel;
    private JPanel homePanel;

    // For Work Time stuff
    private JComboBox sortWorkTimeBox;
    private JList workTimeList;
    private JButton reportButton;
    private JButton workTimeButton;

    // Need to set be For Personal info
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JButton personalInfoButton;

    // Need to be set for Company Info
    private JLabel companyNameLabel;
    private JLabel companyAddressLabel;
    private JLabel companyPhoneLabel;
    private JLabel companyEmailLabel;
    private JButton companyInfoButton;
    private JButton logoutButton;

    private final String[] sortList = {"Day","Monthly"};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    public MainScreenUser(Worker worker) {
        super("User App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        //Add Menu Bar
        JMenu help = new JMenu("help");
        help.add(contactInfo);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        contactInfo.addActionListener(e -> contactInfoAction());
        personalInfoButton.addActionListener(e -> personalInfoButtonAction());
        workTimeButton.addActionListener(e -> workTimeButtonAction());
        companyInfoButton.addActionListener(e -> companyInfoButtonAction());
        logoutButton.addActionListener(e -> logoutButtonAction());
        reportButton.addActionListener(e -> reportButtonAction());

    }

    // Show and sets Personal Info
    private void personalInfoButtonAction() {
        //Setting the Labels


        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"personalInfoPanel");
    }

    //Show Work Time PAnel
    private void workTimeButtonAction() {

        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"workTimePanel");
    }

    //Show and sets Company Info
    private void companyInfoButtonAction() {
        //Setting the Labels


        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"companyInfoPanel");
    }

    // go back to login
    private void logoutButtonAction() {
        this.dispose();

        new LoginScreenUser();
    }

    //Prints a report
    private void reportButtonAction() {
    }

    //Show Contact Info
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortWorkTimeBox = new JComboBox(sortList);
    }
}
