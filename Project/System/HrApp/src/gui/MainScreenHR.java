package gui;

import constants.Config;
import constants.FilePaths;
import constants.Texts;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainScreenHR extends JFrame {

    // Just Panels
    private JPanel backPanel;
    private JPanel mainPanelCard;
    private JPanel loginPanel;
    private JPanel mainPanel;
    private JPanel addUserCard;
    private JPanel rightPanel;
    private JPanel buttonPanel;
    private JPanel employeeCard;
    private JButton logoutButton;

    // For switching main Panels
    private JButton newUserCardButton;
    private JButton employeesButton;

    // For Adding a New User
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField addressTextField;
    private JTextField userNameTextField;
    private JPasswordField userPasswordField;
    private JButton showPasswordButton;
    private JButton addUserButton;
    private JTextField sectorTextField;
    private JTextField workPlaceTextField;
    private JTextField phoneTextField;
    private JTextField emailTextField;

    // For Showing Employees
    private JTable employeeTable;
    private JButton detailsButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JComboBox sortBox;
    private JComboBox subSortBox;
    private JPanel homePanel;
    private JLabel errorMsgLabel;
    private JLabel licenceErrorLabel;

    private static final Logger LOGGER = Logger.getLogger(MainScreenHR.class.getName());

    private final String[] sortList = {"All","Sector","Work Place"};
    private final String[] tableColumns ={"Username", "Name and Surname", "Sector", "Work Place"};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

    private boolean isPasswordHidden = true;

    private ArrayList<Employee> workers;

    public MainScreenHR(HumanResourceWorker hrWorker) {
        super("HR App");
        this.setContentPane(backPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        JMenu help = new JMenu("help");
        help.add(contactInfo);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        contactInfo.addActionListener(e -> contactInfoAction());
        newUserCardButton.addActionListener(e -> newUserCardButtonAction());
        employeesButton.addActionListener(e -> employeesButtonAction());
        logoutButton.addActionListener(e -> logoutButtonAction());
        showPasswordButton.addActionListener(e -> togglePassword());
        detailsButton.addActionListener(e -> detailsButtonAction());
        searchButton.addActionListener(e -> searchButtonAction());
        addUserButton.addActionListener(e -> addUserButtonAction());
        sortBox.addItemListener(e -> sortBoxAction());
        subSortBox.addItemListener(e -> subSortBoxAction());

        workers = new ArrayList<>();
    }

    // For sorting and displaying users in employeeTable
    private void subSortBoxAction() {

        String cat = (String) sortBox.getSelectedItem();
        String item = (String) subSortBox.getSelectedItem();

        if (cat != null && item != null) {
            ArrayList<Employee> targetData = null;

            if (cat.equals("Sector"))
                targetData = (ArrayList<Employee>) workers.stream()
                        .filter(w -> w.getSector().equals(item))
                        .collect(Collectors.toList());
            else if (cat.equals("Work Place"))
                targetData = (ArrayList<Employee>) workers.stream()
                        .filter(w -> w.getWorkPlace().equals(item))
                        .collect(Collectors.toList());

            if (targetData != null) 
                arrayListToMatrix(targetData);
        }
    }

    private void sortBoxAction() {

        // Need to show All user in   employeeTable
        String item =  (String) sortBox.getSelectedItem();

        if (item != null && !item.equals("All")) {

            subSortBox.removeAllItems();
            ArrayList<String> data = null;

            if (item.equals("Sector"))
                // get sectors
                data = (ArrayList<String>) workers.stream().map(Employee::getSector).collect(Collectors.toList());

            else if (item.equals("Work Place"))
                // get work places
                data = (ArrayList<String>) workers.stream().map(Employee::getWorkPlace).collect(Collectors.toList());

            else if (item.equals("All"))
                employeesButtonAction();


            if (data != null) {
                for (String sub : data)
                    subSortBox.addItem(sub);
                subSortBox.setSelectedIndex(0);
                subSortBox.setEnabled(true);
            }
        } else {
            subSortBox.removeAllItems();
            subSortBox.setEnabled(false);
        }
    }

    // For Add a New user
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
            // String password = new String(userPasswordField.getPassword());

            String password = Employee.getPasswordSha(new String(userPasswordField.getPassword()));

            File file = new File(FilePaths.WORKER_ACCOUNTS + userName);
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

                Worker worker = new Worker(name, surname, dateOfBirth, address, phone, email, workPlace, sector, userName, password);

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

                Worker.updateFile(worker);
                flashUserTextFields();

                Company company = Company.getDataFromFile();
                if (company != null) {
                    company.setNumberOfWorkers(company.getNumberOfWorkers() + 1);
                    Company.writeData(company);
                }

                newUserCardButtonAction();

            } else {
                showErrorMsg(Texts.MESSAGE_WORKER_EXISTS, true);
                System.out.println(Texts.MESSAGE_WORKER_EXISTS);
            }
        } else {
            showErrorMsg(Texts.MESSAGE_ALL_FIELDS_REQUIRED, true);
            System.out.println(Texts.MESSAGE_ALL_FIELDS_REQUIRED);
        }

        this.pack();
    }


    //For searching Users and showing them in   employeeTable
    private void searchButtonAction() {
        //Name in searchTextField

    }

    //For Show User Details selected in employeeTable
    private void detailsButtonAction() {

        int row = employeeTable.getSelectedRow();
        String username = (String) employeeTable.getValueAt(row, 0);
        System.out.println(username);

        Worker worker = (Worker) workers.stream().filter(w -> w.getUserName().equals(username)).findFirst().orElse(null);

        if (worker != null)
            new UserDetails(worker);
    }

    private void logoutButtonAction() {
        this.dispose();
        new LoginScreenHR();
    }

    // Showing addUserCard   on mainPanel
    private void newUserCardButtonAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel, "addUserCard");

        showErrorMsg("", false);

        Config config = Config.readConfigFile();
        Company company = Company.getDataFromFile();

        if (company != null && config != null && !config.isHaveLicence()) {
            if (company.getNumberOfWorkers() >= config.getNumberOfWorkerAccounts()) {
                addUserButton.setEnabled(false);

                // showErrorMsg(Texts.MESSAGE_MAX_NUMBER_OF_WORKERS, true);
                licenceErrorLabel.setText(Texts.MESSAGE_MAX_NUMBER_OF_WORKERS);
                licenceErrorLabel.setVisible(true);
                System.out.println(Texts.MESSAGE_MAX_NUMBER_OF_WORKERS);
            }
        } else {
            addUserButton.setEnabled(true);
            licenceErrorLabel.setText("");
            licenceErrorLabel.setVisible(false);
        }
    }

    // Showing employeeCard   on mainPanel
    private void employeesButtonAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel, "employeeCard");

        workers.clear();

        File workersFolder = new File(FilePaths.WORKER_ACCOUNTS);
        if (workersFolder.exists()) {
            File[] files = workersFolder.listFiles();
            if (files != null)
            for (File file : files) {
                Worker worker = Worker.getDataFromFile(file);
                workers.add(worker);
            }

            System.out.println(workers.size());
            arrayListToMatrix(workers);
        }
    }

    // Showing Contact Info
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo, Company.getContactInfo());
    }

    //
    private void togglePassword() {
        if (isPasswordHidden) {
            userPasswordField.setEchoChar((char) 0);
            isPasswordHidden = false;
        } else {
            userPasswordField.setEchoChar('•');
            isPasswordHidden = true;
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortBox = new JComboBox(sortList);
        sortBox.setSelectedIndex(0);

        subSortBox = new JComboBox();

        employeeTable = new JTable(new Object[1][tableColumns.length], tableColumns);
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

    private boolean checkFields() {
        return nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty() ||
                dateOfBirthTextField.getText().isEmpty() || addressTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                workPlaceTextField.getText().isEmpty() || sectorTextField.getText().isEmpty() ||
                userNameTextField.getText().isEmpty() || userPasswordField.getPassword().length == 0;

    }

    private void showErrorMsg(String msg, boolean visible){
        errorMsgLabel.setText(msg);
        errorMsgLabel.setVisible(visible);
    }

    private void arrayListToMatrix(ArrayList<Employee> employees) {

        // TableModel t = employeeTable.getModel();
        TableModel t = new DefaultTableModel(tableColumns, employees.size());
        for (int i = 0; i < employees.size(); ++i) {
            t.setValueAt(employees.get(i).getUserName(), i, 0);
            t.setValueAt(employees.get(i).getName(), i, 1);
            t.setValueAt(employees.get(i).getSector(), i, 2);
            t.setValueAt(employees.get(i).getWorkPlace(), i, 3);
        }

        employeeTable.setModel(t);
        this.pack();
    }
}