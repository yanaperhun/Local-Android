package com.local.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private static final String TAG = "DateUtils";

    public static final String FORMAT_DD_MM_YYYY = "dd.MM.yyyy";
    public static final String FORMAT_DD_MM_YYYY_HH_mm_ss = "dd.MM.yyyy HH:mm:ss";
    public static final String FORMAT_UTC_TZ = "dd-MM-yyyy'T'HH:mm:ss.SSS";
    public static final long ONE_DAY_MILLIS = 1000 * 60 * 60 * 24;
    public static final long ONE_WEEK_MILLIS = 1000 * 60 * 60 * 24 * 7;
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_dd_MMMM_yyyy = "dd MMMM yyyy";
    public static final String FORMAT_dd_MMMM = "dd MMMM";

    public static Date getDateFromUTC(String utcTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(utcTime);
    }

    public static Date getDateFromFormat(String utcTime, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(utcTime);
    }

    public static long getMillisFromDate(Date date) {
        return date.getTime();
    }

    public static long getMillisFromString(String utcTime) throws ParseException {
        return getDateFromUTC(utcTime)
                .getTime();
    }

    public static String getMinsSec(long millis) {

        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static String getHoursMins(long millis) {

        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public static long getCurrentUTCInMillis() {
        Calendar updateTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long time = updateTime.getTimeInMillis(); // get milliseconds
        Log.d(TAG, "time in millis : " + time);
        return time;
    }

    public static String getTodayDate(String format) {
        return formatDateToString(Calendar.getInstance().getTime(), format);
    }

    public static String getDayMonthAgo(String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return formatDateToString(calendar.getTime(), format);
    }

    public static long getTodayStartDay() {

        String todayDay = getTodayDate(FORMAT_dd_MMMM_yyyy);

        try {
            Date date = getDateFromFormat(todayDay, FORMAT_dd_MMMM_yyyy);
            return getMillisFromDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static Date getDay2MonthsAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        return calendar.getTime();
    }

    public static long getDay3MonthsAgoMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        return calendar.getTimeInMillis();
    }

    public static String getDay7WeeksAgo(String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -7);
        return formatDateToString(calendar.getTime(), format);
    }

    public static String getDayMonthAgoFromDate(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return formatDateToString(calendar.getTime(), format);
    }

    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat newDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return newDateFormat.format(date);
    }

    public static Date formatStringToDate(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.parse(date);
    }

    public static String formatLongToString(long dateTime, String format) throws ParseException {
        if (dateTime == 0) return "";
        Date date = new Date(dateTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getMonthDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = dateFormat.parse(dateStr);
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return newDateFormat.format(date);

    }

    public static String changeFormat(String dateStr, String currentFormat, String newFormat) throws ParseException {
        Date date = formatStringToDate(dateStr, currentFormat);
        return formatDateToString(date, newFormat);
    }


    public static String[] getNextDays(int daysCount, String format) {
        String[] result = new String[daysCount];


        for (int i = 0; i < daysCount; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            Date date = calendar.getTime();
            result[i] = formatDateToString(date, format);
        }

        return result;
    }

    public static String getFormattedDate(int year, int month, int day, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        try {
            return formatLongToString(calendar.getTimeInMillis(), format);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return "";
    }


    public static Long getMillis(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }
}
