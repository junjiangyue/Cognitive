package com.example.cognitive.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import mehdi.sakout.fancybuttons.FancyButton;

public class DialogWalk extends AppCompatActivity {
    private SharedPreferences sp1;//打卡设置
    private SharedPreferences spStepTask;//步数
    private TextView txtWalkReal;
    private TextView txtWalkGoal;
    private FancyButton btnWalkCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_walk);
        sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        int stepGoal=sp1.getInt("stepGoal",7000);
        spStepTask=this.getSharedPreferences("userStepTask", Context.MODE_PRIVATE);
        int step;
        step=spStepTask.getInt("stepFinish",0);
        txtWalkReal=findViewById(R.id.txt_WalkReal);
        txtWalkReal.setText(String.valueOf(step));
        txtWalkGoal=findViewById(R.id.txt_WalkGoal);
        txtWalkGoal.setText(" / "+stepGoal);
        btnWalkCancel=findViewById(R.id.btn_walk_cancel);
        btnWalkCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogWalk.this.finish();
            }
        });
    }
}
