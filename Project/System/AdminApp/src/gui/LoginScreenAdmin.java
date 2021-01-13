package gui;

import constants.Config;
import constants.FilePaths;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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

    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    private static final Logger LOGGER = Logger.getLogger(LoginScreenAdmin.class.getName());
    public LoginScreenAdmin() {
        super("Admin App - Login");
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

        // Check User passwords
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        Admin admin = getAdminCredentials();

        if (admin != null && admin.getPassword().equals(oldPassword)) {

            if (!oldPassword.equals(newPassword))
                if(newPassword.equals(confirmNewPassword)) {

                    Config config = Config.readConfigFile();

                    admin.setPassword(newPassword);
                    admin.setNumberOfLogins(config != null ? config.getNumberOfLogins() : 10);

                    LOGGER.info(admin.getUserName() + " " + admin.getPassword() + " " + admin.getNumberOfLogins());
                    updateAdminFile(admin);

                } else {
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

        showMainScreen();
    }

    private void login() {

        // Check User Details
        String userName = userNameField.getText();
        String password = new String(loginPasswordField.getPassword());

        Admin newAdmin = new Admin(userName, password);
        Admin admin = getAdminCredentials();

        // First login or need new password
        if (admin != null && admin.equals(newAdmin))
            if (admin.getNumberOfLogins() == 0) {

                CardLayout card = (CardLayout) (loginPanel.getLayout());
                card.show(loginPanel, "newPasswordCard");

            } else {
                admin.setNumberOfLogins(admin.getNumberOfLogins() - 1);
                updateAdminFile(admin);
                showMainScreen();
            }
        else {
            // show message
            // MESSAGE_WRONG_USER_NAME_OR_PASSWORD
        }
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(
                contactInfo,
                "Contact Info\nAdmin\nemail: admin@comName.com\nphone:0123456789");
    }

    private void showMainScreen() {
        this.setVisible(false);
        this.dispose();

        new MainScreenAdmin();
    }

    private Admin getAdminCredentials() {

        try {
            FileInputStream stream = new FileInputStream(FilePaths.ADMIN_ACCOUNT);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = inputStream.readLine();

            inputStream.close();

            if (line != null) {

                String[] data = line.split(",");

                return new Admin(data[0], data[1], Integer.parseInt(data[2]));
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    private void updateAdminFile(Admin admin) {

        try {
            FileWriter fileWriter = new FileWriter(FilePaths.ADMIN_ACCOUNT);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(admin.getUserName());
            writer.write(",");
            writer.write(admin.getPassword());
            writer.write(",");
            writer.write(String.valueOf(admin.getNumberOfLogins()));

            writer.flush();
            writer.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }
}
