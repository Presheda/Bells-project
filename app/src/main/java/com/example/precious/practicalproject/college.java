package com.example.precious.practicalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class college extends AppCompatActivity {

    public static final String collegID = "ID";

    LinearLayout colnas, coleng, colmans, colenvs, college_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colleg);



        colnas = (LinearLayout) findViewById(R.id.colnas);
        coleng = (LinearLayout) findViewById(R.id.coleng);
        colmans = (LinearLayout) findViewById(R.id.colmans);
        colenvs = (LinearLayout) findViewById(R.id.colenvs);
        college_activity = (LinearLayout) findViewById(R.id.college_activity);

        Animation upToDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.uptodown);

        college_activity.startAnimation(upToDown);



        colnas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(1);
            }
        });

        coleng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(2);
            }
        });


        colmans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(3);
            }
        });



        colenvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(4);
            }
        });



    }



    void startNewActivity(int number) {

        Intent intent = new Intent(college.this, college_detail.class);
        intent.putExtra(collegID, "" + number);
        startActivity(intent);

    }


}








