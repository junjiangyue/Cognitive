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


public class FrailResultPage extends AppCompatActivity {
    public TextView showScore;
    public TextView frailAdvice;
    public static FrailResultPage ResultPage;
    //雷达图
    private RadarChart radar;
    //项目表
    List<RadarEntry> list;
    //存分数
    private SharedPreferences spTestScore;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frail_result_page);
            spTestScore= this.getSharedPreferences("userTestScore", Context.MODE_PRIVATE);
            ResultPage=this;
            setScore();
            FrailTest.Test.finish();
            FrailIntro.introPage.finish();
        }

        public void setScore()
        {
            showScore=findViewById(R.id.frail_score);
            frailAdvice=findViewById(R.id.frail_advice);

            TextView showScore = findViewById(R.id.frail_score);
            TextView frailAdvice = findViewById(R.id.frail_advice);

            Intent getIntent=getIntent();
            String scoreString=getIntent.getStringExtra("score");
            String strengthString=getIntent.getStringExtra("strength_point");
            String healthString=getIntent.getStringExtra("health_point");
            int score=Integer.parseInt(scoreString);
            int strength_point=Integer.parseInt(strengthString);
            int health_point=Integer.parseInt(healthString);
            SharedPreferences.Editor editor = spTestScore.edit();
            editor.putInt("strengthScore", strength_point);
            editor.putInt("healthScore", health_point);
            editor.commit();

            showScore.setText(scoreString+"分");
            //showScore.setText(healthString+"分");
            if(score==0)
            {
                frailAdvice.setText(R.string.FRAIL_healthy);
            }
            else if(score>=3)
            {
                frailAdvice.setText(R.string.FRAIL_frailty);
            }
            else
            {
                frailAdvice.setText(R.string.FRAIL_pre_frailty);
            }

            //雷达图的绘制
            radar = (RadarChart) findViewById(R.id.frail_radar);
            list=new ArrayList<>();

            list.add(new RadarEntry((float)(3-strength_point)/3*100));
            list.add(new RadarEntry((float)(2-health_point)/2*100));
            list.add(new RadarEntry(100));
            list.add(new RadarEntry(100));
            list.add(new RadarEntry(100));

            RadarDataSet radarDataSet=new RadarDataSet(list,"frail测试结果");
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
                        return "体力 ";
                    }
                    else if(value==1)
                    {
                        return "健康状况";
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
            Intent intent=new Intent(FrailResultPage.this, SetTask.class);
            //finish();
            startActivity(intent);
            finish();
        }
    }