package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainScreenAdmin extends JFrame{
    private JPanel backPanel;
    private JPanel mainPanelCard;
    private JPanel buttonPanel;
    private JPanel rightPanel;
    private JPanel activatePanel;
    private JPanel mainPanel;
    private JPanel deleteUserPanel;
    private JPanel loginPanelCard;
    private JPanel leftPanel;
    private JPanel loginPanel;
    private JPanel newPasswordCard;
    private JPanel addHrPanel;
    private JPanel loginMainCard;
    //For activating the App
    private JButton activateButtonPanel;
    private JTextField keyTextField;
    private JButton okLicenceButton;
    private JButton logoutButton;
    //For deleting
    private JButton showPasswordButton;
    private JButton searchButton;
    private JTable userTable;
    private JTextField searchTextField;
    private JButton deleteButton;
    private JButton deleteUserButtonPanel;
    //for Add a New User
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField addressTextField;
    private JComboBox workPlaceBox;
    private JComboBox sectorBox;
    private JTextField userNameTextField;
    private JPasswordField userPasswordField;
    private JButton addUserButton;
    private JButton addHrButtonPanel;
    //For creating a new password
    private JPasswordField confirmNewPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField oldPasswordField;
    private JButton okConfirmPassButton;
    //For Login
    private JTextField userNameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JPanel logoutPanel;
    private JLabel humanIcon;

    private String[] tableColumns = {"Name","Username","User Type"};
    private String[][] tableData = {{"Nikola","Nikolic","HR"},{"Nikola","Nikolic","HR"},{"Nikola","Nikolic","HR"}};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    public MainScreenAdmin() {
        super("Admin App");
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

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                deleteButtonAction();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchButtonAction();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logoutButtonAction();

            }
        });
        activateButtonPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateButtonPanelAction();
            }
        });
        deleteUserButtonPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUserButtonPanelAction();

            }
        });
        addHrButtonPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addHrButtonPanelAction();
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUserButtonAction();
            }
        });
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPasswordButton();
            }
        });
        okLicenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okLicenceButtonAction();
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

    private void addUserButtonAction() {
    }

    private void showPasswordButton() {
        userPasswordField.setEchoChar((char) 0);
    }

    //For checking the Licence KEy
    private void okLicenceButtonAction() {
    }

    private void logoutButtonAction() {
        CardLayout card = (CardLayout) (backPanel.getLayout());
        card.show(backPanel, "loginPanelCard");
        card = (CardLayout) (loginPanel.getLayout());
        card.show(loginPanel, "loginMainCard");
    }

    private void addHrButtonPanelAction() {
        //Showing Add HR Panel on Screen
        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"addHrPanel");
    }

    private void deleteUserButtonPanelAction() {
        //Showing Delete User Panel on Screen
        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"deleteUserPanel");

    }

    private void activateButtonPanelAction() {
        //Showing activate Panel on Screen
        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"activatePanel");
    }

    private void deleteButtonAction(){

        //Checking if Admin Wants to Delete User
        int tmp = JOptionPane.showConfirmDialog(this,"Are you sure");
        if (tmp == JOptionPane.YES_OPTION){

            //delete the user

        }

    }

    private void searchButtonAction(){

    }
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        userTable = new JTable(tableData,tableColumns);
    }
}
