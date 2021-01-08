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


    public MainScreen() {
        super("Work Time App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

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
}
