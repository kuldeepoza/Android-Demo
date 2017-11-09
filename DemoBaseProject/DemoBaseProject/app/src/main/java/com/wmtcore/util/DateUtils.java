package com.wmtcore.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    private SimpleDateFormat dateFormatter;
    private String strDateFormat;
    private String strTimeFormat;
    private String strDateTimeFormat;
    private String strStringDate;
    private long milliSeconds;
    private TimeZone timeZone;

    public static final String DF_dd_MM_yyyy = "dd/MM/yyyy";
    public static final String DF_MM_dd_yyyy = "MM/dd/yyyy";
    public static final String DF_dd_MMM_yyyy = "dd/MMM/yyyy";
    public static final String DF_MMMM_dd_yyyy = "MMMM dd, yyyy";
    public static final String DF_MMMM_dd_yyyy_AT_hh_mm_a = "MMMM dd, yyyy @ hh:mm a";
    public static final String DF_dd_MM_yyyy_HH_mm_ss = "dd/MM/yyyy HH:mm:ss";
    public static final String DF_HH_mm_a = "HH:mm a";
    public static final String DF_hh_mm_a = "hh:mm a";
    public static final String DF_MM_dd_yyyy_hh_mm_a = "MM/dd/yyyy hh:mm a";

    public static final String E_MM_DD = "EEEE MM/dd ";

    public static final int DAY_SECONDS = 86400;
    public static final int HOUR_SECONDS = 3600;
    public static final int DAY_MILISECONDS = 86400000;
    public static final int HOUR_MILISECONDS = 3600000;

    public static long getCurrentTimeInSecond() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public static long convertMilliSecondToSecond(long milliSeconds) {
        return TimeUnit.MILLISECONDS.toSeconds(milliSeconds);
    }

    public static long convertMilliSecondToHours(long milliSeconds) {
        return TimeUnit.MILLISECONDS.toSeconds(milliSeconds);
    }

    private static long getOffsetFromUtc() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime());
    }

    public static class Builder {

        DateUtils dateUtils;

        public Builder() {
            dateUtils = new DateUtils();
            dateUtils.timeZone = TimeZone.getDefault();
        }

        public Builder setDateFormat(String strDateFormat) {
            dateUtils.strDateFormat = strDateFormat;
            return this;
        }

        public Builder setTimeFormat(String strTimeFormat) {
            dateUtils.strTimeFormat = strTimeFormat;
            return this;
        }

        public Builder setDateTimeFormat(String strDateTimeFormat) {
            dateUtils.strDateTimeFormat = strDateTimeFormat;
            return this;
        }

        public Builder setSeconds(long seconds) {
            dateUtils.milliSeconds = seconds * 1000;
            return this;
        }

        public Builder setMilliSeconds(long milliSeconds) {
            dateUtils.milliSeconds = milliSeconds;
            return this;
        }

        public Builder setTimeZone(TimeZone timeZone) {
            dateUtils.timeZone = timeZone;
            return this;
        }

        public Builder setUTCTimeZone() {
            dateUtils.timeZone = TimeZone.getTimeZone("UTC");
            return this;
        }

        public Builder setStringDate(String strDate, String strDateFormat) throws ParseException {
            dateUtils.strStringDate = strDate;
            dateUtils.strDateFormat = strDateFormat;
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateFormat);
            dateUtils.milliSeconds = dateUtils.dateFormatter.parse(dateUtils.strStringDate).getTime();
            return this;
        }

        public Builder setStringTime(String strDate, String strTimeFormat) throws ParseException {
            dateUtils.strStringDate = strDate;
            dateUtils.strTimeFormat = strTimeFormat;
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strTimeFormat);
            dateUtils.milliSeconds = dateUtils.dateFormatter.parse(dateUtils.strStringDate).getTime();
            return this;
        }

        public Builder setStringDateTime(String strDate, String strDateTimeFormat) throws ParseException {
            dateUtils.strStringDate = strDate;
            dateUtils.strDateTimeFormat = strDateTimeFormat;
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateTimeFormat);
            dateUtils.milliSeconds = dateUtils.dateFormatter.parse(dateUtils.strStringDate).getTime();
            return this;
        }

        public Builder now() {
            dateUtils.milliSeconds = System.currentTimeMillis();
            return this;
        }

        public Builder addHour(int hour) {
            dateUtils.milliSeconds += hour * 60 * 60 * 1000;
            return this;
        }

        public Builder addMinutes(int minutes) {
            dateUtils.milliSeconds += minutes * 60 * 1000;
            return this;
        }

        public Builder addSeconds(int seconds) {
            dateUtils.milliSeconds += seconds * 1000;
            return this;
        }

        public Builder addMiliSeconds(int milliSeconds) {
            dateUtils.milliSeconds += milliSeconds;
            return this;
        }

        public Builder minusHour(int hour) {
            dateUtils.milliSeconds -= hour * 60 * 60 * 1000;
            return this;
        }

        public Builder minusMinutes(int minutes) {
            dateUtils.milliSeconds -= minutes * 60 * 1000;
            return this;
        }

        public Builder minusSeconds(int seconds) {
            dateUtils.milliSeconds -= seconds * 1000;
            return this;
        }

        public Builder minusMiliSeconds(int milliSeconds) {
            dateUtils.milliSeconds -= milliSeconds;
            return this;
        }

        public String getStringDate() throws ParseException {
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateFormat);
            return dateUtils.dateFormatter.format(getMilliSeconds());
        }

        public long getDateInSecond() throws ParseException {
            /*if (TextUtils.isEmpty(dateUtils.strDateFormat))
                dateUtils.strDateFormat = DateUtils.DF_dd_MM_yyyy;
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateFormat);
            Date date = dateUtils.dateFormatter.parse(dateUtils.dateFormatter.format(getMilliSeconds()));
            return date.getTime() / 1000;*/
            return (getMilliSeconds() - (getMilliSeconds() % DAY_MILISECONDS)) / 1000;
        }

        public long getHourInSecond() throws ParseException {
            return (getMilliSeconds() - (getMilliSeconds() % HOUR_MILISECONDS)) / 1000;
        }

        public Date getDate() throws ParseException {
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateFormat);
            return new Date(getMilliSeconds());
        }

        public String getStringTime() throws ParseException {
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strTimeFormat);
            return dateUtils.dateFormatter.format(getMilliSeconds());
        }

        public Date getTime() throws ParseException {
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strTimeFormat);
            return new Date(getMilliSeconds());
        }

        public String getStringDateTime() throws ParseException {
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateTimeFormat);
            return dateUtils.dateFormatter.format(getMilliSeconds());
        }

        public Date getDateTime() throws ParseException {
            dateUtils.dateFormatter = getSimpleDateFormatIntance(dateUtils.strDateTimeFormat);
            return new Date(getMilliSeconds());
        }

        public Calendar getCalendar() throws ParseException {
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(dateUtils.timeZone);
            cal.setTimeInMillis(getMilliSeconds());
            return cal;
        }

        public long getMilliSeconds() {
            return dateUtils.milliSeconds;
        }

        public long getDateTimeStampInSecond() {
            return convertMilliSecondToSecond(dateUtils.milliSeconds);
        }

        public long getUTCDateTimeStampInSecond() throws ParseException {
            Debug.e("Offset", getOffsetFromUtc() + "");
            return ((dateUtils.milliSeconds - getOffsetFromUtc()) / 1000);
        }

        private SimpleDateFormat getSimpleDateFormatIntance(String format) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(dateUtils.timeZone);
            sdf.setLenient(false);
            return sdf;
        }
    }
}

