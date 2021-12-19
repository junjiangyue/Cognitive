package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogPowerSport extends AppCompatActivity {
    private LinearLayout llSportInfo;
    private FancyButton btnPowerSportCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_power_sport);

        llSportInfo=findViewById(R.id.ll_sport_info);
        btnPowerSportCancel=findViewById(R.id.btn_power_sport_cancel);
        llSportInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialogPowerSport.this, SportInfo.class);
                startActivity(intent);
            }
        });
        btnPowerSportCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogPowerSport.this.finish();
            }
        });
    }
}
