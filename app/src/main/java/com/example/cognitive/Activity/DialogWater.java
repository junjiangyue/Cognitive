package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogWater extends AppCompatActivity {
    private FancyButton btnWaterCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_water);
        btnWaterCancel=findViewById(R.id.btn_water_cancel);
        btnWaterCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogWater.this.finish();
            }
        });
    }
}
