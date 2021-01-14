package constants;

import java.io.File;

public interface FilePaths {

    String DATA_FOLDER = "." + File.separator + "Data" + File.separator;

    String WORKER_REGISTER = DATA_FOLDER + "Register" + File.separator;

    String WORKER_ACCOUNTS = DATA_FOLDER + "UserAccounts" + File.separator;
    String HR_ACCOUNTS = DATA_FOLDER + "HrAccounts" + File.separator;
    String ADMIN_ACCOUNT = DATA_FOLDER + "admin";

    String CONFIG_FILE = DATA_FOLDER + "config";
}
