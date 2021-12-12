package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogSleep extends AppCompatActivity {
    private FancyButton btnSleepCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sleep);
        btnSleepCancel=findViewById(R.id.btn_sleep_cancel);
        btnSleepCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogSleep.this.finish();
            }
        });
    }
}
