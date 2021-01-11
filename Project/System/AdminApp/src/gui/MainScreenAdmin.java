package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenAdmin extends JFrame{
    private JPanel backPanel;
    private JButton addHrButtonPanel;
    private JButton deleteUserButtonPanel;
    private JButton activateButtonPanel;
    private JButton logoutButton;
    private JPanel mainPanel;
    private JPanel activatePanel;
    private JPanel deleteUserPanel;
    private JPanel addHrPanel;
    private JTextField keyTextField;
    private JButton okLicenceButton;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField addressTextField;
    private JComboBox workPlaceBox;
    private JComboBox sectorBox;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JButton addUserButton;
    private JButton showPasswordButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JTable userTable;
    private JButton deleteButton;
    private JPanel buttonPanel;
    private JPanel rightPanel;
    private String[] tableColumns = {"Name","Username","User Type"};
    private String[][] tableData = {{"Nikola","Nikolic","HR"},{"Nikola","Nikolic","HR"},{"Nikola","Nikolic","HR"}};

    public MainScreenAdmin() {
        super("Admin App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();

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
    }

    private void addUserButtonAction() {
    }

    private void showPasswordButton() {
    }

    private void okLicenceButtonAction() {
    }

    private void logoutButtonAction() {
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
        userTable = new JTable(tableData,tableColumns);
    }
}
