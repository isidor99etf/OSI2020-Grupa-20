package gui;

import constants.FilePaths;
import constants.Texts;
import constants.WorkTime;
import model.Admin;
import model.Company;
import model.Date;
import model.Time;
import work_time_app.Main;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

public class MainScreen extends JFrame {

    // private final String[] statusTypes = {"Start the day", "Go on break","Come back from break","End the day"};

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private JPanel mainPanel;
    private JPanel backPanel;
    private JPanel leftPanel;
    private JButton okButton;
    private JLabel humanIcon;
    private final JMenuItem contactInfo = new JMenuItem("Contact Info");
    private JPasswordField pinField;
    private JComboBox statusBox;
    private JLabel messageLabel;


    public MainScreen() {

        //Setting up JFrame
        super("Work Time App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(backPanel);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        for(String fill : WorkTime.getStatusTypes())
            statusBox.addItem(fill);

        // default choice is blank
        statusBox.setSelectedIndex(-1);

        //Add Menu Bar
        JMenu help = new JMenu("Help");
        help.add(contactInfo);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        messageLabel.setVisible(false);

        contactInfo.addActionListener(e -> contactInfoAction());
        okButton.addActionListener(e -> okButtonAction());
    }

    // action performed when OK button pressed
    private void okButtonAction() {

        // clear field
        labelMessage("", false);

        if (pinField.getPassword().length > 0) {
            int pin = Integer.parseInt(new String(pinField.getPassword()));
            if (checkWorker(pin)) {

                Time time = Time.getLastTime(pin);

                int status = WorkTime.resolveTypeFromString(WorkTime.getStatusTypes()[statusBox.getSelectedIndex()]);

                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();

                Date date = new Date(currentDate.getDayOfMonth(), currentDate.getMonthValue(), currentDate.getYear());
                Time newTime = new Time(currentTime.getHour(), currentTime.getMinute(), date, status);

                if (time != null)
                    if (checkTime(time, newTime))
                        writeTime(newTime, pin);
                    else
                        labelMessage(Texts.MESSAGE_WRONG_WORK_STATUS, true);
                else
                    writeTime(newTime, pin);


            } else
                labelMessage(Texts.MESSAGE_WRONG_PIN, true);

        } else
            labelMessage(Texts.MESSAGE_PIN_FIELD_EMPTY, true);
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo, Company.getContactInfo());
    }

    private static boolean checkWorker(int pin) {
        String path = FilePaths.WORKER_REGISTER + pin;
        return new File(path).exists();
    }

    private static boolean checkTime(Time lastTime, Time newTime) {

        if (lastTime.getType() == WorkTime.TYPE_END && newTime.getType() == WorkTime.TYPE_START) return true;

        if (lastTime.getType() == WorkTime.TYPE_START && (newTime.getType() == WorkTime.TYPE_PAUSE_START || newTime.getType() == WorkTime.TYPE_END)) return true;

        if (lastTime.getType() == WorkTime.TYPE_PAUSE_START && newTime.getType() == WorkTime.TYPE_PAUSE_END) return true;

        return lastTime.getType() == WorkTime.TYPE_PAUSE_END && (newTime.getType() == WorkTime.TYPE_PAUSE_START || newTime.getType() == WorkTime.TYPE_END);
    }

    private void labelMessage(String message, boolean visibility) {
        messageLabel.setText(message);
        messageLabel.setVisible(visibility);
        this.pack();
    }

    private void writeTime(Time time, int pin) {
        // reset fields
        pinField.setText("");
        statusBox.setSelectedIndex(-1);

        Time.writeNewTime(time, pin);

        // show message
        labelMessage(Texts.MESSAGE_SUCCESS, true);
    }
}
