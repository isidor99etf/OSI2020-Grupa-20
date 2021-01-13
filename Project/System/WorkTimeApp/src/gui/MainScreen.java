package gui;

import constants.FilePaths;
import constants.Texts;
import constants.WorkTime;
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

        for(String fill : WorkTime.getStatusTypes()){
            statusBox.addItem(fill);
        }
        LOGGER.info(statusBox.getItemCount() + "");

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

    //action performed when OK button pressed
    private void okButtonAction() {

        if (pinField.getPassword().length > 0) {
            int pin = Integer.parseInt(new String(pinField.getPassword()));
            if (checkWorker(pin)) {

                Time time = getLastTime(pin);

                int status = WorkTime.resolveTypeFromString(WorkTime.getStatusTypes()[statusBox.getSelectedIndex()]);

                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();

                Date date = new Date(currentDate.getDayOfMonth(), currentDate.getMonthValue(), currentDate.getYear());
                Time newTime = new Time(currentTime.getHour(), currentTime.getMinute(), date, status);

                if (time != null) {

                    LOGGER.info(time.getFormattedWorkTime());

                    if (checkTime(time, newTime))
                        writeNewTime(newTime, pin);
                    else {
                        messageLabel.setText(Texts.MESSAGE_WRONG_WORK_STATUS);
                        messageLabel.setVisible(true);
                    }

                } else
                    writeNewTime(newTime, pin);
            } else {
                messageLabel.setText(Texts.MESSAGE_WRONG_PIN);
                messageLabel.setVisible(true);
            }
        } else {
            messageLabel.setText(Texts.MESSAGE_PIN_FIELD_EMPTY);
            messageLabel.setVisible(true);
        }
    }

    private void contactInfoAction() {
        JOptionPane.showMessageDialog(contactInfo,"Contact Info\n"+
                                                            "Admin:\n email: admin@comName.com \n phone:0123456789");
    }

   /* private void createUIComponents() {
        statusBox = new JComboBox(WorkTime.getStatusTypes());
        LOGGER.info(statusBox.getItemCount() + "");
    }*/

    private static boolean checkWorker(int pin) {
        String path = FilePaths.WORKER_REGISTER + pin;
        return new File(path).exists();
    }

    private static Time getLastTime(int pin) {

        String path = FilePaths.WORKER_REGISTER + pin;

        try {
            FileInputStream stream = new FileInputStream(path);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = null, tempLine;

            while ((tempLine = inputStream.readLine()) != null)
                line = tempLine;

            if (line != null) {
                String[] data = line.split(",");

                Date date = new Date(data[2], data[3], data[4]);

                return new Time(Integer.parseInt(data[0]), Integer.parseInt(data[1]), date, Integer.parseInt(data[5]));
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    private static void writeNewTime(Time time, int pin) {
        String path = FilePaths.WORKER_REGISTER + pin;

        try {

            FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(time.toString());
            writer.write("\n");
            writer.flush();
            writer.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }

    private static boolean checkTime(Time lastTime, Time newTime) {

        if (lastTime.getType() == WorkTime.TYPE_END && newTime.getType() == WorkTime.TYPE_START) return true;

        if (lastTime.getType() == WorkTime.TYPE_START && (newTime.getType() == WorkTime.TYPE_PAUSE_START || newTime.getType() == WorkTime.TYPE_END)) return true;

        if (lastTime.getType() == WorkTime.TYPE_PAUSE_START && newTime.getType() == WorkTime.TYPE_PAUSE_END) return true;

        return lastTime.getType() == WorkTime.TYPE_PAUSE_END && (newTime.getType() == WorkTime.TYPE_PAUSE_START || newTime.getType() == WorkTime.TYPE_END);
    }
}
