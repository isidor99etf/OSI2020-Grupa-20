package constants;

import java.io.File;

public interface FilePaths {

    String DATA_FOLDER = "." + File.separator + "Data" + File.separator;

    String WORKER_REGISTER = DATA_FOLDER + "Register" + File.separator;

    String WORKER_ACCOUNTS = DATA_FOLDER + "user_accounts.csv";
    String HR_ACCOUNTS = DATA_FOLDER + "hr_accounts.csv";
    String ADMIN_ACCOUNT = DATA_FOLDER + "admin";

    String CONFIG_FILE = DATA_FOLDER + "config";
}
