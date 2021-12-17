package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cognitive.R;

public class FinishHealthyTask extends AppCompatActivity implements View.OnClickListener {
    private CardView cvGetup;
    private TextView txtGetupReal;
    private TextView txtGetupGoal;
    private CardView cvSleep;
    private TextView txtSleepReal;
    private TextView txtSleepGoal;
    private CardView cvStep;
    private TextView txtStepReal;
    private TextView txtStepGoal;
    private CardView cvWater;
    private TextView txtWaterReal;
    private TextView txtWaterGoal;
    private CardView cvRead;
    private TextView txtReadReal;
    private TextView txtReadGoal;
    private CardView cvHobby;
    private TextView txtHobbyReal;
    private TextView txtHobbyGoal;
    private CardView cvSmile;
    private TextView txtSmileReal;
    private TextView txtSmileGoal;
    private CardView cvDiary;
    private TextView txtDairyReal;
    private TextView txtDairyGoal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_healthy_task);
        cvGetup=(CardView) findViewById(R.id.cv_getup);
        txtGetupReal= (TextView) findViewById(R.id.txt_GetupReal);
        txtGetupReal.setText("6:45");
        txtGetupGoal= (TextView) findViewById(R.id.txt_GetupGoal);
        txtGetupGoal.setText(" / 7:00");
        cvSleep=(CardView) findViewById(R.id.cv_sleep);
        txtSleepReal= (TextView) findViewById(R.id.txt_SleepReal);
        txtSleepReal.setText("22:45");
        txtSleepGoal= (TextView) findViewById(R.id.txt_SleepGoal);
        txtSleepGoal.setText(" / 23:00");
        cvStep=(CardView) findViewById(R.id.cv_step);
        txtStepReal= (TextView) findViewById(R.id.txt_StepReal);
        txtStepReal.setText("4567");
        txtStepGoal= (TextView) findViewById(R.id.txt_StepGoal);
        txtStepGoal.setText(" / 7000步");
        cvWater=(CardView) findViewById(R.id.cv_water);
        txtWaterReal= (TextView) findViewById(R.id.txt_WaterReal);
        txtWaterReal.setText("1.5L");
        txtWaterGoal= (TextView) findViewById(R.id.txt_WaterGoal);
        txtWaterGoal.setText(" / 1.5L");
        cvRead=(CardView) findViewById(R.id.cv_read);
        txtReadReal= (TextView) findViewById(R.id.txt_ReadReal);
        txtReadReal.setText("20");
        txtReadGoal= (TextView) findViewById(R.id.txt_ReadGoal);
        txtReadGoal.setText("分钟 / 30分钟");
        cvHobby=(CardView) findViewById(R.id.cv_hobby);
        txtHobbyReal= (TextView) findViewById(R.id.txt_HobbyReal);
        txtHobbyReal.setText("70");
        txtHobbyGoal= (TextView) findViewById(R.id.txt_HobbyGoal);
        txtHobbyGoal.setText("分钟 / 60分钟");
        cvSmile=(CardView) findViewById(R.id.cv_smile);
        txtSmileReal= (TextView) findViewById(R.id.txt_SmileReal);
        txtSmileReal.setText("0");
        txtSmileGoal= (TextView) findViewById(R.id.txt_SmileGoal);
        txtSmileGoal.setText(" / 1次");
        cvDiary=(CardView) findViewById(R.id.cv_diary);
        txtDairyReal= (TextView) findViewById(R.id.txt_DairyReal);
        txtDairyReal.setText("0");
        txtDairyGoal= (TextView) findViewById(R.id.txt_DairyGoal);
        txtDairyGoal.setText(" / 1篇");
        cvGetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogGetup.class);
                startActivity(intent);
            }
        });
        cvSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogSleep.class);
                startActivity(intent);
            }
        });
        cvStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogWalk.class);
                startActivity(intent);
            }
        });
        cvWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogWater.class);
                startActivity(intent);
            }
        });
        cvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogRead.class);
                startActivity(intent);
            }
        });
        cvHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogHobby.class);
                startActivity(intent);
            }
        });
        cvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogSmile.class);
                startActivity(intent);
            }
        });
        cvDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogDiary.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {



    }




}
