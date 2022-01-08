package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OneWeekReport extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_week_report);
        Intent intent=getIntent();
        String beginDate=intent.getStringExtra("beginDate");
        String endDate=intent.getStringExtra("endDate");
        int dailyReal=intent.getIntExtra("dailyReal",0);
        int sportReal=intent.getIntExtra("sportReal",0);
        int getupReal=intent.getIntExtra("getupReal",0);
        int sleepReal=intent.getIntExtra("sleepReal",0);
        int powerReal=intent.getIntExtra("powerReal",0);
        int stepReal=intent.getIntExtra("stepReal",0);
        long days=getDay(beginDate,endDate)+1;
        TextView txtTaskDay;
        txtTaskDay=findViewById(R.id.txt_taskDay);
        txtTaskDay.setText("这一周健康打卡"+days+"天");
        TextView txtDailyReal;
        txtDailyReal=findViewById(R.id.txt_dailyReal);
        txtDailyReal.setText("共"+dailyReal+"天完成所有每日打卡");
        TextView txtSportReal;
        txtSportReal=findViewById(R.id.txt_sportReal);
        txtSportReal.setText("每周运动打卡完成"+sportReal+"项");
        TextView txtSleepFinish;
        txtSleepFinish=findViewById(R.id.txt_sleepFinish);
        txtSleepFinish.setText(sleepReal+"天完成早睡");
        TextView txtGetupFinish;
        txtGetupFinish=findViewById(R.id.txt_getupFinish);
        txtGetupFinish.setText(getupReal+"天完成早起");
        TextView txtStepFinish;
        txtStepFinish=findViewById(R.id.txt_stepFinish);
        txtStepFinish.setText(stepReal+"天达到目标步数");
        TextView txtSportFinish;
        txtSportFinish=findViewById(R.id.txt_sportFinish);
        txtSportFinish.setText("运动"+sportReal+"次");
        TextView txtPowerFinish;
        txtPowerFinish=findViewById(R.id.txt_powerFinish);
        txtPowerFinish.setText("完成力量训练"+powerReal+"次");
        TextView txtSportTime;
        txtSportTime=findViewById(R.id.txt_sportTime);
        int sportTime=powerReal*20+(sportReal-powerReal)*30;
        txtSportTime.setText("总共运动"+sportTime+"分钟");
    }
    public long getDay(String str1,String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = simpleDateFormat.parse(str1);
            Date date2 = simpleDateFormat.parse(str2);
            long Maxtime =(date2.getTime()>date1.getTime())?date2.getTime():date1.getTime();
            long Mintime = (date2.getTime()>date1.getTime())?date1.getTime():date2.getTime();
            long l = Maxtime - Mintime;
            long l1 =l/(1000*60*60*24);
            return l1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
