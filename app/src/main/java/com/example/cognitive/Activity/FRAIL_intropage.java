package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

public class FRAIL_intropage extends AppCompatActivity {

    public static FRAIL_intropage introPage;
    private Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_intropage);
        introPage=this;
    }


    public void startActivityForResult(View view)
    {
        finish();
    }

    public void startFrailTest(View view)
    {
        Intent intent=new Intent( FRAIL_intropage.this,FRAIL_test.class);
        //finish();
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_from_top,R.anim.slide_to_bottom);
    }
}