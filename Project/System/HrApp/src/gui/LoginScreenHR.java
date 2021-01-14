package gui;

import constants.Config;
import constants.FilePaths;
import model.HumanResourceWorker;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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

        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        HumanResourceWorker hrWorker = getHrData(userNameField.getText());

        if (hrWorker != null && hrWorker.getPassword().equals(oldPassword)) {

            if (!oldPassword.equals(newPassword))
                if (newPassword.equals(confirmNewPassword)) {

                    Config config = Config.readConfigFile();

                    hrWorker.setPassword(newPassword);
                    hrWorker.setNumberOfLogins(config != null ? config.getNumberOfLogins() : 10);

                    updateHrWorkerFile(hrWorker);
                }
                else {
                    // show message
                    // MESSAGE_NEW_PASSWORD_NOT_MATCH
                }
            else {
                // show message
                // MESSAGE_SAME_NEW_OLD_PASSWORD
            }
        }
        else {
            // show message
            // MESSAGE_WRONG_OLD_PASSWORD
        }

        showMainScreen(hrWorker);
    }

    private void login() {

        // Check User Details
        String userName = userNameField.getText();
        String password = new String(loginPasswordField.getPassword());

        HumanResourceWorker hrWorker = getHrData(userName);

        // First login or need new password
        if (hrWorker != null && hrWorker.getPassword().equals(password))
            if (hrWorker.getNumberOfLogins() == 0) {

                CardLayout card = (CardLayout) (loginPanel.getLayout());
                card.show(loginPanel, "newPasswordCard");

            } else {

                hrWorker.setNumberOfLogins(hrWorker.getNumberOfLogins() - 1);
                updateHrWorkerFile(hrWorker);
                showMainScreen(hrWorker);
            }
        else {
            // show message
            // MESSAGE_WRONG_USER_NAME_OR_PASSWORD
        }

    }

    private HumanResourceWorker getHrData(String userName) {

        try {
            FileInputStream stream = new FileInputStream(FilePaths.HR_ACCOUNTS + userName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();

            reader.close();

            if (line != null) {
                String[] data = line.split(",");
                return new HumanResourceWorker(data);
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    private void updateHrWorkerFile(HumanResourceWorker hrWorker) {

        try {
            FileWriter fileWriter = new FileWriter(FilePaths.HR_ACCOUNTS + hrWorker.getUserName());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(hrWorker.toString());
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

    private void showMainScreen(HumanResourceWorker hrWorker) {
        this.setVisible(false);
        this.dispose();

        new MainScreenHR(hrWorker);
    }
}

