package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenUser extends JFrame {
    private JButton companyInfoButton;
    private JButton workTimeButton;
    private JButton personalInfoButton;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JPanel personalInfoPanel;
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JPanel workTimePanel;
    private JList list1;
    private JComboBox sortBox;
    private JButton reportButton;
    private JPanel companyInfoPanel;
    private JLabel companyNameLabel;
    private JLabel companyAddressLabel;
    private JLabel companyPhoneLabel;
    private JLabel companyEmailLabel;
    private JPanel rightPanel;
    private JPanel buttonPanel;
    private JPanel backPanel;
    private String[] sortList = {"Day","Monthly"};


    public MainScreenUser() {
        super("User App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();


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
    }

    private void personalInfoButtonAction() {
        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"personalInfoPanel");
    }

    private void workTimeButtonAction() {
        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"workTimePanel");
    }

    private void companyInfoButtonAction() {
        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"companyInfoPanel");
    }

    private void logoutButtonAction() {
    }

    private void reportButtonAction() {
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortBox = new JComboBox(sortList);
    }
}
