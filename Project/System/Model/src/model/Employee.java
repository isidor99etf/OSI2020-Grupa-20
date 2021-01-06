package model;

import java.util.ArrayList;

public abstract class Employee {

    public Employee() {
    }

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
    protected String id;
    protected String workPlace;
    protected String sector;
    protected int numberOfLogins;
    protected ArrayList<Time> workTime;

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

    public String getId() { return id; }

    public ArrayList<Time> getWorkTime() { return workTime; }

    public void setPIN(int PIN) { this.PIN = PIN; }

    public void setName(String name) { this.name = name; }

    public void setSurname(String surname) { this.surname = surname; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setPassword(String password) { this.password = password; }

    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public void setAddress(String address) { this.address = address; }

    public void setActive(boolean active) { this.active = active; }

    public void setId(String id) { this.id = id; }

    public void setWorkPlace(String workPlace) { this.workPlace = workPlace; }

    public void setSector(String sector) { this.sector = sector; }

    public void setNumberOfLogins(int numberOfLogins) { this.numberOfLogins = numberOfLogins; }

    @Override
    public String toString() {
        return id + ", " + name + " " + surname + ", " + dateOfBirth + ", " + address + ", " + email + ", " + phone;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + getName().hashCode();
    }
}
