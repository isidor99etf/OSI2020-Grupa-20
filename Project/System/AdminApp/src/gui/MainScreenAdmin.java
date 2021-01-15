package gui;

import admin_app.Main;
import constants.Config;
import constants.FilePaths;
import model.Date;
import model.HumanResourceWorker;
import model.Time;
import model.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
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
    private JComboBox workPlaceBox;
    private JComboBox sectorBox;
    private JTextField userNameTextField;
    private JPasswordField userPasswordField;
    private JButton addUserButton;
    private JButton addHrButtonPanel;

    //
    private JButton logoutButton;

    private final String[] tableColumns = {"Name","Username","User Type"};

    private final JMenuItem contactInfo = new JMenuItem("Contact Info");

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


        contactInfo.addActionListener(e -> contactInfoAction());
        deleteButton.addActionListener(e -> deleteButtonAction());
        searchButton.addActionListener(e -> searchButtonAction());
        logoutButton.addActionListener(e -> logoutButtonAction());
        activateButtonPanel.addActionListener(e -> activateButtonPanelAction());
        deleteUserButtonPanel.addActionListener(e -> deleteUserButtonPanelAction());
        addHrButtonPanel.addActionListener(e -> addHrButtonPanelAction());
        addUserButton.addActionListener(e -> addUserButtonAction());
        showPasswordButton.addActionListener(e -> showPasswordButton());
        okLicenceButton.addActionListener(e -> okLicenceButtonAction());

    }

    // For Add a new HR user
    private void addUserButtonAction() {

    }

    //Shows user password in String form not in *
    private void showPasswordButton() {
        userPasswordField.setEchoChar((char) 0);
    }

    //For checking the Licence KEy
    private void okLicenceButtonAction() {

        Config config = Config.readConfigFile();
        String key = keyTextField.getText();

        if(key.equals(config.getLicencesKey()) && !config.isHaveLicence())
        {
            config.setHaveLicence(true);
            config.setNumberOfWorkerAccounts(10);
            config.setNumberOfHrAccounts(5);
            keyTextField.setVisible(false);
            okLicenceButton.setVisible(false);
            //Nakon ovoga treba ubaciti obavjestenje da je kupljena licenca
            //Treba dodati JLabel da napise da je kompanija vec kupila licencu tj uspjesno kupila licencu
            Config.rewriteConfigFile(config);
        }
    }

    // For log out
    private void logoutButtonAction() {
        this.dispose();
        new LoginScreenAdmin();
    }

    //Showing Add HR Panel on Screen
    private void addHrButtonPanelAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"addHrPanel");
    }

    //Showing Delete User Panel on Screen
    private void deleteUserButtonPanelAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"deleteUserPanel");

    }

    //Showing activate Panel on Screen
    private void activateButtonPanelAction() {

        CardLayout card = (CardLayout) (mainPanel.getLayout());
        card.show(mainPanel,"activatePanel");
    }

    private void deleteButtonAction() {

        //Checking if Admin Wants to Delete User
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

                String line = null, tempLine;

                while ((tempLine = inputStream.readLine()) != null)
                    line = tempLine;

                inputStream.close();

                String datas[] = line.split(",");

                worker=new Worker(datas);

                boolean active = worker.isActive();

                if(active)
                {
                    active = false;
                    worker.setActive(false); //Radnik vise nije aktivan, deaktiviran mu je korisnicki nalog
                }




            } catch (Exception exception) {
                LOGGER.warning(exception.fillInStackTrace().toString());
            }


            try{
                FileOutputStream stream = new FileOutputStream(path);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));

                writer.write(worker.toString()); //Nakon deaktiviranja vrsimo ponovni upis u fajl
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

                String line = null, tempLine;

                while ((tempLine = inputStream.readLine()) != null)
                    line = tempLine;

                inputStream.close();

                String datas[] = line.split(",");

                humanResourceWorker=new HumanResourceWorker(datas);

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
    private void searchButtonAction(){

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
                //userTable.add(); //Dodavanje korisnika u tabelu za prikaz
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
                //userTable.add(); //Dodavanje korisnika u tabelu za prikaz
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }



    }
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

}
