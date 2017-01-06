package com.romao.nhlspider.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by rpiontkovsky on 12/27/2016.
 */

public class DateUtil {

    private static final DateFormat shortDateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
    private static final DateTimeZone defaultTimeZone = DateTimeZone.forID("America/New_York");

    public static String toShortString(DateTime dateTime) {
        if (dateTime == null) {
            throw new AssertionError("Datetime should not be null");
        }

        shortDateFormat.setTimeZone(defaultTimeZone.toTimeZone());
        String result = shortDateFormat.format(dateTime.toDate());
        return result;
    }

    public static DateTime toStartOfDay(DateTime dateTime) {
        if (dateTime == null) {
            throw new AssertionError("Datetime should not be null");
        }

        shortDateFormat.setTimeZone(defaultTimeZone.toTimeZone());
        return dateTime.withZone(defaultTimeZone).withTime(0, 0, 0, 0);
    }

    public static DateTimeZone getDefaultTimeZone() {
        return defaultTimeZone;
    }

    public static int daysBetween(DateTime d1, DateTime d2) {
        long millisInOneDay = (24 * 60 * 60 * 1000);

        DateTime dt1 = d1.withTime(0, 0, 0, 0);
        DateTime dt2 = d2.withTime(0, 0, 0, 0);

        return  (int) ((dt2.getMillis() - dt1.getMillis()) / millisInOneDay);
    }
}
