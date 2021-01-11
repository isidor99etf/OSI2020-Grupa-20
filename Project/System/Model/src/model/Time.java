package model;

import constants.WorkTime;

public class Time {

    private final int hour;
    private final int minute;
    private final Date date;
    private final int type;

    public Time(int hour, int minute, Date date, String type) {
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.type = WorkTime.resolveTypeFromString(type);
    }

    public Time(int hour, int minute, Date date, int type) {
        this.hour = hour;
        this.minute = minute;
        this.date = date;
        this.type = type;
    }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public Date getDate() { return date; }

    public String getFormattedWorkTime() {
        return hour + ":" + minute + " " + date.getFormattedDate() + " " + WorkTime.resolveType(type);
    }

    public int getType() { return type; }

    @Override
    public String toString() {
        return hour + "," + minute + "," + date + "," + type;
    }
}