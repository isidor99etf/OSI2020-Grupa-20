package model;

import constants.FilePaths;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class Company {

    private String name;
    private String address;
    private String city;
    private String country;
    private int numberOfWorkers;
    private int numberOfHrWorkers;
    private ArrayList<String> phones;
    private ArrayList<String> emails;
    //private ArrayList<Employee> employees; // maybe

    //private boolean haveLicence; //Signalizira da li kompanija vec ima licencu, Config fajl

    // Prointer d.o.o,Mile Dodika 55A,Banja Luka,Republika Srpska,3,066/546-546,065/432-314,066/543-897,2,mile@gmail.com,milinsin@gmail.com

    private static final Logger LOGGER = Logger.getLogger(Company.class.getName());

    public Company() { }

    public Company(String name, String address, String city, String country, int numberOfWorkers, int numberOfHrWorkers, ArrayList<String> phones, ArrayList<String> emails) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.numberOfWorkers = numberOfWorkers;
        this.numberOfHrWorkers = numberOfHrWorkers;
        this.phones = phones;
        this.emails = emails;
    }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public String getCity() { return city; }

    public String getCountry() { return country; }

    public int getNumberOfWorkers() { return numberOfWorkers; }

    public int getNumberOfHrWorkers() { return numberOfHrWorkers; }

    public ArrayList<String> getPhones() { return phones; }

    public ArrayList<String> getEmails() { return emails; }

    public String getPhonesStringFormatted() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");

        for (String phone : phones)
            builder.append(phone).append("<br/>");

        builder.append("</html>");
        return builder.toString();
    }

    public String getEmailsStringFormatted() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");

        for (String email : emails)
            builder.append(email).append("<br/>");

        builder.append("</html>");
        return builder.toString();
    }

    public void setName(String name) { this.name = name; }

    public void setAddress(String address) { this.address = address; }

    public void setCity(String city) { this.city = city; }

    public void setCountry(String country) { this.country = country; }

    public void setNumberOfWorkers(int numberOfWorkers) { this.numberOfWorkers = numberOfWorkers; }

    public void setNumberOfHrWorkers(int numberOfHrWorkers) { this.numberOfHrWorkers = numberOfHrWorkers; }

    public void addPhone(String phone) { this.phones.add(phone); }

    public void addEmail(String email) { this.emails.add(email); }

    @Override
    public String toString() {
        return name + "," +
                address + "," + city + "," + country + "," +
                numberOfWorkers + "," + numberOfHrWorkers + "," +
                phones.size() + "," + getPhonesString() +
                emails.size() + "," + getEmailsString();
    }

    public static Company getDataFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FilePaths.COMPANY_INFO_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line = reader.readLine();

            reader.close();

            if (line != null) {

                String[] data = line.split(",");

                int numberOfWorkers = Integer.parseInt(data[4]);
                int numberOfHrWorkers = Integer.parseInt(data[5]);
                int numberOfPhones = Integer.parseInt(data[6]);
                int numberOfEmails = Integer.parseInt(data[7 + numberOfPhones]);

                ArrayList<String> phones = new ArrayList<>(numberOfPhones);
                ArrayList<String> emails = new ArrayList<>(numberOfEmails);

                phones.addAll(Arrays.asList(data).subList(7, 7 + numberOfPhones));
                emails.addAll(Arrays.asList(data).subList(7 + (numberOfPhones + 1), 7 + (numberOfPhones + 1) + numberOfEmails));

                return new Company(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        numberOfWorkers,
                        numberOfHrWorkers,
                        phones,
                        emails
                );
            }

        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }

        return null;
    }

    public static void writeData(Company company) {
        try {
            FileWriter fileWriter = new FileWriter(FilePaths.COMPANY_INFO_FILE);
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(company.toString());
            writer.flush();
            writer.close();
        } catch (Exception exception) {
            LOGGER.warning(exception.fillInStackTrace().toString());
        }
    }

    private String getPhonesString() {
        StringBuilder builder = new StringBuilder();

        for (String phone : phones)
            builder.append(phone).append(",");

        return builder.toString();
    }

    private String getEmailsString() {
        StringBuilder builder = new StringBuilder();

        for (String email : emails)
            builder.append(email).append(",");

        return builder.toString();
    }
}
