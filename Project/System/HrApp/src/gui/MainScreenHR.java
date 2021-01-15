package gui;

import model.HumanResourceWorker;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

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
    private JComboBox workPlaceBox;
    private JComboBox sectorBox;
    private JTextField userNameTextField;
    private JPasswordField userPasswordField;
    private JButton showPasswordButton;
    private JButton addUserButton;

    // For Showing Employees
    private JTable employeeTable;
    private JButton detailsButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JComboBox sortBox;
    private JComboBox subSortBox;
    private JPanel homePanel;

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
        showPasswordButton.addActionListener(e -> userPasswordField.setEchoChar((char) 0));
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

        //Need to show All user in   employeeTable

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

    //For Add a New user
    private void addUserButtonAction() {


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

    //Showing Contact Info
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo, "Contact Info\n" +
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortBox = new JComboBox(sortList);
        subSortBox = new JComboBox();

        employeeTable = new JTable(tableData,tableColumns);
    }
}