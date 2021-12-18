package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogOtherSport extends AppCompatActivity {
    private LinearLayout llSportInfo;
    private FancyButton btnOtherSportCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_other_sport);

        llSportInfo=findViewById(R.id.ll_sport_info);
        btnOtherSportCancel=findViewById(R.id.btn_other_sport_cancel);
        llSportInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialogOtherSport.this, SportInfo.class);
                startActivity(intent);
            }
        });
        btnOtherSportCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogOtherSport.this.finish();
            }
        });
    }
}
