package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame{
    private String[] statusTypes = {"Start", "Go on break","End the day"};

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPasswordField pinField;
    private JComboBox statusBox ;
    private JButton okButton;
    private JLabel humanIcon;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu help = new JMenu("help");
    private  JMenuItem contactInfo = new JMenuItem("Contact Info");
    private JPanel workTimePanel;
    private JPanel companyInfoPanel;
    private JPanel personalInfoPanel;
    private JPanel backPanel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel dateOfBirthLabel;
    private JLabel addressLabel;
    private JLabel workPlaceLabel;
    private JLabel sectorLAbel;
    private JLabel userNameLabel;
    private JPanel leftPanel;


    public MainScreen() {
        super("Work Time App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        help.add(contactInfo);
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        contactInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contactInfoAction();
            }
        });


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                okButtonAction();
            }
        });
    }

    //action performed when OK button pressed
    private void okButtonAction() {
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                                                            "Admin:\n email: admin@comName.com \n phone:0123456789");

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        statusBox = new JComboBox(statusTypes);
    }
}
