package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;
import com.example.cognitive.model.MyMarkerView;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AD8ResultPage extends AppCompatActivity {
    //雷达图
    private RadarChart radar;
    //项目表
    List<RadarEntry> list;
    //存分数
    private SharedPreferences spTestScore;
    int score;
    int judgement_point;
    int memory_point;
    int cognition_point;

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
        TextView adviceAspects = findViewById(R.id.frail_aspects);
        TextView detailedSport =findViewById(R.id.ad8_sport_advice);
        TextView detailedHealth =findViewById(R.id.ad8_health_advice);
        TextView detailedMemory=findViewById(R.id.ad8_memory_advice);

        Intent getIntent=getIntent();
        String scoreString=getIntent.getStringExtra("ad8_score");
        String judgementString=getIntent.getStringExtra("ad8_judgement");
        String memoryString=getIntent.getStringExtra("ad8_memory");
        String cognitionString=getIntent.getStringExtra("ad8_cognition");
        score=Integer.parseInt(scoreString);
        judgement_point=Integer.parseInt(judgementString);
        memory_point=Integer.parseInt(memoryString);
        cognition_point=Integer.parseInt(cognitionString);
        boolean flag=false;
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

        String aspects = "\t\t\t\t您可能存在的认知衰弱问题为";
        String detailed_health ="";
        String detailed_sport ="";
        String detailed_memory="";

        //用字符串数组存储各维度建议，然后随机出现
        Random r=new Random();
        String [] sportArray={ this.getResources().getString(R.string.sport_advice_1),
                this.getResources().getString(R.string.sport_advice_2),
                this.getResources().getString(R.string.sport_advice_3),
                this.getResources().getString(R.string.sport_advice_4)};
        String [] healthArray={ this.getResources().getString(R.string.diet_advice_1),
                this.getResources().getString(R.string.diet_advice_2),
                this.getResources().getString(R.string.diet_advice_3)};
        String [] memoryArray={ this.getResources().getString(R.string.memory_advice_1),
                this.getResources().getString(R.string.memory_advice_2)};

        detailed_health+="<font color='#0000CD'>健康膳食建议：</font>";
        detailed_health+=healthArray[r.nextInt(2)];

        detailed_sport +="<font color='#0000CD'>运动建议：</font>";
        detailed_sport +=sportArray[r.nextInt(3)];

        detailed_memory+="<font color='#0000CD'>提高记忆建议：</font>";
        detailed_memory+=memoryArray[r.nextInt(1)];

        if (judgement_point > 0) {
            aspects += "<font color='#FF0000'>判断力</font>，";
            flag = true;
        }
        if (memory_point > 0) {
            aspects += "<font color='#FF0000'>记忆力</font>，";
            flag = true;
        }
        if (cognition_point > 0) {
            aspects += "<font color='#FF0000'>意识</font>，";
            flag = true;
        }
        aspects += "需要对这些方面进行检查、治疗。";
        if (flag) {
            adviceAspects.setText(Html.fromHtml(aspects));
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

        radarDataSet.setDrawFilled(true); // 绘制填充，默认为false
        radarDataSet.setColor(Color.parseColor("#F08080"));
        radarDataSet.setFillColor(Color.parseColor("#F08080"));
        radarDataSet.setFillAlpha(51); // 填充内容透明度
        radarDataSet.setDrawValues(false);

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
        //设置雷达图选项，marker、是否转动等
        MyMarkerView myMarkerView = new MyMarkerView(this);
        myMarkerView.setChartView(radar);
        radar.setMarker(myMarkerView);
        radar.getDescription().setEnabled(false);
        radar.setRotationEnabled(false);

        detailedSport.setText(Html.fromHtml(detailed_sport));
        detailedHealth.setText(Html.fromHtml(detailed_health));
        detailedMemory.setText(Html.fromHtml(detailed_memory));
    }

    public void backToMainPage(View view)
    {
        //Intent intent=new Intent(FrailResultPage.this, MainActivity.class);
        //finish();
        Intent intent=new Intent(AD8ResultPage.this, SetTask.class);
        startActivity(intent);
        finish();
    }
    public void backToMain(View view)
    {
        //Intent intent=new Intent(FrailResultPage.this, MainActivity.class);
        //finish();
        Intent intent=new Intent(AD8ResultPage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void goToAD8Analyze(View view)
    {
        Intent intent=new Intent(AD8ResultPage.this, AD8AnalyzeActivity.class);
        intent.putExtra("judgement_point", judgement_point);
        intent.putExtra("memory_point", memory_point);
        intent.putExtra("cognition_point", cognition_point);

        //finish();
        startActivity(intent);
    }
}