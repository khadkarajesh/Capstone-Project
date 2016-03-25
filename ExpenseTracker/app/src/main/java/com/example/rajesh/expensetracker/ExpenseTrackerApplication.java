package com.example.rajesh.expensetracker;

import android.app.Application;

import timber.log.Timber;


public class ExpenseTrackerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
