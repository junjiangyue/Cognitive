package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogRead extends AppCompatActivity {
    private FancyButton btnReadCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_read);
        btnReadCancel=findViewById(R.id.btn_read_cancel);
        btnReadCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogRead.this.finish();
            }
        });
    }
}
