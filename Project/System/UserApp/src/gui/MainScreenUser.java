package gui;

import constants.WorkTime;
import model.*;
import user_app.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MainScreenUser extends JFrame {

    // Just Panels
    private JPanel backPanel;
    private JPanel mainPanel;
    private JPanel mainPanelCard;
    private JPanel buttonPanel;
    private JPanel rightPanel;
    private JPanel personalInfoPanel;
    private JPanel companyInfoPanel;
    private JPanel loginPanel;
    private JPanel workTimePanel;
    private JPanel homePanel;

    // For Work Time stuff
    private JComboBox sortWorkTimeBox;
    private JButton reportButton;
    private JButton workTimeButton;

    // Need to set be For Personal info
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JButton personalInfoButton;

    // Need to be set for Company Info
    private JLabel companyNameLabel;
    private JLabel companyAddressLabel;
    private JLabel companyPhoneLabel;
    private JLabel companyEmailLabel;
    private JButton companyInfoButton;
    private JButton logoutButton;
    private JLabel companyCityLabel;
    private JLabel companyCountryLabel;
    private JLabel pinLabel;
    private JTable workTimeTable;


    private final String[] sortList = {"Daily","Monthly"};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private final Worker worker;

    DefaultTableModel tableModelDay;
    DefaultTableModel tableModelMonth;

    public MainScreenUser(Worker worker) {
        super("User App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.worker = worker;

        //Add Menu Bar
        JMenu help = new JMenu("help");
        help.add(contactInfo);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        String[] tableColumnsDay = {"Time", "Date", "Status"};
        String[] tableColumnsMonth = {"Month", "Time Worked"};

        tableModelDay = new DefaultTableModel(null,tableColumnsDay);
        tableModelMonth = new DefaultTableModel(null, tableColumnsMonth);

        contactInfo.addActionListener(e -> contactInfoAction());
        personalInfoButton.addActionListener(e -> personalInfoButtonAction());
        workTimeButton.addActionListener(e -> workTimeButtonAction());
        companyInfoButton.addActionListener(e -> companyInfoButtonAction());
        logoutButton.addActionListener(e -> logoutButtonAction());
        reportButton.addActionListener(e -> reportButtonAction());
        sortWorkTimeBox.addItemListener(e -> sortWorkTimeAction());
    }

    private void sortWorkTimeAction(){
        String item = (String) sortWorkTimeBox.getSelectedItem();

        ArrayList<Time> allTimes = Time.getAllWorkTimeInfo(worker.getPIN());

        if(item != null){

            if(item.equals("Daily")){

                tableModelDay.getDataVector().removeAllElements();

                for(Time time : allTimes) {
                    String[] data = time.getFormattedWorkTime().split(" ");
                    tableModelDay.addRow(new Object[]{data[0], data[1], data[2]});
                }
                workTimeTable.setModel(tableModelDay);
            }

            if (item.equals("Monthly")){

                tableModelMonth.getDataVector().removeAllElements();

                Map<String, List<Time>> data = allTimes.stream().collect(Collectors.groupingBy(t -> t.getDate().getMonthYear()));

                for (String month : data.keySet()) {
                    float worked = 0.0F;
                    for (Time time : data.get(month)) {
                        if (WorkTime.resolveType(time.getType()).equals(WorkTime.TYPE_STRING_START))
                            worked -= (time.getHour() + (float) time.getMinute() / 60.0);

                        else if (WorkTime.resolveType(time.getType()).equals(WorkTime.TYPE_STRING_END))
                            worked +=  (time.getHour() + (float) time.getMinute() / 60.0);
                    }

                    int hours = (int) worked;
                    int minutes = (int) (worked * 60) % 60 ;
                    String[] monthData = month.split("\\.");

                    tableModelMonth.addRow(new Object[] {new DateFormatSymbols().getMonths()[Integer.parseInt(monthData[0])-1] + " " + monthData[1],
                            hours + ":" + minutes});
                }
                workTimeTable.setModel(tableModelMonth);
            }
        }
    }

    // Show and sets Personal Info
    private void personalInfoButtonAction() {

        // Setting the Labels
        nameLabel.setText( worker.getFirstName() );
        surnameLabel.setText( worker.getSurname() );
        dateOfBirthLabel.setText( worker.getDateOfBirth() );
        addressLabel.setText( worker.getAddress() );
        phoneLabel.setText( worker.getPhone() );
        emailLabel.setText( worker.getEmail() );
        workPlaceLabel.setText( worker.getWorkPlace() );
        sectorLabel.setText( worker.getSector() );
        userNameLabel.setText( worker.getUserName() );
        pinLabel.setText( String.valueOf(worker.getPIN()) );

        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"personalInfoPanel");

        this.pack();
    }

    //Show Work Time Panel
    private void workTimeButtonAction() {

        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"workTimePanel");
    }

    // Show and sets Company Info
    private void companyInfoButtonAction() {

        Company company = Company.getDataFromFile();

        if (company != null) {
            companyNameLabel.setText(company.getName());
            companyAddressLabel.setText(company.getAddress());
            companyCityLabel.setText(company.getCity());
            companyCountryLabel.setText(company.getCountry());
            companyPhoneLabel.setText(company.getPhonesStringFormatted());
            companyEmailLabel.setText(company.getEmailsStringFormatted());
        }

        CardLayout card = (CardLayout)(mainPanel.getLayout());
        card.show(mainPanel,"companyInfoPanel");

        this.pack();
    }

    // go back to login
    private void logoutButtonAction() {
        this.dispose();
        new LoginScreenUser();
    }

    // Prints a report
    private void reportButtonAction() {
        ArrayList<Time> allTimes = Time.getAllWorkTimeInfo(worker.getPIN());
        for (Time t : allTimes)
            System.out.println(t.getFormattedWorkTime());

        // create csv file
        downloadReport(allTimes);
    }

    // Show Contact Info
    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo, Company.getContactInfo());
    }

    private void createUIComponents() {
        sortWorkTimeBox = new JComboBox(sortList);
        sortWorkTimeBox.setSelectedIndex(-1);
    }

    // potrebno napraviti dugme na koje ce se ovo aktivirati
    private void downloadReport(ArrayList<Time> times) {

        Map<String, List<Time>> data = times.stream().collect(Collectors.groupingBy(t -> t.getDate().getFormattedDate()));

        // System.out.println("Keys " + data.keySet().size());

        JFileChooser chooser = new JFileChooser();
        int retVal = chooser.showDialog(MainScreenUser.this, "Izaberi");
        if (retVal == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                for (String d : data.keySet()) {
                    writer.write(d);
                    writer.write("\n");
                    for (Time t : data.get(d)) {
                        writer.write(t.getFormattedTimeForFile());
                        writer.write("\n");
                    }
                    writer.flush();
                }

                writer.close();
            } catch (Exception ex) {
                LOGGER.warning(ex.fillInStackTrace().toString());
            }
        }
    }
}
