package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogHobby extends AppCompatActivity {
    private FancyButton btnHobbyCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hobby);
        btnHobbyCancel=findViewById(R.id.btn_hobby_cancel);
        btnHobbyCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogHobby.this.finish();
            }
        });
    }
}
