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
    private TextView txtStepReal;
    private TextView txtStepGoal;
    private TextView txtWaterReal;
    private TextView txtWaterGoal;
    private TextView txtReadReal;
    private TextView txtReadGoal;
    private TextView txtHobbyReal;
    private TextView txtHobbyGoal;
    private TextView txtSmileReal;
    private TextView txtSmileGoal;
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
        txtStepReal= (TextView) findViewById(R.id.txt_StepReal);
        txtStepReal.setText("4567");
        txtStepGoal= (TextView) findViewById(R.id.txt_StepGoal);
        txtStepGoal.setText(" / 7000步");
        txtWaterReal= (TextView) findViewById(R.id.txt_WaterReal);
        txtWaterReal.setText("1.5L");
        txtWaterGoal= (TextView) findViewById(R.id.txt_WaterGoal);
        txtWaterGoal.setText(" / 1.5L");
        txtReadReal= (TextView) findViewById(R.id.txt_ReadReal);
        txtReadReal.setText("20");
        txtReadGoal= (TextView) findViewById(R.id.txt_ReadGoal);
        txtReadGoal.setText("分钟 / 30分钟");
        txtHobbyReal= (TextView) findViewById(R.id.txt_HobbyReal);
        txtHobbyReal.setText("70");
        txtHobbyGoal= (TextView) findViewById(R.id.txt_HobbyGoal);
        txtHobbyGoal.setText("分钟 / 60分钟");
        txtSmileReal= (TextView) findViewById(R.id.txt_SmileReal);
        txtSmileReal.setText("0");
        txtSmileGoal= (TextView) findViewById(R.id.txt_SmileGoal);
        txtSmileGoal.setText(" / 1次");
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


    }

    @Override
    public void onClick(View view) {



    }




}
