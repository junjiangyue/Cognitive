package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;


public class FrailResultPage extends AppCompatActivity {
    public TextView showScore;
    public TextView frailAdvice;
    public static FrailResultPage ResultPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_result_page);
        ResultPage=this;
        setScore();
        FRAIL_test.Test.finish();
        FRAIL_intropage.introPage.finish();
    }

    public void setScore()
    {
        showScore=findViewById(R.id.frail_score);
        frailAdvice=findViewById(R.id.frail_advice);

        Intent getIntent=getIntent();
        String scoreString=getIntent.getStringExtra("score");
        int score=Integer.parseInt(scoreString);


        showScore.setText(scoreString+"分");
        if(score==0)
        {
            frailAdvice.setText(R.string.FRAIL_healthy);
        }
        else if(score>=3)
        {
            frailAdvice.setText(R.string.FRAIL_frailty);
        }
        else
        {
            frailAdvice.setText(R.string.FRAIL_pre_frailty);
        }
    }

    public void backToMainPage(View view)
    {
        //Intent intent=new Intent(FrailResultPage.this, MainActivity.class);
        finish();

        //startActivity(intent);
    }
}