package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame{
    private JPanel backPanel;
    private JButton companyInfoButton;
    private JButton workTimeButton;
    private JButton personalInfoButton;
    private JPanel mainPanel;
    private JPanel personalInfoPanel;
    private JPanel workTimePanel;
    private JPanel companyInfoPanel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel addressLabel;
    private JLabel workPlaceLabel;
    private JLabel sectorLabel;
    private JLabel userNameLabel;
    private JLabel dateOfBirthLabel;
    private JComboBox sortBox;
    private JButton reportButton;
    private JList list1;
    private JLabel companyNameLabel;
    private JLabel companyAddressLabel;
    private JLabel companyPhoneLabel;
    private JLabel companyEmailLabel;
    private String[] sortList = {"Day","Monthly"};

    public MainScreen() {
        super("User App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();

        personalInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel,"personalInfoPanel");

            }
        });
        workTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel,"workTimePanel");

            }
        });
        companyInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout card = (CardLayout)(mainPanel.getLayout());
                card.show(mainPanel,"companyInfoPanel");

            }
        });
    }

    private void createUIComponents() {
        sortBox = new JComboBox(sortList);

    }
}
