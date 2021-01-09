package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel backPanel;
    private JPanel leftPanel;
    private JPanel mainPanel;
    private JLabel humanIcon;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu help = new JMenu("help");
    private  JMenuItem contactInfo = new JMenuItem("Contact Info");

    public LoginScreen() {
        //JFrame setup
        super("User App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();

        //menu bar setup
        help.add(contactInfo);
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        contactInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactInfoAction();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonAction();
            }
        });
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

    private void loginButtonAction() {

    }
}
