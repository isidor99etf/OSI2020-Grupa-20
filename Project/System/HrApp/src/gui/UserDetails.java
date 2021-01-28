package gui;

import constants.WorkTime;
import model.Time;
import model.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDetails extends JFrame{
    private JPanel backPanel;
    private JButton personalInfoButton;
    private JLabel userNameLabel;
    private JLabel sectorLabel;
    private JLabel workPlaceLabel;
    private JLabel addressLabel;
    private JLabel dateOfBirthLabel;
    private JLabel surnameLabel;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JButton workTimeButton;
    private JPanel personalInfoPanel;
    private JPanel mainPanel;
    private JPanel workTimePanel;
    private JComboBox sortWorkTimeBox;
    private JTable workTimeTable;

    private final String[] sortList = {"Daily","Monthly"};

    DefaultTableModel tableModelDay;
    DefaultTableModel tableModelMonth;

    Worker worker;

    public UserDetails(Worker worker)  {
        // JFrame set up
        super("User Details");
        this.setContentPane(backPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.worker = worker;

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

        String[] tableColumnsDay = {"Time", "Date", "Status"};
        String[] tableColumnsMonth = {"Month", "Time Worked"};

        tableModelDay = new DefaultTableModel(null,tableColumnsDay);
        tableModelMonth = new DefaultTableModel(null, tableColumnsMonth);

        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        personalInfoButton.addActionListener( e -> showPersonalInfo());
        workTimeButton.addActionListener(e -> showWorkTime());
        sortWorkTimeBox.addItemListener(e -> sortWorkTime());
    }

    private void sortWorkTime() {
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

    private void showWorkTime() {
        CardLayout card = (CardLayout) mainPanel.getLayout();
        card.show(mainPanel,"workTimePanel");
    }

    private void showPersonalInfo() {
        CardLayout card = (CardLayout) mainPanel.getLayout();
        card.show(mainPanel,"personalInfoPanel");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        sortWorkTimeBox = new JComboBox(sortList);
        sortWorkTimeBox.setSelectedIndex(-1);
    }
}
