package model;

import java.util.ArrayList;

public abstract class Employee {

    protected int PIN;
    protected String name;
    protected String surname;
    protected String userName;
    protected String password;
    protected String dateOfBirth;
    protected String address;
    protected String email;
    protected String phone;
    protected boolean active;
    protected String workPlace;
    protected String sector;
    protected int numberOfLogins;
    protected ArrayList<Time> workTime;

    public Employee(String[] data) {
        PIN = Integer.parseInt(data[0]);
        name = data[1];
        surname = data[2];
        userName = data[3];
        password = data[4];
        dateOfBirth = data[5];
        address = data[6];
        email = data[7];
        phone = data[8];
        active = Boolean.parseBoolean(data[9]);
        workPlace = data[10];
        sector = data[11];
        numberOfLogins = Integer.parseInt(data[12]);
    }

    public Employee() {
        this.numberOfLogins = 0;
    }

    public Employee(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Employee(String userName, String password, int numberOfLogins) {
        this(userName, password);
        this.numberOfLogins = numberOfLogins;
    }

    public int getPIN() { return PIN; }

    public String getName() { return name + " " + surname; }

    public String getUserName() { return userName; }

    public String getPassword() { return password; }

    public String getDateOfBirth() { return dateOfBirth; }

    public String getAddress() { return address; }

    public String getWorkPlace() { return workPlace; }

    public String getSector() { return sector; }

    public int getNumberOfLogins() { return numberOfLogins; }

    public boolean isActive() { return active; }

    public ArrayList<Time> getWorkTime() { return workTime; }

    public void setPIN(int PIN) { this.PIN = PIN; }

    public void setName(String name) { this.name = name; }

    public void setSurname(String surname) { this.surname = surname; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setPassword(String password) { this.password = password; }

    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setAddress(String address) { this.address = address; }

    public void setActive(boolean active) { this.active = active; }

    public void setWorkPlace(String workPlace) { this.workPlace = workPlace; }

    public void setSector(String sector) { this.sector = sector; }

    public void setNumberOfLogins(int numberOfLogins) { this.numberOfLogins = numberOfLogins; }

    @Override
    public String toString() {
        return
                String.format("%06d", PIN) + "," +
                        name + "," + surname + "," +
                        userName + "," + password + "," +
                        dateOfBirth + "," + address + "," +
                        email + "," + phone + "," + active + "," +
                        workPlace + "," + sector + "," + numberOfLogins;
    }

    @Override
    public int hashCode() {
        return userName.hashCode() + getName().hashCode();
    }
}
