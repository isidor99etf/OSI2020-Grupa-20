package gui;

import model.Worker;

import javax.swing.*;

public class UserDetails extends JFrame{
    private JPanel backPanel;
    private JPanel personalInfoPanel;
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;

    public UserDetails(Worker worker)  {
        // JFrame set up
        super("User Details");
        this.setContentPane(backPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        // Setting the labels
        nameLabel.setText( worker.getFirstName() );
        surnameLabel.setText( worker.getSurname() );
        dateOfBirthLabel.setText( worker.getDateOfBirth() );
        addressLabel.setText( worker.getAddress() );
        phoneLabel.setText( worker.getPhone() );
        emailLabel.setText( worker.getEmail() );
        workPlaceLabel.setText( worker.getWorkPlace() );
        sectorLabel.setText( worker.getSector() );
        userNameLabel.setText( worker.getUserName() );

        this.pack();
    }
}
