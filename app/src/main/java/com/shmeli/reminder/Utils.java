package com.shmeli.reminder;

import java.text.SimpleDateFormat;

/**
 * Created by Serghei Ostrovschi on 9/19/17.
 */

public class Utils {

    public static String getDate(long date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

        return dateFormat.format(date);
    }

    public static String getFullDate(long date) {

        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

        return fullDateFormat.format(date);
    }

    public static String getTime(long time) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        return timeFormat.format(time);
    }
}
