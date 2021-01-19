package gui;

import model.Admin;
import model.Company;
import model.Worker;
import user_app.Main;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;

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
    private JList workTimeList;
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


    private final String[] sortList = {"Day","Monthly"};
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private final Worker worker;

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

        contactInfo.addActionListener(e -> contactInfoAction());
        personalInfoButton.addActionListener(e -> personalInfoButtonAction());
        workTimeButton.addActionListener(e -> workTimeButtonAction());
        companyInfoButton.addActionListener(e -> companyInfoButtonAction());
        logoutButton.addActionListener(e -> logoutButtonAction());
        reportButton.addActionListener(e -> reportButtonAction());

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

        // Setting the Labels

        /*
        String text;
        String[] content;
        String companyName = null;
        String companyAdress = null;
        String companyCity = null;
        String companyCountry = null;
        int numberOfEmails;
        int numberOfPhones;
        ArrayList<String> phones = new ArrayList<>();
        ArrayList<String> emails = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(FilePaths.COMPANY_INFO_FILE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            text = bufferedReader.readLine();
            content = text.split(",");
            companyName = content[0];
            companyAdress = content[1];
            companyCity = content[2];
            companyCountry = content[3];

            numberOfPhones = Integer.parseInt(content[4]);
            phones.addAll(Arrays.asList(content).subList(5, numberOfPhones + 5));

            numberOfEmails = Integer.parseInt(content[5 + numberOfPhones]);
            emails.addAll(Arrays.asList(content).subList(6 + numberOfPhones, numberOfEmails + 6 + numberOfPhones));


            bufferedReader.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }*/

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

    //Prints a report
    private void reportButtonAction() {
    }

    // Show Contact Info
    private void contactInfoAction() {
        Admin admin = Admin.getDataFromFile();
        String contactInfoMessage = "";
        if (admin != null)
            contactInfoMessage =
                    String.format("Contact Info:\nAdmin email: %s\nAdmin phone: %s", admin.getEmail(), admin.getPhone());

        JOptionPane.showMessageDialog(contactInfo,contactInfoMessage);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortWorkTimeBox = new JComboBox(sortList);
    }
}
