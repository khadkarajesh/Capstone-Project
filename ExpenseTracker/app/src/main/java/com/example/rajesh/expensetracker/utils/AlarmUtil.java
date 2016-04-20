package com.example.rajesh.expensetracker.utils;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.rajesh.expensetracker.notification.ExpenseTrackerBroadCastReceiver;

import timber.log.Timber;

public class AlarmUtil {

    public static void setAlarm(Context context) {
        long milliSeconds = 1460873280000l;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent broadCastIntent = new Intent(context, ExpenseTrackerBroadCastReceiver.class);

        Timber.d("alarm called");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, broadCastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliSeconds, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, milliSeconds, pendingIntent);
        }
    }
}
