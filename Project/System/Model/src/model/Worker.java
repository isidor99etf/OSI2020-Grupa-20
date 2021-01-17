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

    public static Worker getDataFromFile(String userName) {
        try {
            FileInputStream stream = new FileInputStream(FilePaths.WORKER_ACCOUNTS + userName);
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