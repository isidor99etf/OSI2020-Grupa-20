package model;

import constants.FilePaths;
import constants.WorkTime;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Time {

    private static final Logger LOGGER = Logger.getLogger(Time.class.getName());

    private final int hour;
    private final int minute;
    private final Date date;
    private final int type;

    public Time(int hour, int minute, Date date, String type) {
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.type = WorkTime.resolveTypeFromString(type);
    }

    public Time(int hour, int minute, Date date, int type) {
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.type = type;
    }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public Date getDate() { return date; }

    public String getFormattedWorkTime() {
        return hour + ":" + minute + " " + date.getFormattedDate() + " " + WorkTime.resolveType(type);
    }

    public int getType() { return type; }

    @Override
    public String toString() {
        return hour + "," + minute + "," + date + "," + type;
    }

    public static Time getLastTime(int pin) {

        String path = FilePaths.WORKER_REGISTER + pin;

        try {
            FileInputStream stream = new FileInputStream(path);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = null, tempLine;

            while ((tempLine = inputStream.readLine()) != null)
                line = tempLine;

            inputStream.close();

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

    public static ArrayList<Time> getAllWorkTimeInfo(int pin)
    {
        ArrayList<Time> times = new ArrayList<>();
        String path = FilePaths.WORKER_REGISTER + pin;

        try {
            FileInputStream stream = new FileInputStream(path);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line , tempLine;

            while ((tempLine = inputStream.readLine()) != null)
            {
                line = tempLine;

            if (line != null) {
                String[] data = line.split(",");

                Date date = new Date(data[2], data[3], data[4]);
                Time time = new Time(Integer.parseInt(data[0]),Integer.parseInt(data[1]),date,Integer.parseInt(data[5]));
                times.add(time);
            }

            }
            inputStream.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return times;
    }

    public static void writeNewTime(Time time, int pin) {
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
}