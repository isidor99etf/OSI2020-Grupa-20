package gui;

import constants.Config;
import constants.FilePaths;
import model.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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

        Worker worker = getWorkerData(userNameField.getText());

        if (worker != null && worker.getPassword().equals(oldPassword)) {

            if (!oldPassword.equals(newPassword))
                if (newPassword.equals(confirmNewPassword)) {

                    Config config = Config.readConfigFile();

                    worker.setPassword(newPassword);
                    worker.setNumberOfLogins(config != null ? config.getNumberOfLogins() : 10);

                    updateWorkerFile(worker);
                }
                else {
                    // show message
                    // MESSAGE_NEW_PASSWORD_NOT_MATCH
                }
            else {
                // show message
                // MESSAGE_SAME_NEW_OLD_PASSWORD
            }

        } else {
            // show message
            // MESSAGE_WRONG_OLD_PASSWORD
        }

        showMainScreen(worker);
    }

    private void login() {

        // Check User Details
        String userName = userNameField.getText();
        String password = new String(loginPasswordField.getPassword());

        Worker worker = getWorkerData(userName);

        // First login or need new password
        if (worker != null && worker.getPassword().equals(password))
            if (worker.getNumberOfLogins() == 0) {

                CardLayout card = (CardLayout) (loginPanel.getLayout());
                card.show(loginPanel, "newPasswordCard");

            } else {
                worker.setNumberOfLogins(worker.getNumberOfLogins() - 1);
                updateWorkerFile(worker);
                showMainScreen(worker);
            }
        else {
            // show message
            // MESSAGE_WRONG_USER_NAME_OR_PASSWORD
        }
    }

    private Worker getWorkerData(String userName) {

        try {
            FileInputStream stream = new FileInputStream(FilePaths.WORKER_ACCOUNTS + userName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();

            reader.close();

            if (line != null) {
                String[] data = line.split(",");
                return new Worker(data);
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    private void updateWorkerFile(Worker worker) {

        try {
            FileWriter fileWriter = new FileWriter(FilePaths.WORKER_ACCOUNTS + worker.getUserName());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(worker.toString());
            writer.flush();
            writer.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(
                contactInfo,
                "Contact Info\nAdmin\nemail: admin@comName.com\nphone:0123456789");
    }

    private void showMainScreen(Worker worker) {
        this.setVisible(false);
        this.dispose();

        new MainScreenUser(worker);
    }
}

