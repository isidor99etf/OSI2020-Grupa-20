package model;

import constants.FilePaths;

import java.io.*;
import java.util.logging.Logger;

public class Worker extends Employee {

    private static final Logger LOGGER = Logger.getLogger(Worker.class.getName());

    public Worker(String userName, String password) {
        super(userName, password);
    }

    public Worker(String userName, String password, int numberOfLogins) {
        super(userName, password, numberOfLogins);
    }

    public Worker(String[] data) {
        super(data);
    }

    public Worker(String name, String surname, String dateOfBirth, String address, String phone, String email, String workPlace, String sector, String userName, String password) {
        super(name, surname, dateOfBirth, address, phone, email, workPlace, sector, userName, password);
    }

    public static Worker getDataFromFile(String userName) {
        return getDataFromFile(new File(FilePaths.WORKER_ACCOUNTS + userName));
    }

    public static Worker getDataFromFile(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();

            stream.close();
            reader.close();

            if (line != null) {
                String[] data = line.split(",");
                return new Worker(data);
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
        return null;
    }

    public static void updateFile(Worker worker) {
        try {
            FileWriter fileWriter = new FileWriter(FilePaths.WORKER_ACCOUNTS + worker.getUserName());
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