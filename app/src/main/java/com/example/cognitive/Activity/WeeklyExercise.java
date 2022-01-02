package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cognitive.R;

public class WeeklyExercise extends AppCompatActivity {
    private SharedPreferences sp1;
    private String TAG="WeeklyExercise";
    private CardView cvSport1;
    private CardView cvSport2;
    private CardView cvSport3;
    private CardView cvSport4;
    private CardView cvSport41;
    private CardView cvSport5;
    private CardView cvSport6;
    private CardView cvSport61;
    private CardView cvSport7;
    private int everyWeekSport;
    private int weeklyPowerSport;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_exercise);
        sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        everyWeekSport = sp1.getInt("everyWeekSport",4);
        Log.d(TAG,"everyWeekSport:"+everyWeekSport);
        weeklyPowerSport = sp1.getInt("weeklyPowerSport",2);
        Log.d(TAG,"weeklyPowerSport:"+weeklyPowerSport);
        cvSport1=(CardView) findViewById(R.id.cv_sport1);
        cvSport2=(CardView) findViewById(R.id.cv_sport2);
        cvSport3=(CardView) findViewById(R.id.cv_sport3);
        cvSport4=(CardView) findViewById(R.id.cv_sport4);
        cvSport41=(CardView) findViewById(R.id.cv_sport4_1);
        cvSport5=(CardView) findViewById(R.id.cv_sport5);
        cvSport6=(CardView) findViewById(R.id.cv_sport6);
        cvSport61=(CardView) findViewById(R.id.cv_sport6_1);
        cvSport7=(CardView) findViewById(R.id.cv_sport7);
        if(weeklyPowerSport==1){
            cvSport4.setVisibility(View.GONE);
            cvSport6.setVisibility(View.GONE);
            if(everyWeekSport==3){
                cvSport2.setVisibility(View.GONE);
                cvSport41.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==4){
                cvSport2.setVisibility(View.GONE);
                cvSport41.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==5){
                cvSport2.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==6){
                cvSport2.setVisibility(View.GONE);
            }
        }
        if(weeklyPowerSport==2){
            cvSport41.setVisibility(View.GONE);
            cvSport6.setVisibility(View.GONE);
            if(everyWeekSport==3){
                cvSport2.setVisibility(View.GONE);
                cvSport3.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==4){
                cvSport2.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==5){
                cvSport3.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==6){
                cvSport2.setVisibility(View.GONE);
            }
        }
        if(weeklyPowerSport==3){
            cvSport41.setVisibility(View.GONE);
            cvSport61.setVisibility(View.GONE);
            if(everyWeekSport==3){
                cvSport2.setVisibility(View.GONE);
                cvSport3.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==4){
                cvSport3.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==5){
                cvSport3.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==6){
                cvSport5.setVisibility(View.GONE);
            }
        }
        cvSport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeeklyExercise.this, DialogPowerSport.class);
                startActivity(intent);
            }
        });
        cvSport2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeeklyExercise.this, DialogOtherSport.class);
                startActivity(intent);
            }
        });
    }
}
