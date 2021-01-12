package gui;

import javax.swing.*;
import java.awt.*;

public class UserDetails extends JFrame{
    private JPanel personalInfoPanel;
    private JPanel backPanel;
    //Need to be set
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;


    public UserDetails()  {
        super("User Details");
        this.setContentPane(backPanel);
        this.pack();
        this.setVisible(true);
    }
}
