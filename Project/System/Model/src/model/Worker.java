package model;

public class Worker extends Employee {

    public Worker(String userName, String password) {
        super(userName, password);
    }

    public Worker(String userName, String password, int numberOfLogins) {
        super(userName, password, numberOfLogins);
    }

    public Worker(String[] data) {
        super(data);
    }
}