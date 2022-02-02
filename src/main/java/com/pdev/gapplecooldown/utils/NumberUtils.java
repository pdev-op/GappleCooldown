package com.pdev.gapplecooldown.utils;

public class NumberUtils {
    static final int SECOND = 1000;
    static final int MINUTE = SECOND * 60;
    static final int HOUR = MINUTE * 60;
    static final int DAY = HOUR * 24;
    static final int WEEK = DAY * 7;

    public static String getTimeFormatted(Long remaining) {
        int weeks = (int) (remaining / WEEK);
        int days = (int) ((remaining % WEEK) / DAY);
        int hours = (int) ((remaining % DAY) / HOUR);
        int minutes = (int) ((remaining % HOUR) / MINUTE);
        int seconds = (int) ((remaining % MINUTE) / SECOND);

        return "" + (weeks > 0 ? weeks + " week(s), " : "") + (days > 0 ? days + " days, " : "")
                + (hours > 0 ? hours + " hours, " : "") + (minutes > 0 ? minutes + "m " : "") + seconds + "s";
    }
}
