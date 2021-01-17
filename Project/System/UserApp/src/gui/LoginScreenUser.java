package gui;

import constants.Config;
import constants.Texts;
import model.Admin;
import model.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class LoginScreenUser extends JFrame {

    private JPanel backPanel;
    private JPanel leftPanel;
    private JLabel humanIcon;
    private JPanel loginPanel;
    private JPanel newPasswordCard;

    // Fie new Password
    private JPasswordField confirmNewPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField oldPasswordField;
    private JButton okConfirmPassButton;

    // For Login
    private JPanel loginMainCard;
    private JTextField userNameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JLabel errorLabelNewPassword;
    private JLabel errorLabelLogin;

    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    private static final Logger LOGGER = Logger.getLogger(LoginScreenUser.class.getName());

    public LoginScreenUser() {
        super("User App - Login");
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
        okConfirmPassButton.addActionListener(e -> newPassword());
        loginButton.addActionListener(e -> login());
    }

    private void newPassword() {

        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        Worker worker = Worker.getDataFromFile(userNameField.getText());

        // Deletes Old Msg
        showErrorMsgNewPassword("",false);

        if (worker != null && worker.getPassword().equals(oldPassword))
            if (!oldPassword.equals(newPassword))
                if (newPassword.equals(confirmNewPassword)) {

                    Config config = Config.readConfigFile();

                    worker.setPassword(newPassword);
                    worker.setNumberOfLogins(config != null ? config.getNumberOfLogins() : 10);

                    Worker.updateFile(worker);

                    showMainScreen(worker);
                }
                else
                    showErrorMsgNewPassword(Texts.MESSAGE_NEW_PASSWORD_NOT_MATCH,true);
            else
                showErrorMsgNewPassword(Texts.MESSAGE_SAME_NEW_OLD_PASSWORD,true);
        else
            showErrorMsgNewPassword(Texts.MESSAGE_WRONG_OLD_PASSWORD,true);
    }

    private void login() {

        // Check User Details
        String userName = userNameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        Worker worker = Worker.getDataFromFile(userName);

        // Deletes odl msg
        showErrorMsgLogin("",false);

        // First login or need new password
        if (worker != null && worker.getPassword().equals(password))
            if (worker.getNumberOfLogins() == 0) {

                CardLayout card = (CardLayout) (loginPanel.getLayout());
                card.show(loginPanel, "newPasswordCard");

            } else {
                worker.setNumberOfLogins(worker.getNumberOfLogins() - 1);
                Worker.updateFile(worker);
                showMainScreen(worker);
            }
        else {

            showErrorMsgLogin(Texts.MESSAGE_WRONG_USER_NAME_OR_PASSWORD,true);
        }
    }

    private void contactInfoAction() {
        Admin admin = Admin.getDataFromFile();
        String contactInfoMessage = "";
        if (admin != null)
            contactInfoMessage =
                    String.format("Contact Info:\nAdmin email: %s\nAdmin phone: %s", admin.getEmail(), admin.getPhone());

        JOptionPane.showMessageDialog(contactInfo,contactInfoMessage);
    }

    private void showMainScreen(Worker worker) {
        this.setVisible(false);
        this.dispose();

        new MainScreenUser(worker);
    }

    private void showErrorMsgLogin(String error, boolean visible) {
        errorLabelLogin.setText(error);
        errorLabelLogin.setVisible(visible);

        if(visible) {
            flashTextFieldLogin();
        }

        this.pack();
    }

    private void showErrorMsgNewPassword(String error, boolean visible){
        errorLabelNewPassword.setText(error);
        errorLabelNewPassword.setVisible(visible);

        if(visible) {
            flashTextFieldNew();
        }

        this.pack();
    }

    private void flashTextFieldLogin() {
        userNameField.setText("");
        loginPasswordField.setText("");
    }

    private void flashTextFieldNew() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmNewPasswordField.setText("");
    }

}

