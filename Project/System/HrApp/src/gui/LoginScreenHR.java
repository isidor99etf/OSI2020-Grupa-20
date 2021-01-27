package gui;

import constants.Config;
import constants.Texts;
import model.Admin;
import model.Company;
import model.Employee;
import model.HumanResourceWorker;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

public class LoginScreenHR extends JFrame {

    private JPanel backPanel;
    private JPanel leftPanel;
    private JLabel humanIcon;
    private JPanel loginPanel;
    private JPanel newPasswordCard;

    // For a new Password
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

    private static final Logger LOGGER = Logger.getLogger(LoginScreenHR.class.getName());

    public LoginScreenHR() {
        super("HR App - Login");
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

        showErrorMsgNewPassword("",false);

        /*String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());*/

        String oldPassword = Employee.getPasswordSha(new String(oldPasswordField.getPassword()));
        String newPassword = Employee.getPasswordSha(new String(newPasswordField.getPassword()));
        String confirmNewPassword = Employee.getPasswordSha(new String(confirmNewPasswordField.getPassword()));

        HumanResourceWorker hrWorker = HumanResourceWorker.getDataFromFile(userNameField.getText());

        if (hrWorker != null && hrWorker.getPassword().equals(oldPassword))
            if (!oldPassword.equals(newPassword))
                if (newPassword.equals(confirmNewPassword)) {

                    Config config = Config.readConfigFile();

                    hrWorker.setPassword(newPassword);
                    hrWorker.setNumberOfLogins(config != null ? config.getNumberOfLogins() : 10);

                    HumanResourceWorker.updateFile(hrWorker);

                    showMainScreen(hrWorker);
                }
                else
                    showErrorMsgNewPassword(Texts.MESSAGE_NEW_PASSWORD_NOT_MATCH,true);
            else
                showErrorMsgNewPassword(Texts.MESSAGE_SAME_NEW_OLD_PASSWORD,true);
        else
            showErrorMsgNewPassword(Texts.MESSAGE_WRONG_OLD_PASSWORD,true);
    }

    private void login() {
        //Deletes Old msg
        showErrorMsgLogin("",false);

        // Check User Details
        String userName = userNameField.getText();
        // String password = new String(loginPasswordField.getPassword());

        String password = Employee.getPasswordSha(new String(loginPasswordField.getPassword()));

        HumanResourceWorker hrWorker = HumanResourceWorker.getDataFromFile(userName);

        // First login or need new password
        if (hrWorker != null && hrWorker.getPassword().equals(password))
            if (hrWorker.isActive())
                if (hrWorker.getNumberOfLogins() == 0) {

                    CardLayout card = (CardLayout) (loginPanel.getLayout());
                    card.show(loginPanel, "newPasswordCard");

                } else {

                    hrWorker.setNumberOfLogins(hrWorker.getNumberOfLogins() - 1);
                    HumanResourceWorker.updateFile(hrWorker);
                    showMainScreen(hrWorker);
                }
            else
                showErrorMsgLogin(Texts.MESSAGE_DEACTIVATED_ACCOUNT, true);
        else
            showErrorMsgLogin(Texts.MESSAGE_WRONG_USER_NAME_OR_PASSWORD,true);
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo, Company.getContactInfo());
    }

    private void showMainScreen(HumanResourceWorker hrWorker) {
        this.setVisible(false);
        this.dispose();

        new MainScreenHR(hrWorker);
    }

    private void showErrorMsgLogin(String error, boolean visible) {
        errorLabelLogin.setText(error);
        errorLabelLogin.setVisible(visible);

        if(visible) {
            flashTextFieldLogin();
        }
        this.pack();
    }

    private void showErrorMsgNewPassword(String error, boolean visible) {
        errorLabelNewPassword.setText(error);
        errorLabelNewPassword.setVisible(visible);

        if( visible)
            flashTextFieldNew();

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

