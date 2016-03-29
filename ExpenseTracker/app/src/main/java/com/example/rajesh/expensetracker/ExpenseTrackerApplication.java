package com.example.rajesh.expensetracker;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import timber.log.Timber;


public class ExpenseTrackerApplication extends Application {
    public static ExpenseTrackerApplication expenseTrackerApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Stetho.initializeWithDefaults(this);
        expenseTrackerApplication = this;
    }

    public static Context getExpenseTrackerApplication() {
        return expenseTrackerApplication;
    }


}
