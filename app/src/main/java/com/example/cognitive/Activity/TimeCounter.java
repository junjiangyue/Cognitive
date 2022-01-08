package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCounter extends AppCompatActivity implements View.OnClickListener, Chronometer.OnChronometerTickListener{
    private int Durtime = 3;

    private int leftTime = Durtime;

    private Chronometer chronometer;
    private Button btn_start,btn_stop,btn_finish,btn_spotTask;
    private TextView txt_timeFinish,txt_taskName,txt_taskTime;
    String taskName;
    int taskTime,finishTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_counter);
        Intent intent=getIntent();
        taskName=intent.getStringExtra("taskName");
        taskTime=intent.getIntExtra("taskTime",0);
        finishTime=intent.getIntExtra("finishTime",0);
        Durtime=taskTime-finishTime;
        leftTime = Durtime;
        initView();
    }
    private void initView() {
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        btn_start = (Button) findViewById(R.id.btnStart);
        btn_stop = (Button) findViewById(R.id.btnStop);
        btn_finish = (Button) findViewById(R.id.btnFinish);
        btn_spotTask = (Button) findViewById(R.id.btnStopTask);
        txt_timeFinish = (TextView) findViewById(R.id.txt_timeFinish);
        txt_taskName = (TextView) findViewById(R.id.txt_taskName);
        txt_taskName.setText(taskName);
        txt_taskTime = (TextView) findViewById(R.id.txt_taskTime);
        int taskMinute=taskTime/60;
        txt_taskTime.setText("总计："+taskMinute+"分钟");
        chronometer.setOnChronometerTickListener(this);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        btn_spotTask.setOnClickListener(this);
        setChronometerText();

    }
    public void setChronometerText()
    {
        Date d = new Date(leftTime * 1000);

        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");

        chronometer.setText(timeFormat.format(d));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                chronometer.start();// 开始计时
                break;
            case R.id.btnStop:
                chronometer.stop(); // 停止计时
                break;
            case R.id.btnFinish:
                //leftTime=0;
                if(leftTime!=0) {
                    Toast.makeText(TimeCounter.this,"时间不足，还不能完成打卡哦！",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(TimeCounter.this,"打卡成功！",Toast.LENGTH_SHORT).show();
                    chronometer.stop(); // 停止计时
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish(); //结束当前的activity的生命周期
                }
                //
                //chronometer.setBase(SystemClock.elapsedRealtime());// 复位
                //leftTime = Durtime;
                //setChronometerText();
                break;
            case R.id.btnStopTask:
                chronometer.stop(); // 停止计时
                Intent intent = new Intent();
                // 获取用户计算后的结果
                int finishTime=Durtime-leftTime;
                intent.putExtra("finishTime", finishTime); //将计算的值回传回去
                // 通过intent对象返回结果，必须要调用一个setResult方法，
                // setResult(888, data);第一个参数表示结果返回码，一般只要大于1就可以
                setResult(2, intent);
                finish(); //结束当前的activity的生命周期
        }
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        leftTime--;
        setChronometerText();

        if(leftTime == 0 ){
            txt_timeFinish.setVisibility(View.VISIBLE);
            //Toast.makeText(TimeCounter.this,"时间到了~",Toast.LENGTH_SHORT).show();

            chronometer.stop();
            return;
        }
    }
}
