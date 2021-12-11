package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cognitive.Adapter.MyAD8PagerAdapter;
import com.example.cognitive.R;

import java.util.ArrayList;

public class AD8Test extends AppCompatActivity {
    private ViewPager vpager_ad8;
    private ArrayList<View> aList;
    //ViewPager适配器
    private MyAD8PagerAdapter mAdapter;
    public static AD8Test Test;
    public int score=0;
    public int judgement_point=0;
    public int memory_point=0;
    public int cognition_point=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad8_test);
        vpager_ad8=(ViewPager)findViewById(R.id.ad8_viewpager);
        Test=this;

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        aList.add(li.inflate(R.layout.ad8_1,null,false));
        aList.add(li.inflate(R.layout.ad8_2,null,false));
        aList.add(li.inflate(R.layout.ad8_3,null,false));
        aList.add(li.inflate(R.layout.ad8_4,null,false));
        aList.add(li.inflate(R.layout.ad8_5,null,false));
        aList.add(li.inflate(R.layout.ad8_6,null,false));
        aList.add(li.inflate(R.layout.ad8_7,null,false));
        aList.add(li.inflate(R.layout.ad8_8,null,false));


        mAdapter = new MyAD8PagerAdapter(aList);
        vpager_ad8.setAdapter(mAdapter);
    }

    public void getAD8Result(View v)
    {
        for(int i=0;i<8;i++)
        {
            score+=mAdapter.result[i];
        }

        judgement_point=mAdapter.result[0];
        memory_point=mAdapter.result[2]+mAdapter.result[4]+mAdapter.result[6]+mAdapter.result[7];
        cognition_point=mAdapter.result[1]+mAdapter.result[3]+mAdapter.result[5];

        Intent intent=new Intent(AD8Test.this,AD8ResultPage.class);
        intent.putExtra("ad8_score",String.valueOf(score));
        intent.putExtra("ad8_judgement",String.valueOf(judgement_point));
        intent.putExtra("ad8_memory",String.valueOf(memory_point));
        intent.putExtra("ad8_cognition",String.valueOf(cognition_point));
        startActivity(intent);
    }

}