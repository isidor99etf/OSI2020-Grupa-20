package gui;

import constants.Config;
import constants.Texts;
import model.Admin;
import model.Company;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class LoginScreenAdmin extends JFrame {

    private JPanel backPanel;
    private JPanel leftPanel;
    private JPanel newPasswordCard;
    private JPanel loginMainCard;
    private JPanel loginPanel;
    private JLabel humanIcon;

    // For login stuff
    private JButton loginButton;
    private JPasswordField loginPasswordField;
    private JTextField userNameField;

    // For a new password
    private JPasswordField confirmNewPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField oldPasswordField;
    private JButton okConfirmPassButton;
    private JLabel errorLabelNewPassword;
    private JLabel errorLabelLogin;
    private JLabel companyNameLabel;

    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    private static final Logger LOGGER = Logger.getLogger(LoginScreenAdmin.class.getName());

    public LoginScreenAdmin() {
        super("Admin App - Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
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

        Company company = Company.getDataFromFile();
        if (company != null)
            companyNameLabel.setText(company.getName());

        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
    }

    private void newPassword() {
        //deletes old msg
        showErrorMsgNewPassword("",false);

        // Check User passwords
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        Admin admin = Admin.getDataFromFile();

        if (admin != null && admin.getPassword().equals(oldPassword))
            if (!oldPassword.equals(newPassword))
                if (newPassword.equals(confirmNewPassword)) {

                    Config config = Config.readConfigFile();

                    admin.setPassword(newPassword);
                    admin.setNumberOfLogins(config != null ? config.getNumberOfLogins() : 10);

                    Admin.updateFile(admin);

                    showMainScreen();

                } else
                    showErrorMsgNewPassword(Texts.MESSAGE_NEW_PASSWORD_NOT_MATCH, true);
            else
                showErrorMsgNewPassword(Texts.MESSAGE_SAME_NEW_OLD_PASSWORD, true);
        else
            showErrorMsgNewPassword(Texts.MESSAGE_WRONG_OLD_PASSWORD,true);
    }

    private void login() {
        //deletes old msg
        showErrorMsgLogin("",false);

        // Check User Details
        String userName = userNameField.getText();
        String password = new String(loginPasswordField.getPassword());

        Admin newAdmin = new Admin(userName, password);
        Admin admin = Admin.getDataFromFile();

        // First login or need new password
        if (admin != null && admin.equals(newAdmin))
            if (admin.getNumberOfLogins() == 0) {

                CardLayout card = (CardLayout) (loginPanel.getLayout());
                card.show(loginPanel, "newPasswordCard");

            } else {
                admin.setNumberOfLogins(admin.getNumberOfLogins() - 1);
                Admin.updateFile(admin);
                showMainScreen();
            }
        else
            showErrorMsgLogin(Texts.MESSAGE_WRONG_USER_NAME_OR_PASSWORD,true);

    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo, Company.getContactInfo());
    }

    private void showMainScreen() {
        this.setVisible(false);
        this.dispose();

        new MainScreenAdmin();
    }

    private void showErrorMsgLogin(String error, boolean visible) {
        errorLabelLogin.setText(error);
        errorLabelLogin.setVisible(visible);

        if (visible)
            flashTextFieldLogin();
    }

    private void showErrorMsgNewPassword(String error, boolean visible) {
        errorLabelNewPassword.setText(error);
        errorLabelNewPassword.setVisible(visible);

        if (visible)
            flashTextFieldNew();
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
