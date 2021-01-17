package gui;

import constants.FilePaths;
import constants.Texts;
import model.Admin;
import model.Employee;
import model.HumanResourceWorker;
import model.Worker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(MainScreenHR.class.getName());

    private final String[] sortList = {"All","Sector","Work Place"};
    private final String[] subSortListSector = {"Sector1","Sector2","Sector2"};
    private final String[] subSortListWork = {"Work Place2","Work Place2","Work Place3"};
    private final String[] tableColumns ={"Name","Surname","Sector","Work Place","Status"};
    private final String[][] tableData = {{"NIkola","Nikolic","Sector 1","Work Place 1","At work"},
                                      {"NIkola","Nikolic","Sector 1","Work Place 1","At work"}};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

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
    }

    //For sorting and displaying users in employeeTable
    private void subSortBoxAction() {
    }

    private void sortBoxAction() {

        // Need to show All user in   employeeTable

        String item =  (String)sortBox.getSelectedItem();
        if(!item.equals("All")){
            subSortBox.removeAllItems();
            if (item.equals("Sector")){
                for (String sub : subSortListSector){
                        subSortBox.addItem(sub);
                }

            }
            else {
                for (String sub : subSortListWork) {
                    subSortBox.addItem(sub);
                }
            }
           subSortBox.setEnabled(true);

        }
        else {
            subSortBox.removeAllItems();
            subSortBox.setEnabled(false);
        }
    }

    // For Add a New user
    private void addUserButtonAction() {

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

        File file = new File(FilePaths.WORKER_ACCOUNTS + userName);
        if (!file.exists()) {

            if (!Employee.DATE_PATTERN.matcher(dateOfBirth).find()) {
                // show message
                // Texts.MESSAGE_DATE_FORMAT
                System.out.println(Texts.MESSAGE_DATE_FORMAT);
                return;
            }

            if (!Employee.EMAIL_PATTERN.matcher(email).find()) {
                // show message
                // Texts.MESSAGE_EMAIL_FORMAT
                System.out.println(Texts.MESSAGE_EMAIL_FORMAT);
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

        } else {
            // show message
            // MESSAGE_WORKER_EXISTS
        }
    }


    //For searching Users and showing them in   employeeTable
    private void searchButtonAction() {
        //Name in searchTextField

    }

    //For Show User Details selected in employeeTable
    private void detailsButtonAction() {
        //Needs a Worker Odj to work
        // new UserDetails( worker );

    }

    private void logoutButtonAction() {
        this.dispose();
        new LoginScreenHR();
    }

    //Showing addUserCard   on mainPanel
    private void newUserCardButtonAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel, "addUserCard");


    }

    //Showing employeeCard   on mainPanel
    private void employeesButtonAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel, "employeeCard");
    }

    // Showing Contact Info
    private void contactInfoAction() {
        Admin admin = Admin.getDataFromFile();
        String contactInfoMessage = "";
        if (admin != null)
            contactInfoMessage =
                    String.format("Contact Info:\nAdmin email: %s\nAdmin phone: %s", admin.getEmail(), admin.getPhone());

        JOptionPane.showMessageDialog(contactInfo,contactInfoMessage);
    }

    private void togglePassword() {
        userPasswordField.setEchoChar((char) 0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortBox = new JComboBox(sortList);
        subSortBox = new JComboBox();

        employeeTable = new JTable(tableData,tableColumns);
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
}