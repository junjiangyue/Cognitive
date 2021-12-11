package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

public class ADIntro extends AppCompatActivity {
    public static ADIntro introPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adintro);
        introPage=this;
    }

    public void startAD8Test(View view)
    {
        Intent intent=new Intent( ADIntro.this, AD8Test.class);
        //finish();
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_from_top,R.anim.slide_to_bottom);
    }
}