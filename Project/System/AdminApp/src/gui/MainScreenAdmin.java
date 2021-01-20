package gui;

import admin_app.Main;
import constants.Config;
import constants.FilePaths;
import constants.Texts;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.logging.Logger;

public class MainScreenAdmin extends JFrame {

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

    // For activating the App
    private JButton activateButtonPanel;
    private JTextField keyTextField;
    private JButton okLicenceButton;

    // For deleting USer
    private JButton searchButton;
    private JTable userTable;
    private JButton deleteButton;
    private JButton deleteUserButtonPanel;
    private JTextField searchTextField;

    // For Adding a HR User
    private JButton showPasswordButton;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField addressTextField;
    private JTextField userNameTextField;
    private JPasswordField userPasswordField;
    private JButton addUserButton;
    private JButton addHrButtonPanel;
    private JTextField phoneTextField;
    private JTextField emailTextField;
    private JTextField sectorTextField;
    private JTextField workPlaceTextField;

    //
    private JButton logoutButton;
    private JLabel licenceMsgLabel;
    private JPanel addEmailPanel;
    private JPanel addPhonePanel;
    private JTextField newPhoneTextField;
    private JButton addNewPhoneButton;
    private JTextField newEmailTextField;
    private JButton addNewEmailButton;
    private JButton addPhoneButtonPanel;
    private JButton addEmailButtonPanel;
    private JLabel errorMsgLabel;
    private JLabel errorMsgKeyLabel;

    private boolean isPasswordHidden = true;

    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    private final DefaultTableModel workerModel;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public MainScreenAdmin() {
        super("Admin App");
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

        String[] tableColumns = {"Username", "Name", "Surname", "User Type"};
        workerModel = new DefaultTableModel(null, tableColumns);
        userTable.setModel( workerModel );

        contactInfo.addActionListener(e -> contactInfoAction());
        deleteButton.addActionListener(e -> deleteButtonAction());
        searchButton.addActionListener(e -> searchButtonAction());
        logoutButton.addActionListener(e -> logoutButtonAction());
        activateButtonPanel.addActionListener(e -> activateButtonPanelAction());
        deleteUserButtonPanel.addActionListener(e -> deleteUserButtonPanelAction());
        addHrButtonPanel.addActionListener(e -> addHrButtonPanelAction());
        addUserButton.addActionListener(e -> addUserButtonAction());
        showPasswordButton.addActionListener(e -> togglePassword());
        okLicenceButton.addActionListener(e -> okLicenceButtonAction());
        addNewPhoneButton.addActionListener(e -> addNewPhoneButtonAction());
        addNewEmailButton.addActionListener(e -> addNewEmailButtonAction());
        addEmailButtonPanel.addActionListener(e -> addEmailButtonPanelAction());
        addPhoneButtonPanel.addActionListener(e -> addPhoneButtonPanelAction());
    }

    // For adding a new email address
    private void addNewPhoneButtonAction() {

        Company company = Company.getDataFromFile();
        String phone = newPhoneTextField.getText();

        if (company != null) {
            company.addPhone(phone);
            Company.writeData(company);
        }

        newPhoneTextField.setText("");
    }

    // For adding a new phone number
    private void addNewEmailButtonAction() {
        Company company = Company.getDataFromFile();
        String email = newEmailTextField.getText();

        if (company != null) {
            company.addEmail(email);
            Company.writeData(company);
        }
        newEmailTextField.setText("");
    }

