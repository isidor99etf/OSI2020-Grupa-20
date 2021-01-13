package constants;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Config {

    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

    /*
        All data are separated by comma, and all are in one row
        FORMAT:
            licence
            number_of_hr_without_licence
            number_of_worker_without_licence
            number_of_logins_with_one_password

        if licence is -1, then licence is not presented

     */

    private final int licence;
    private final int numberOfHrAccounts;
    private final int numberOfWorkerAccounts;
    private final int numberOfLogins;

    private Config(int licence, int numberOfHrAccounts, int numberOfWorkerAccounts, int numberOfLogins) {
        this.licence = licence;
        this.numberOfHrAccounts = numberOfHrAccounts;
        this.numberOfWorkerAccounts = numberOfWorkerAccounts;
        this.numberOfLogins = numberOfLogins;
    }

    public int getLicence() { return licence; }

    public int getNumberOfHrAccounts() { return numberOfHrAccounts; }

    public int getNumberOfWorkerAccounts() { return numberOfWorkerAccounts; }

    public int getNumberOfLogins() { return numberOfLogins; }

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
                        Integer.parseInt(data[3])
                );
            }
        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }
}
