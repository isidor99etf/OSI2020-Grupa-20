package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MainScreenAdmin extends JFrame{
    private JPanel backPanel;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel addHrPanel;
    private JPanel homePanel;
    private JPanel buttonPanel;
    private JPanel rightPanel;
    private JPanel logoutPanel;
    private JPanel deleteUserPanel;
    private JPanel activatePanel;
    //For activating the App
    private JButton activateButtonPanel;
    private JTextField keyTextField;
    private JButton okLicenceButton;
    //For deleting USer
    private JButton searchButton;
    private JTable userTable;
    private JButton deleteButton;
    private JButton deleteUserButtonPanel;
    private JButton addUserButton;
    private JButton addHrButtonPanel;
    private JTextField searchTextField;
    //For Adding a HR User
    private JButton showPasswordButton;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField addressTextField;
    private JComboBox workPlaceBox;
    private JComboBox sectorBox;
    private JTextField userNameTextField;
    private JPasswordField userPasswordField;
    //
    private JButton logoutButton;

    private final String[] tableColumns = {"Name","Username","User Type"};

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

    }



    //For Add a new HR user
    private void addUserButtonAction() {
    }

    //Shows user password in String form not in *
    private void showPasswordButton() {
        userPasswordField.setEchoChar((char) 0);
    }

    //For checking the Licence KEy
    private void okLicenceButtonAction() {
    }

    //For login out
    private void logoutButtonAction() {

    }

    //Showing Add HR Panel on Screen
    private void addHrButtonPanelAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"addHrPanel");
    }

    //Showing Delete User Panel on Screen
    private void deleteUserButtonPanelAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"deleteUserPanel");

    }

    //Showing activate Panel on Screen
    private void activateButtonPanelAction() {

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

    //searches for  user and show it in  userTable
    private void searchButtonAction(){

    }
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

}
