package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainScreenUser extends JFrame {
    //Just PAnels
    private JPanel backPanel;
    private JPanel mainPanel;
    private JPanel mainPanelCard;
    private JPanel buttonPanel;
    private JPanel rightPanel;
    private JPanel personalInfoPanel;
    private JPanel companyInfoPanel;
    private JPanel loginPanelCard;
    private JPanel leftPanel;
    private JPanel loginMainCard;
    private JPanel newPasswordCard;
    private JPanel loginPanel;
    private JPanel workTimePanel;
    //For Work Time stuff
    private JComboBox sortWorkTimeBox;
    private JList workTimeList;
    private JButton reportButton;
    private JButton workTimeButton;
    //Need to set be For Personal info
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JButton personalInfoButton;
    //Need to be set for Company Info
    private JLabel companyNameLabel;
    private JLabel companyAddressLabel;
    private JLabel companyPhoneLabel;
    private JLabel companyEmailLabel;
    private JButton companyInfoButton;
    //For creating a new password
    private JPasswordField confirmNewPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField oldPasswordField;
    private JButton okConfirmPassButton;
    //For login
    private JTextField userNameTextField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JLabel humanIcon;
    private JButton logoutButton;

    private String[] sortList = {"Day","Monthly"};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");



    public MainScreenUser() {
        super("User App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();
        this.setVisible(true);


        //Add Menu Bar
        JMenu help = new JMenu("help");
        help.add(contactInfo);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        contactInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                contactInfoAction();
            }
        });

        personalInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                personalInfoButtonAction();
            }
        });
        workTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workTimeButtonAction();
            }
        });
        companyInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                companyInfoButtonAction();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutButtonAction();
            }
        });
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportButtonAction();
            }
        });
        okConfirmPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okConfirmPassButtonAction();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonAction();
            }
        });
    }

    private void loginButtonAction() {
        //Chek User Details

        //First login or need new password
        if (true) {
            CardLayout card = (CardLayout) (loginPanel.getLayout());
            card.show(loginPanel, "newPasswordCard");
        } else {
            CardLayout card = (CardLayout) (backPanel.getLayout());
            card.show(backPanel, "mainPanelCard");
        }
    }

    private void okConfirmPassButtonAction() {
        boolean good = Arrays.equals(newPasswordField.getPassword(), confirmNewPasswordField.getPassword());
        if (good) {
            CardLayout card = (CardLayout) (backPanel.getLayout());
            card.show(backPanel, "mainPanelCard");
        }
    }

    private void personalInfoButtonAction() {
        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"personalInfoPanel");

        //Setting the Labels
    }

    private void workTimeButtonAction() {
        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"workTimePanel");

        //
    }

    private void companyInfoButtonAction() {
        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"companyInfoPanel");

        //Setting the Labels
    }

    private void logoutButtonAction() {

        CardLayout card = (CardLayout) (backPanel.getLayout());
        card.show(backPanel, "loginPanelCard");
        card = (CardLayout) (loginPanel.getLayout());
        card.show(loginPanel, "loginMainCard");
    }

    private void reportButtonAction() {
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortWorkTimeBox = new JComboBox(sortList);
    }
}
