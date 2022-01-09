package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

public class FrailIntro extends AppCompatActivity {

    public static FrailIntro introPage;
    private Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_intropage);
        introPage=this;
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }


    public void startActivityForResult(View view)
    {
        finish();
    }

    public void startFrailTest(View view)
    {
        Intent intent=new Intent( FrailIntro.this, FrailTest.class);
        //finish();
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_from_top,R.anim.slide_to_bottom);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}