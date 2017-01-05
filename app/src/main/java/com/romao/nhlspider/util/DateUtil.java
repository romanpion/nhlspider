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

    public static String toShortString(DateTime dateTime) {
        if (dateTime == null) {
            throw new AssertionError("Datetime should not be null");
        }

        DateTimeZone dtz = DateTimeZone.forID("America/New_York");
        shortDateFormat.setTimeZone(dtz.toTimeZone());
        String result = shortDateFormat.format(dateTime.toDate());
        return result;
    }
}
