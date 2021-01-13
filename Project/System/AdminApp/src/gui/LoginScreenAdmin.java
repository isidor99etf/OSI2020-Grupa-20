package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreenAdmin extends JFrame{
    private JPanel backPanel;
    private JPanel leftPanel;
    private JPanel newPasswordCard;
    private JPanel loginMainCard;
    private JPanel loginPanel;
    private JLabel humanIcon;
    //For login stuff
    private JButton loginButton;
    private JPasswordField loginPasswordField;
    private JTextField userNameField;
    //For a new password
    private JPasswordField confirmNewPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField oldPasswordField;
    private JButton okConfirmPassButton;

    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    public LoginScreenAdmin() {
        super("Admin App - Login");
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

        okConfirmPassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPassword();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               login();
            }
        });
    }

    private void newPassword() {
        //Check USer passwords

        showMainScreen();
    }

    private void login() {
        //Check User Details

        //First login or need new password
        if (true) {
            CardLayout card = (CardLayout) (loginPanel.getLayout());
            card.show(loginPanel, "newPasswordCard");
        } else {
            showMainScreen();
        }

    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

    private void showMainScreen() {
        this.setVisible(false);
        this.dispose();
        new MainScreenAdmin();
    }
}
