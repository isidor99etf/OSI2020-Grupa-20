package model;

import constants.FilePaths;

import java.io.*;
import java.util.logging.Logger;

public class HumanResourceWorker extends Employee {

    private static final Logger LOGGER = Logger.getLogger(HumanResourceWorker.class.getName());

    public HumanResourceWorker(String userName, String password) {
        super(userName, password);
    }

    public HumanResourceWorker(String userName, String password, int numberOfLogins) {
        super(userName, password, numberOfLogins);
    }

    public HumanResourceWorker(String[] data) {
        super(data);
    }

    public HumanResourceWorker(String name, String surname, String dateOfBirth, String address, String phone, String email, String workPlace, String sector, String userName, String password) {
        super(name, surname, dateOfBirth, address, phone, email, workPlace, sector, userName, password);
    }

    public static HumanResourceWorker getDataFromFile(String userName) {
        try {
            FileInputStream stream = new FileInputStream(FilePaths.HR_ACCOUNTS + userName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();

            stream.close();
            reader.close();

            if (line != null) {
                String[] data = line.split(",");
                return new HumanResourceWorker(data);
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
        return null;
    }

    public static void updateFile(HumanResourceWorker worker) {
        try {
            FileWriter fileWriter = new FileWriter(FilePaths.HR_ACCOUNTS + worker.getUserName());
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(worker.toString());
            writer.flush();
            writer.close();

            fileWriter.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }
}