package model;

import constants.FilePaths;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Company {

    private String name;
    private String address;
    private String city;
    private String country;
    private ArrayList<String> phones;
    private ArrayList<String> emails;
    private ArrayList<Employee> employees; // maybe

    //private boolean haveLicence; //Signalizira da li kompanija vec ima licencu, Config fajl

    // Prointer d.o.o,Mile Dodika 55A,Banja Luka,Republika Srpska,3,066/546-546,065/432-314,066/543-897,2,mile@gmail.com,milinsin@gmail.com

    public Company() { }



    public String getName() { return name; }

    public String getAddress() { return address; }

    public String getCity() { return city; }

    public String getCountry() { return country; }

    public int getNumberOfEmployees() { return employees.size(); }

    public ArrayList<String> getPhones() { return phones; }

    public ArrayList<String> getEmails() { return emails; }

    public ArrayList<Employee> getEmployees() { return employees; }

    public void setName(String name) { this.name = name; }

    public void setAddress(String address) { this.address = address; }

    public void setCity(String city) { this.city = city; }

    public void setCountry(String country) { this.country = country; }

    public void addPhone(String phone) { this.phones.add(phone); }

    public void addEmail(String email) { this.emails.add(email); }

    public void addEmployee(Employee employee) { this.employees.add(employee); }



}
