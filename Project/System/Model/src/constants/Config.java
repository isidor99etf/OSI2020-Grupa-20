package constants;

import java.io.*;
import java.util.logging.Logger;

public class Config {

    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

    /*
        All data are separated by comma, and all are in one row
        FORMAT:
            number_of_hr_without_licence
            number_of_worker_without_licence
            number_of_logins_with_one_password

        if licence is -1, then licence is not presented

     */

    private int numberOfWorkerAccounts;
    private int numberOfHrAccounts;
    private final int numberOfLogins;
    private boolean haveLicence; //Signalizira da li ima licencu
    private String licencesKey; //Kljuc za licencu

    private Config(int numberOfWorkerAccounts, int numberOfHrAccounts, int numberOfLogins, boolean haveLicence,String licencesKey) {
        this.numberOfWorkerAccounts = numberOfWorkerAccounts;
        this.numberOfHrAccounts = numberOfHrAccounts;
        this.numberOfLogins = numberOfLogins;
        this.haveLicence = haveLicence;
        this.licencesKey = licencesKey;
    }

    public int getNumberOfHrAccounts() { return numberOfHrAccounts; }

    public int getNumberOfWorkerAccounts() { return numberOfWorkerAccounts; }

    public int getNumberOfLogins() { return numberOfLogins; }

    public boolean isHaveLicence() {
        return haveLicence;
    }

    public void setHaveLicence(boolean haveLicence) {
        this.haveLicence = haveLicence;
    }

    public String getLicencesKey() {
        return licencesKey;
    }

    public void setLicencesKey(String licencesKey) {
        this.licencesKey = licencesKey;
    }

    public void setNumberOfHrAccounts(int numberOfHrAccounts) {
        this.numberOfHrAccounts = numberOfHrAccounts;
    }

    public void setNumberOfWorkerAccounts(int numberOfWorkerAccounts) {
        this.numberOfWorkerAccounts = numberOfWorkerAccounts;
    }

    public static Config readConfigFile() {

        try {
            FileInputStream stream = new FileInputStream(FilePaths.CONFIG_FILE);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(stream));

            String line = inputStream.readLine();

            inputStream.close();

            if (line != null) {
                String[] data = line.split(",");

                return new Config(
                        Integer.parseInt(data[0]),
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Boolean.parseBoolean(data[3]),
                        data[4]
                );
            }
        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    public static void writeConfigFile(Config config) {
        try {
            FileOutputStream stream = new FileOutputStream(FilePaths.CONFIG_FILE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
            writer.write(config.toString());
            writer.flush();
            writer.close();
        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }

    @Override
    public String toString() {
        return numberOfWorkerAccounts + "," + numberOfHrAccounts + ","
                + numberOfLogins + "," + haveLicence + "," + licencesKey;
    }
}
