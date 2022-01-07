package com.example.cognitive.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;
import com.example.cognitive.Utils.AppManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;

/*public class DialogPowerSport extends AppCompatActivity {
    private SharedPreferences spFinishTask;
    private SharedPreferences spWeekReport;
    private LinearLayout llSportInfo;
    private FancyButton btnPowerSportCancel;
    private FancyButton btnPowerSportFinish;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_power_sport);
        spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        spWeekReport=this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        llSportInfo=findViewById(R.id.ll_sport_info);
        btnPowerSportCancel=findViewById(R.id.btn_power_sport_cancel);
        llSportInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialogPowerSport.this, SportInfo.class);
                startActivity(intent);
            }
        });
        btnPowerSportCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogPowerSport.this.finish();
            }
        });
        //完成一项打卡
        btnPowerSportFinish=findViewById(R.id.btn_power_sport_finish);
        btnPowerSportFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("powerSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                int sportReal=spWeekReport.getInt("sportReal",0);
                sportReal=sportReal+1;
                SharedPreferences.Editor editor1 = spWeekReport.edit();
                editor1.putInt("sportReal",sportReal);
                int powerReal=spWeekReport.getInt("powerReal",0);
                powerReal=powerReal+1;
                editor1.putInt("powerReal",powerReal);
                Calendar instance = Calendar.getInstance();
                int weekDay = instance.get(Calendar.DAY_OF_WEEK);
                if(weekDay==1){
                    editor1.putInt("SunReal",1);
                } else if(weekDay==2) {
                    editor1.putInt("MonReal",1);
                } else if(weekDay==3) {
                    editor1.putInt("TueReal",1);
                } else if(weekDay==4) {
                    editor1.putInt("WenReal",1);
                } else if(weekDay==5) {
                    editor1.putInt("ThurReal",1);
                } else if(weekDay==6) {
                    editor1.putInt("FriReal",1);
                } else if(weekDay==7) {
                    editor1.putInt("SatReal",1);
                }
                editor1.putString("endDate",simpleDateFormat.format(date));
                if(spWeekReport.getString("beginDate",null)==null){
                    editor1.putString("beginDate",simpleDateFormat.format(date));
                }
                editor1.commit();
                Intent intent = new Intent(DialogPowerSport.this, FinishHealthyTask.class);
                AppManager.getAppManager().finishActivity(FinishHealthyTask.class);
                startActivity(intent);
                finish();

                //DialogDiary.this.finish();
            }
        });
    }
}*/
public class DialogPowerSport extends Dialog {
    private SharedPreferences spFinishTask;
    private SharedPreferences spWeekReport;
    private LinearLayout llSportInfo;
    private FancyButton btnPowerSportCancel;
    private FancyButton btnPowerSportFinish;
    public interface DataBackListener{
        public void getData(int data);
    }
    DialogPowerSport.DataBackListener listener;
    public DialogPowerSport(Context context, final DialogPowerSport.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_power_sport);
        llSportInfo=findViewById(R.id.ll_sport_info);
        btnPowerSportCancel=findViewById(R.id.btn_power_sport_cancel);
        /*llSportInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DialogPowerSport.this, SportInfo.class);
                startActivity(intent);
            }
        });*/
        btnPowerSportCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //完成一项打卡
        btnPowerSportFinish=findViewById(R.id.btn_power_sport_finish);
        btnPowerSportFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.getData(1);
                dismiss();
                //DialogDiary.this.finish();
            }
        });
    }
}
