package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cognitive.R;

public class WeeklyExercise extends AppCompatActivity {
    private CardView cvSport1;
    private CardView cvSport2;
    private CardView cvSport3;
    private CardView cvSport4;
    private CardView cvSport5;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_exercise);
        cvSport1=(CardView) findViewById(R.id.cv_sport1);
        cvSport2=(CardView) findViewById(R.id.cv_sport2);
        cvSport3=(CardView) findViewById(R.id.cv_sport3);
        cvSport4=(CardView) findViewById(R.id.cv_sport4);
        cvSport5=(CardView) findViewById(R.id.cv_sport5);
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
