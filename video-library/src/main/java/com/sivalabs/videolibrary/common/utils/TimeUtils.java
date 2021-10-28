package com.sivalabs.videolibrary.common.utils;

public class TimeUtils {

    public static final long ONE_SECOND = 1000;

    public static final long SECONDS = 60;

    public static final long ONE_MINUTE = ONE_SECOND * 60;

    public static final long MINUTES = 60;

    public static final long ONE_HOUR = ONE_MINUTE * 60;

    public static final long HOURS = 24;

    public static final long ONE_DAY = ONE_HOUR * 24;

    private TimeUtils() {}

    /**
     * converts time (in milliseconds) to human-readable format "<w> days, <x> hours, <y> minutes
     * and (z) seconds"
     */
    public static String millisToLongDHMS(long duration) {
        StringBuffer res = new StringBuffer();
        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp)
                        .append(" day")
                        .append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp)
                        .append(" hour")
                        .append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            temp = duration / ONE_SECOND;
            long millis = duration % ONE_SECOND;

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                if (millis < 1) res.append(" and ");
                else res.append(", ");
            }

            if (temp > 0) {
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            if (millis > 0) {
                res.append(" and " + millis + " millis");
            }
            return res.toString();
        } else {
            return duration + " millis";
        }
    }
}
