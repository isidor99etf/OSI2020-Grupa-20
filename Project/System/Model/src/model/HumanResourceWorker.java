package model;

public class HumanResourceWorker extends Employee {

    public HumanResourceWorker(String userName, String password) {
        super(userName, password);
    }

    public HumanResourceWorker(String userName, String password, int numberOfLogins) {
        super(userName, password, numberOfLogins);
    }

    public HumanResourceWorker(String[] data) {
        super(data);
    }
}