package model;

public class Date {

    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Date(String day, String month, String year) {
        this.day = Integer.parseInt(day);
        this.month = Integer.parseInt(month);
        this.year = Integer.parseInt(year);
    }

    public String getFormattedDate() {
        return day + "." + month + "." + year + ".";
    }

    @Override
    public String toString() {
        return day + "," + month + "," + year;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Date)) return false;

        Date other = (Date) obj;

        return other.day == day && other.month == month && other.year == year;
    }
}
