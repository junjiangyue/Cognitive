package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class HistoryResult extends AppCompatActivity {

    //雷达图
    private RadarChart radar;
    //项目表
    List<RadarEntry> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_result);

        setScore();
    }

    @SuppressLint("SetTextI18n")
    private void setScore() {
        TextView historyTestname;
        TextView historyScore;

        TextView historyAdvice = findViewById(R.id.history_advice);
        TextView historyTime = findViewById(R.id.history_time);
        TextView adviceAspects = findViewById(R.id.advice_aspects);
        TextView detailedSport =findViewById(R.id.detailed_sport_advice);
        TextView detailedHealth =findViewById(R.id.detailed_health_advice);
        TextView detailedMemory=findViewById(R.id.detailed_memory_advice);
        historyTestname = findViewById(R.id.history_testname);
        historyScore = findViewById(R.id.history_score);

        Intent getIntent = getIntent();
        String scoreString = getIntent.getStringExtra("score");
        String testdateString = getIntent.getStringExtra("testdate");
        String testtimeString = getIntent.getStringExtra("testtime");
        String testnameString = getIntent.getStringExtra("testname");
        String strengthString = getIntent.getStringExtra("strength");
        String healthString = getIntent.getStringExtra("health");
        String judgementString = getIntent.getStringExtra("judgement");
        String memoryString = getIntent.getStringExtra("memory");
        String cognitionString = getIntent.getStringExtra("cognition");

        int strength_point = Integer.parseInt(strengthString);
        int health_point = Integer.parseInt(healthString);
        int judgement_point = Integer.parseInt(judgementString);
        int memory_point = Integer.parseInt(memoryString);
        int cognition_point = Integer.parseInt(cognitionString);
        boolean flag=false;
        String aspects = "\t\t\t\t您可能存在的认知衰弱问题为";
        String detailed_health ="";
        String detailed_sport ="";
        String detailed_memory="";

        detailed_health+="<font color='#0000CD'>健康膳食建议：</font>";
        detailed_health+=this.getResources().getString(R.string.health_advice);

        detailed_sport +="<font color='#0000CD'>运动建议：</font>";
        detailed_sport +=this.getResources().getString(R.string.sport_advice);

        detailed_memory+="<font color='#0000CD'>提高记忆建议：</font>";
        detailed_memory+=this.getResources().getString(R.string.memory_advice);

        //对分数进行辨别，选出出现问题的一些健康方面
        if (health_point > 0) {
            aspects += "<font color='#FF0000'>健康状况</font>，";
            flag = true;
        }
        if (strength_point > 0) {
            aspects += "<font color='#FF0000'>体力</font>，";
            flag = true;
        }
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



        historyTestname.setText(testnameString);
        historyScore.setText(scoreString);
        historyTime.setText("测试时间：" + testdateString + " " + testtimeString);
        int score = Integer.parseInt(scoreString);
        if (testnameString.equals("FRAIL")) {
            if (score == 0) {
                historyAdvice.setText(R.string.FRAIL_healthy);
            } else if (score >= 3) {
                historyAdvice.setText(R.string.FRAIL_frailty);
            } else {
                historyAdvice.setText(R.string.FRAIL_pre_frailty);
            }
        } else if (testnameString.equals("AD-8")) {
            if (score < 2) {
                historyAdvice.setText(R.string.FRAIL_healthy);
            } else {
                historyAdvice.setText(R.string.FRAIL_frailty);
            }
        }

        //绘制雷达图
        radar = (RadarChart) findViewById(R.id.history_radar);
        list = new ArrayList<>();

        list.add(new RadarEntry((float) (2 - health_point) / 2 * 100));
        list.add(new RadarEntry((float) (3 - strength_point) / 3 * 100));
        list.add(new RadarEntry((float) (5 - memory_point) / 5 * 100));
        list.add(new RadarEntry((float) (1 - judgement_point) * 100));
        list.add(new RadarEntry((float) (4 - cognition_point) / 4 * 100));

        RadarDataSet radarDataSet = new RadarDataSet(list, "历史测试结果");
        RadarData radarData = new RadarData(radarDataSet);
        radar.setData(radarData);

        radarDataSet.setDrawFilled(true); // 绘制填充，默认为false
        if(testnameString.equals("FRAIL")) {
            radarDataSet.setColor(Color.parseColor("#00F5FF"));
            radarDataSet.setFillColor(Color.parseColor("#00F5FF"));

        }
        else if(testnameString.equals("AD-8")) {
            radarDataSet.setColor(Color.parseColor("#F08080"));
            radarDataSet.setFillColor(Color.parseColor("#F08080")); // 填充颜色
        }
        radarDataSet.setFillAlpha(51); // 填充内容透明度
        radarDataSet.setDrawValues(false);


        //Y轴最小值不设置会导致数据中最小值默认成为Y轴最小值
        radar.getYAxis().setAxisMinimum(0);
        radar.getXAxis().setAxisMaximum(90);
        //对X轴的文字进行定义
        XAxis xAxis = radar.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value == 0) {
                    return "健康状况";
                } else if (value == 1) {
                    return "体力";
                } else if (value == 2) {
                    return "记忆力";
                } else if (value == 3) {
                    return "判断力";
                } else if (value == 4) {
                    return "意识";
                } else {
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

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testnameString.equals("FRAIL")) {
                    Intent intent = new Intent(HistoryResult.this, FrailIntro.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HistoryResult.this, ADIntro.class);
                    startActivity(intent);
                }

            }
        });
    }
}

