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

public class FrailDefeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_defeat);

        //获取Frail测试的分数
        Intent getIntent=getIntent();
        String scoreString=getIntent.getStringExtra("score");
        String strengthString=getIntent.getStringExtra("strength_point");
        String healthString=getIntent.getStringExtra("health_point");
        int score=Integer.parseInt(scoreString);
        int strength_point=Integer.parseInt(strengthString);
        int health_point=Integer.parseInt(healthString);

        setPercentage(score);

        Button frailAdvice=findViewById(R.id.btn_frail_advice);
        frailAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrailDefeatActivity.this, FrailResultPage.class);
                intent.putExtra("score", String.valueOf(score));
                intent.putExtra("strength_point", String.valueOf(strength_point));
                intent.putExtra("health_point", String.valueOf(health_point));
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setPercentage(int score)
    {
        TextView commentText=findViewById(R.id.frail_defeat_comment);
        int percentage=100-score*10;
        commentText.setText("结果不错，再接再厉！");
        //percentText.setText(percentage +"%");

        MarkView markView=findViewById(R.id.frail_mark);
        markView.setMark(percentage);
        markView.setStrokeColors(Color.BLUE);
    }
}