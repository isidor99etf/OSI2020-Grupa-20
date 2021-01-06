package model;

public class Time {

    public Time() { }

    public Time(int hour, int minute, String date) {
        this.hour = hour;
        this.minute = minute;
        this.date = date;
    }

    private int hour;
    private int minute;
    private String date;

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public String getDate() { return date; }

    public String getFormattedWorkTime() {
        return hour + ":" + minute + " " + date;
    }
}