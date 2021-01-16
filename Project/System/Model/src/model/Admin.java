package model;

import constants.FilePaths;

import java.io.*;
import java.util.logging.Logger;

public class Admin extends Employee {

    private static final Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public Admin(String userName, String password) {
        super(userName, password);
    }

    public Admin(String userName, String password, String email, String phone, int numberOfLogins) {
        super(userName, password, email, phone, numberOfLogins);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Admin)) return false;

        Admin other = (Admin) obj;

        return other.userName.equals(this.userName) && other.password.equals(this.password);
    }

    @Override
    public String toString() {
        return "model.Admin " + super.toString();
    }

    public static Admin getDataFromFile() {
        try {
            FileInputStream stream = new FileInputStream(FilePaths.ADMIN_ACCOUNT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = reader.readLine();

            reader.close();

            if (line != null) {

                String[] data = line.split(",");

                return new Admin(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    public static void updateFile(Admin admin) {
        try {
            FileWriter fileWriter = new FileWriter(FilePaths.ADMIN_ACCOUNT);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(admin.getUserName());
            writer.write(",");
            writer.write(admin.getPassword());
            writer.write(",");
            writer.write(String.valueOf(admin.getNumberOfLogins()));

            writer.flush();
            writer.close();

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }
}