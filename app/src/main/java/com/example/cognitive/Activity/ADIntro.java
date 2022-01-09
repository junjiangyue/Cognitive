package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }

    public void startAD8Test(View view)
    {
        Intent intent=new Intent( ADIntro.this, AD8Test.class);
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