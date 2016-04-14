package com.example.rajesh.expensetracker.utils;


import com.example.rajesh.expensetracker.report.ReportFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {
    public enum TimeStamp {
        YEAR_FIRST_DAY, YEAR_LAST_DAY, MONTH_FIRST_DAY, MONTH_LAST_DAY, WEEK_FIRST_DAY, WEEK_LAST_DAY
    }

    public static long getTimeInMilliSeconds(TimeStamp timeStamp) {
        Calendar calendar = Calendar.getInstance();
        switch (timeStamp) {
            case YEAR_FIRST_DAY:
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                break;
            case YEAR_LAST_DAY:
                calendar.add(Calendar.YEAR, 1);
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                calendar.add(Calendar.YEAR, -1);
                break;
            case MONTH_FIRST_DAY:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case MONTH_LAST_DAY:
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, -1);
                break;
            case WEEK_FIRST_DAY:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
            case WEEK_LAST_DAY:
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                break;
        }
        return calendar.getTimeInMillis();
    }

    public static String getTimeInFormattedString(long milliSeconds) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(milliSeconds);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyy");

        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());

        return simpleDateFormat.format(date);
    }
    public static long[] getTimeInMilliSecondsByReportType(ReportFragment.ReportType reportType) {
        long[] timeInMilli = new long[2];
        switch (reportType) {
            case REPORT_BY_WEEK:
                timeInMilli[0] = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.WEEK_FIRST_DAY);
                timeInMilli[1] = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.WEEK_LAST_DAY);
                break;
            case REPORT_BY_MONTH:
                timeInMilli[0] = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.MONTH_FIRST_DAY);
                timeInMilli[1] = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.MONTH_LAST_DAY);
                break;
            case REPORT_BY_YEAR:
                timeInMilli[0] = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.YEAR_FIRST_DAY);
                timeInMilli[1] = DateTimeUtil.getTimeInMilliSeconds(DateTimeUtil.TimeStamp.YEAR_LAST_DAY);
                break;
        }
        return timeInMilli;
    }


}