    // Shows ADD new Email address on Screen
    private void addEmailButtonPanelAction() {
        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"addEmailPanel");
    }

    // Shows ADD New phone number on Screen
    private void addPhoneButtonPanelAction() {
        CardLayout card = (CardLayout) mainPanel.getLayout();
        card.show(mainPanel,"addPhonePanel");
    }


    // For Add a new HR user
    private void addUserButtonAction() {

        showErrorMsg("", false);

        if (!checkFields()) {

            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String dateOfBirth = dateOfBirthTextField.getText();
            String address = addressTextField.getText();
            String phone = phoneTextField.getText();
            String email = emailTextField.getText();
            String workPlace = workPlaceTextField.getText();
            String sector = sectorTextField.getText();
            String userName = userNameTextField.getText();
            String password = new String(userPasswordField.getPassword());

            File file = new File(FilePaths.HR_ACCOUNTS + userName);
            if (!file.exists()) {

                if (!Employee.DATE_PATTERN.matcher(dateOfBirth).find()) {

                    showErrorMsg(Texts.MESSAGE_DATE_FORMAT, true);
                    System.out.println(Texts.MESSAGE_DATE_FORMAT);
                    this.pack();
                    return;
                }

                if (!Employee.EMAIL_PATTERN.matcher(email).find()) {

                    showErrorMsg(Texts.MESSAGE_EMAIL_FORMAT, true);
                    System.out.println(Texts.MESSAGE_EMAIL_FORMAT);
                    this.pack();
                    return;
                }

                HumanResourceWorker worker = new HumanResourceWorker(name, surname, dateOfBirth, address, phone, email, workPlace, sector, userName, password);

                File register = new File(FilePaths.WORKER_REGISTER);
                File[] files = register.listFiles();

                int pin;
                boolean isPin = true;
                Random random = new Random();
                do {
                    pin = 100_000 + random.nextInt(800_000);

                    if (files != null)
                        for (File f : files)
                            if (f.getName().equals(String.valueOf(pin)))
                                isPin = false;

                } while (!isPin);

                worker.setPIN(pin);

                // create register file
                try {
                    File workerRegister = new File(FilePaths.WORKER_REGISTER + pin);
                    workerRegister.createNewFile();
                } catch (Exception exception) {
                    LOGGER.warning(exception.fillInStackTrace().toString());
                }

                HumanResourceWorker.updateFile(worker);
                flashUserTextFields();

                Company company = Company.getDataFromFile();
                if (company != null) {
                    company.setNumberOfHrWorkers(company.getNumberOfHrWorkers() + 1);
                    Company.writeData(company);
                }

            } else {

                showErrorMsg(Texts.MESSAGE_WORKER_EXISTS,true);
                System.out.println(Texts.MESSAGE_WORKER_EXISTS);
            }
        } else {
            showErrorMsg(Texts.MESSAGE_ALL_FIELDS_REQUIRED, true);
            System.out.println(Texts.MESSAGE_ALL_FIELDS_REQUIRED);
        }

        this.pack();
    }

    // For checking the Licence Key
    private void okLicenceButtonAction() {
        showErrorMsgKey("",false);

        Config config = Config.readConfigFile();
        String key = keyTextField.getText();

        if (config != null && key.equals(config.getLicencesKey()) && !config.isHaveLicence()) {
            config.setHaveLicence(true);
            config.setNumberOfWorkerAccounts(10);
            config.setNumberOfHrAccounts(5);

            // Looking the input and showing a msg
            keyTextField.setText("");
            keyTextField.setEnabled(false);
            okLicenceButton.setEnabled(false);
            licenceMsgLabel.setText(Texts.MESSAGE_LICENCE_HAS_BEEN_ACTIVATED);
            licenceMsgLabel.setVisible(true);

            // Saving Config
            Config.writeConfigFile(config);
        } else {

            showErrorMsgKey(Texts.MESSAGE_WRONG_LICENCE, true);
            System.out.println(Texts.MESSAGE_WRONG_LICENCE);
        }
        this.pack();
    }

    private void showErrorMsgKey(String msg, boolean visible){
        errorMsgKeyLabel.setVisible(visible);
        errorMsgKeyLabel.setText(msg);
    }

    // For log out
    private void logoutButtonAction() {
        this.dispose();
        new LoginScreenAdmin();
    }

    //Showing Add HR Panel on Screen
    private void addHrButtonPanelAction() {

        showErrorMsg("", false);

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"addHrPanel");

        sectorTextField.setText("Administracija");
        sectorTextField.setEditable(false);
        workPlaceTextField.setText("Menadzer ljudskih resursa");
        workPlaceTextField.setEditable(false);

        Config config = Config.readConfigFile();
        Company company = Company.getDataFromFile();

        if (company != null && config != null && !config.isHaveLicence())
            if (company.getNumberOfHrWorkers() >= config.getNumberOfHrAccounts()) {
                addUserButton.setEnabled(false);

                showErrorMsg(Texts.MESSAGE_MAX_NUMBER_OF_WORKERS,true);
                System.out.println(Texts.MESSAGE_MAX_NUMBER_OF_WORKERS);
            }
    }

    //Showing Delete User Panel on Screen
    private void deleteUserButtonPanelAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"deleteUserPanel");

    }

    //Showing activate Panel on Screen
    private void activateButtonPanelAction() {
        Config config = Config.readConfigFile();

        licenceMsgLabel.setText("");
        showErrorMsgKey("", false);

        if (config != null && config.isHaveLicence()) {

            // Looking the input and showing a msg
            keyTextField.setEnabled(false);
            okLicenceButton.setEnabled(false);
            licenceMsgLabel.setText(Texts.MESSAGE_LICENCE_IS_ACTIVE);
            licenceMsgLabel.setVisible(true);
        }

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"activatePanel");
    }

    private void deleteButtonAction() {

        // Checking if Admin Wants to Delete User
        int tmp = JOptionPane.showConfirmDialog(this,"Are you sure");
        if (tmp == JOptionPane.YES_OPTION) {

            String username = searchTextField.getText();
            String path = FilePaths.WORKER_ACCOUNTS+username;
            String hrPath = FilePaths.HR_ACCOUNTS+username;
            Worker worker = null;
            HumanResourceWorker humanResourceWorker = null;

            try {
                FileInputStream stream = new FileInputStream(path);
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

                String line = "", tempLine;

                while ((tempLine = inputStream.readLine()) != null)
                    line = tempLine;

                inputStream.close();

                String[] data = line.split(",");
                worker = new Worker(data);

                if (worker.isActive())
                    worker.setActive(false); //Radnik vise nije aktivan, deaktiviran mu je korisnicki nalog

            } catch (Exception exception) {
                LOGGER.warning(exception.fillInStackTrace().toString());
            }

            try {
                FileOutputStream stream = new FileOutputStream(path);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

                writer.write(worker.toString()); //Nakon deaktiviranja vrsimo ponovni upis u fajl4
                writer.close();

            }
            catch (Exception exception)
            {
                LOGGER.warning(exception.fillInStackTrace().toString());
            }


            //Za HR naloge

            try {
                FileInputStream stream = new FileInputStream(hrPath);
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

                String line = "", tempLine;

                while ((tempLine = inputStream.readLine()) != null)
                    line = tempLine;

                inputStream.close();

                String[] data = line.split(",");

                humanResourceWorker=new HumanResourceWorker(data);

                boolean active = humanResourceWorker.isActive();

                if(active)
                {
                    active = false;
                    humanResourceWorker.setActive(false); //Radnik vise nije aktivan, deaktiviran mu je korisnicki nalog
                }




            } catch (Exception exception) {
                LOGGER.warning(exception.fillInStackTrace().toString());
            }

            try{
                FileOutputStream stream = new FileOutputStream(hrPath);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

                writer.write(humanResourceWorker.toString()); //Nakon deaktiviranja vrsimo ponovni upis u fajl
                writer.close();

            }
            catch (Exception exception)
            {
                LOGGER.warning(exception.fillInStackTrace().toString());
            }

            searchTextField.setText("");
            //delete the user

        }

    }

    //searches for  user and show it in  userTable
    private void searchButtonAction() {

        String username = searchTextField.getText();
        String path = FilePaths.WORKER_ACCOUNTS+username;
        String hrPath = FilePaths.HR_ACCOUNTS + username;
        Worker worker = null;
        HumanResourceWorker humanResourceWorker = null;
        try {
            FileInputStream stream = new FileInputStream(path);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = null, tempLine;

            while ((tempLine = inputStream.readLine()) != null)
                line = tempLine;

            inputStream.close();

            String datas[] = line.split(",");

            worker=new Worker(datas);

            if(worker!=null)
            {
                addUserInTable(worker,"Worker");
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
        try {
            FileInputStream stream = new FileInputStream(hrPath);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = null, tempLine;

            while ((tempLine = inputStream.readLine()) != null)
                line = tempLine;

            inputStream.close();

            String datas[] = line.split(",");

            humanResourceWorker=new HumanResourceWorker(datas);

            if(worker!=null)
            {
                addUserInTable(worker,"HR");
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
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

    private void flashUserTextFields() {
        nameTextField.setText("");
        surnameTextField.setText("");
        dateOfBirthTextField.setText("");
        addressTextField.setText("");
        phoneTextField.setText("");
        emailTextField.setText("");
        sectorTextField.setText("");
        workPlaceTextField.setText("");
        userNameTextField.setText("");
        userPasswordField.setText("");
    }

    private void togglePassword() {
        if (isPasswordHidden) {
            userPasswordField.setEchoChar((char) 0);
            isPasswordHidden = false;
        } else {
            userPasswordField.setEchoChar('•');
            isPasswordHidden = true;
        }
    }

    private void addUserInTable(Worker worker, String type) {
        workerModel.getDataVector().removeAllElements();
        workerModel.insertRow(0, new Object[] {worker.getUserName(),worker.getFirstName(),worker.getSurname(),type});
    }

    private boolean checkFields() {
        return nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() ||
                dateOfBirthTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                workPlaceTextField.getText().isEmpty() || sectorTextField.getText().isEmpty() ||
                userNameTextField.getText().isEmpty() || userPasswordField.getPassword().length == 0;

    }

    private void showErrorMsg (String msg, boolean visible){
        errorMsgLabel.setText(msg);
        errorMsgLabel.setVisible(visible);
    }
}
