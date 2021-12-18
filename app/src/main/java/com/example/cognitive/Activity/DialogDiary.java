package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogDiary extends AppCompatActivity {
    private FancyButton btnDiaryCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_diary);
        btnDiaryCancel=findViewById(R.id.btn_diary_cancel);
        btnDiaryCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogDiary.this.finish();
            }
        });
    }
}
