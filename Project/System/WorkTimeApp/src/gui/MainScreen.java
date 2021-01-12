package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame{
    private final String[] statusTypes = {"Start the day", "Go on break","Come back from break","End the day"};

    private JPanel mainPanel;
    private JPanel backPanel;
    private JPanel leftPanel;
    private JButton okButton;
    private JLabel humanIcon;
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");
    //Check USer details
    private JPasswordField pinField;
    private JComboBox statusBox ;


    public MainScreen() {
        //Setting up JFrame
        super("Work Time App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();
        this.setVisible(true);

        //Add Menu Bar
        JMenu help = new JMenu("help");
        help.add(contactInfo);
        JMenuBar menuBar = new JMenuBar();
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
