package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogSmile extends AppCompatActivity {
    private FancyButton btnSmileCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_smile);
        btnSmileCancel=findViewById(R.id.btn_smile_cancel);
        btnSmileCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogSmile.this.finish();
            }
        });
    }
}
