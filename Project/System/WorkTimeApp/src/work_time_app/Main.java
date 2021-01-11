package work_time_app;

import constants.FilePaths;
import constants.WorkTime;
import model.Date;
import model.Time;

import java.io.*;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        LOGGER.info(String.valueOf(checkWorker(546323)));
        LOGGER.info(String.valueOf(checkWorker(456234)));

        // test case
        if (checkWorker(546323)) {

            Time time = getLastTime(546323);

            Date date = new Date(21, 5, 2021);
            Time newTime = new Time(12, 45, date, WorkTime.TYPE_START);

            if (time != null) {

                LOGGER.info(time.getFormattedWorkTime());

                if (checkTime(time, newTime)) {
                    writeNewTime(newTime, 546323);
                }
            } else
                writeNewTime(newTime, 546323);

        }
    }

    private static boolean checkWorker(int pin) {
        String path = FilePaths.WORKER_REGISTER + pin;
        return new File(path).exists();
    }

    private static Time getLastTime(int pin) {

        String path = FilePaths.WORKER_REGISTER + pin;

        try {
            FileInputStream stream = new FileInputStream(path);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = null, tempLine = null;

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
        return true;
    }
}
