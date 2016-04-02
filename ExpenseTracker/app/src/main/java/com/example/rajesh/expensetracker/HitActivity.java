package com.example.rajesh.expensetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.rajesh.expensetracker.widget.CircularView;

public class HitActivity extends AppCompatActivity {

    CircularView circularImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit);

        circularImageView = (CircularView) findViewById(R.id.iv_circular_image);
        circularImageView.setFillColor("#ff00ff");
    }
}
