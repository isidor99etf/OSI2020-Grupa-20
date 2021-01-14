package model;

public class Admin extends Employee {

    public Admin(String userName, String password) {
        super(userName, password);
    }

    public Admin(String userName, String password, int numberOfLogins) {
        super(userName, password, numberOfLogins);
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
}