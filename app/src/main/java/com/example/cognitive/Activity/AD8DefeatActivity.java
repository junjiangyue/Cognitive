package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import tr.xip.markview.MarkView;

import com.example.cognitive.R;

public class AD8DefeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad8_defeat);

        //获取Frail测试的分数
        Intent getIntent=getIntent();
        String scoreString=getIntent.getStringExtra("ad8_score");
        String judgementString=getIntent.getStringExtra("ad8_judgement");
        String memoryString=getIntent.getStringExtra("ad8_memory");
        String cognitionString=getIntent.getStringExtra("ad8_cognition");
        int score=Integer.parseInt(scoreString);
        int judgement_point=Integer.parseInt(judgementString);
        int memory_point=Integer.parseInt(memoryString);
        int cognition_point=Integer.parseInt(cognitionString);

        setPercentage(score);

        Button ad8Advice=findViewById(R.id.btn_ad8_advice);
        ad8Advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AD8DefeatActivity.this, AD8ResultPage.class);
                intent.putExtra("ad8_score", String.valueOf(score));
                intent.putExtra("ad8_judgement", String.valueOf(judgement_point));
                intent.putExtra("ad8_memory", String.valueOf(memory_point));
                intent.putExtra("ad8_cognition", String.valueOf(cognition_point));
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setPercentage(int score)
    {
        TextView commentText=findViewById(R.id.ad8_defeat_comment);
        int percentage=100-score*10;
        commentText.setText("结果不错，再接再厉！");
        //percentText.setText(percentage +"%");

        MarkView markView=findViewById(R.id.ad8_mark);
        markView.setMark(percentage);
        markView.setStrokeColors(Color.RED);
    }
}