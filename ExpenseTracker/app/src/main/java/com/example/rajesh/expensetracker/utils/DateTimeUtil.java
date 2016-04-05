package com.example.rajesh.expensetracker.utils;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import timber.log.Timber;

public class DateTimeUtil {

    public static final int MONTH_FIRST_DATE = 0;
    public static final int MONTH_LAST_DATE = 1;


    public static long getTimeStamp(int dayOfMonth) {
        long timeStamp = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Calendar georgianCalender = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);

        if (dayOfMonth == MONTH_FIRST_DATE) {
            timeStamp = georgianCalender.getTimeInMillis();
            Timber.d("start date timestamp %d",timeStamp);
        }
        if(dayOfMonth==MONTH_LAST_DATE){
            int lastDate = georgianCalender.getActualMaximum(Calendar.DAY_OF_MONTH);
            Timber.d("last date of month %d",lastDate);
            georgianCalender = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), lastDate);
            timeStamp = georgianCalender.getTimeInMillis();
            Timber.d("end date timestamp %d",timeStamp);
        }
        return timeStamp;
    }
}
