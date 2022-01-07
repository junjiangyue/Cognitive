package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class AD8ResultPage extends AppCompatActivity {
    //雷达图
    private RadarChart radar;
    //项目表
    List<RadarEntry> list;
    //存分数
    private SharedPreferences spTestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad8_result_page);
        spTestScore= this.getSharedPreferences("userTestScore", Context.MODE_PRIVATE);
        setScore();
        AD8Test.Test.finish();
        ADIntro.introPage.finish();
    }

    private void setScore()
    {
        TextView showScore = findViewById(R.id.ad8_score);
        TextView ad8Advice = findViewById(R.id.ad8_advice);

        Intent getIntent=getIntent();
        String scoreString=getIntent.getStringExtra("ad8_score");
        String judgementString=getIntent.getStringExtra("ad8_judgement");
        String memoryString=getIntent.getStringExtra("ad8_memory");
        String cognitionString=getIntent.getStringExtra("ad8_cognition");
        int score=Integer.parseInt(scoreString);
        int judgement_point=Integer.parseInt(judgementString);
        int memory_point=Integer.parseInt(memoryString);
        int cognition_point=Integer.parseInt(cognitionString);
        SharedPreferences.Editor editor = spTestScore.edit();
        editor.putInt("judgementScore", judgement_point);
        editor.putInt("memoryScore", memory_point);
        editor.putInt("cognitionScore", cognition_point);
        editor.commit();
        showScore.setText(scoreString+"分");
        if(score<2)
        {
            ad8Advice.setText(R.string.FRAIL_healthy);
        }
        else
        {
            ad8Advice.setText(R.string.FRAIL_frailty);
        }


        //雷达图的绘制
        radar = (RadarChart) findViewById(R.id.ad8_radar);
        list=new ArrayList<>();

        list.add(new RadarEntry(100));
        list.add(new RadarEntry(100));
        list.add(new RadarEntry((float)(5-memory_point)/5*100));
        list.add(new RadarEntry((float)(1-judgement_point)*100));
        list.add(new RadarEntry((float) (4-cognition_point)/4*100));

        RadarDataSet radarDataSet=new RadarDataSet(list,"AD-8测试结果");
        RadarData radarData=new RadarData(radarDataSet);
        radar.setData(radarData);

        //Y轴最小值不设置会导致数据中最小值默认成为Y轴最小值
        radar.getYAxis().setAxisMinimum(0);
        radar.getXAxis().setAxisMaximum(90);
        //对X轴的文字进行定义
        XAxis xAxis=radar.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value==0)
                {
                    return "健康状况";
                }
                else if(value==1)
                {
                    return "体力";
                }
                else if(value==2)
                {
                    return "记忆力";
                }
                else if(value==3)
                {
                    return "判断力";
                }
                else if(value==4)
                {
                    return "意识";
                }
                else {
                    return null;
                }
            }
        });
        radar.getDescription().setEnabled(false);
        radar.setRotationEnabled(false);
    }

    public void backToMainPage(View view)
    {
        //Intent intent=new Intent(FrailResultPage.this, MainActivity.class);
        //finish();
        Intent intent=new Intent(AD8ResultPage.this, SetTask.class);
        startActivity(intent);
        finish();
    }
}