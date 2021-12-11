package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cognitive.Adapter.MyFrailPagerAdapter;
import com.example.cognitive.R;

import java.util.ArrayList;

public class FrailTest extends AppCompatActivity {
    private ViewPager vpager_frail;
    private ArrayList<View> aList;
    //ViewPager适配器
    private MyFrailPagerAdapter mAdapter;
    public static FrailTest Test;
    public int score=0;
    public int strength_point=0;
    public int health_point=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_test);
        vpager_frail = (ViewPager) findViewById(R.id.frail_viewpager);
        Test=this;

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        aList.add(li.inflate(R.layout.frail_1,null,false));
        aList.add(li.inflate(R.layout.frail_2,null,false));
        aList.add(li.inflate(R.layout.frail_3,null,false));
        aList.add(li.inflate(R.layout.frail_4,null,false));
        aList.add(li.inflate(R.layout.frail_5,null,false));

        mAdapter = new MyFrailPagerAdapter(aList);
        vpager_frail.setAdapter(mAdapter);
    }

    public void getFrailResult(View view)
    {
        mAdapter.result[4]=((float)(mAdapter.weight_pre-mAdapter.weight_cur)/(float)mAdapter.weight_cur>0.05)? 1:0;
        for(int i=0;i<5;i++)
        {
            if(i!=3)
            {
                score+=mAdapter.result[i];
            }
            else
            {
                score+=(mAdapter.result[i]>5)?1:0;
            }
        }
        strength_point=mAdapter.result[0]+mAdapter.result[1]+mAdapter.result[2];
        health_point=((mAdapter.result[3]>5)?1:0)+mAdapter.result[4];
        //Log.i("weight", "afterTextChanged: "+mAdapter.weight_cur);
        Intent intent=new Intent(FrailTest.this,FrailResultPage.class);
        intent.putExtra("score",String.valueOf(score));
        intent.putExtra("strength_point",String.valueOf(strength_point));
        intent.putExtra("health_point",String.valueOf(health_point));
        //finish();
        startActivity(intent);
    }
}