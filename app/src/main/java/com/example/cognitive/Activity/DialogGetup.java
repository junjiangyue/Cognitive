package com.example.cognitive.Activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cognitive.R;
import com.example.cognitive.Utils.AppManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;

/*public class DialogGetup extends AppCompatActivity {
    private SharedPreferences sp1;//打卡设置
    private SharedPreferences spFinishTask;
    private SharedPreferences spWeekReport;
    private FancyButton btnGetupFinish;
    private FancyButton btnGetupCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_getup);
        sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        spWeekReport=this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        btnGetupCancel=findViewById(R.id.btn_getup_cancel);
        btnGetupCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogGetup.this.finish();
            }
        });
        //完成一项打卡
        btnGetupFinish=findViewById(R.id.btn_getup_finish);
        btnGetupFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                Date now=new Date(System.currentTimeMillis());
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putString("getupFinish", simpleDateFormat1.format(now));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                //存周报
                String getupTime= sp1.getString("getupTime","7:00");
                getupTime=getupTime.substring(0,getupTime.length()-3);
                int getupReal=spWeekReport.getInt("getupReal",0);
                if(isDate2Bigger(simpleDateFormat1.format(now),getupTime)){
                    getupReal=getupReal+1;
                }
                SharedPreferences.Editor editor1 = spWeekReport.edit();
                editor1.putInt("getupReal",getupReal);
                editor1.putString("endDate",simpleDateFormat.format(date));
                if(spWeekReport.getString("beginDate",null)==null){
                    editor1.putString("beginDate",simpleDateFormat.format(date));
                }
                editor1.commit();
                Intent intent = new Intent(DialogGetup.this, FinishHealthyTask.class);
                AppManager.getAppManager().finishActivity(FinishHealthyTask.class);
                startActivity(intent);
                finish();

                //DialogDiary.this.finish();
            }
        });
    }
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }
}*/
public class DialogGetup extends Dialog {
    private SharedPreferences sp1;//打卡设置
    private SharedPreferences spFinishTask;
    private SharedPreferences spWeekReport;
    private FancyButton btnGetupFinish;
    private FancyButton btnGetupCancel;
    public interface DataBackListener{
        public void getData(int data);
    }
    DialogGetup.DataBackListener listener;
    public DialogGetup(Context context, final DialogGetup.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_getup);
        btnGetupCancel=findViewById(R.id.btn_getup_cancel);
        btnGetupCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //完成一项打卡
        btnGetupFinish=findViewById(R.id.btn_getup_finish);
        btnGetupFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.getData(1);

                dismiss();

                //DialogDiary.this.finish();
            }
        });
    }
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }
}