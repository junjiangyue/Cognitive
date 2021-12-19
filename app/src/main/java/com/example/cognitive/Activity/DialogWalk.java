package com.example.cognitive.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogWalk extends AppCompatActivity {
    private TextView txtWalkReal;
    private TextView txtWalkGoal;
    private FancyButton btnWalkCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_walk);
        txtWalkReal=findViewById(R.id.txt_WalkReal);
        txtWalkReal.setText("7000");
        txtWalkGoal=findViewById(R.id.txt_WalkGoal);
        txtWalkGoal.setText(" / 7000");
        btnWalkCancel=findViewById(R.id.btn_walk_cancel);
        btnWalkCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogWalk.this.finish();
            }
        });
    }
}
