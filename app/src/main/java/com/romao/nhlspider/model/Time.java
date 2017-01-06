package com.romao.nhlspider.model;

/**
 * Created by rpiontkovsky on 1/6/2017.
 */

public class Time {

    private final int hour;
    private final int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }

    public static Time valueOf(String timeStr) {
        if (timeStr == null || timeStr.indexOf(":") < 0) {
            throw new AssertionError("Incorrect time format = " + timeStr);
        }

        String[] parts = timeStr.split(":");
        if (parts.length != 2) {
            throw new AssertionError("Incorrect time format = " + timeStr);
        }

        try {
            int hh = Integer.parseInt(parts[0]);
            int mm = Integer.parseInt(parts[1]);
            return new Time(hh, mm);
        } catch (NumberFormatException ex) {
            throw new AssertionError("Incorrect time format = " + timeStr);
        }
    }
}
