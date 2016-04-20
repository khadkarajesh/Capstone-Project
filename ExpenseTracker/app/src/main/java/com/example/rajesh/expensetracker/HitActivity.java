package com.example.rajesh.expensetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import timber.log.Timber;

public class HitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 8);
        calendar.set(Calendar.AM_PM,Calendar.AM);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);


        //calendar.set(Calendar.MINUTE,0);
        calendar.getTimeInMillis();





        Timber.d(" time in milliseconds %d",calendar.getTimeInMillis());
    }
}
