package com.example.cognitive.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SetHealthyTask extends AppCompatActivity implements View.OnClickListener {

    private String TAG="SetHealthyTask";
    private TextView txtGetUpTime;
    private Button btnGetUpTime;
    private TextView txtSleepTime;
    private Button btnSleepTime;
    private TextView txtSetStep;
    private Button btnEverydayStep;
    private TextView txtSportTime;
    private Button btnEveryWeekSport;
    private TextView txtPowerTime;
    private Button btnEveryWeekPower;
    private CheckBox cbDrinkWater;
    private int getUpHour=8;
    private int getUpMinute=0;
    private int sleepHour=23;
    private int sleepMinute=0;
    private int stepGoal;
    private int sportTime;
    private int powerTime;
    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_healthy_task);
        btnGetUpTime= (Button) findViewById(R.id.btn_getUpTime);
        txtGetUpTime= (TextView) findViewById(R.id.txt_getUpTime);
        txtGetUpTime.setText("8:00");
        btnGetUpTime.setOnClickListener(this);
        btnSleepTime= (Button) findViewById(R.id.btn_sleepTime);
        txtSleepTime= (TextView) findViewById(R.id.txt_sleepTime);
        txtSleepTime.setText("23:00");
        btnSleepTime.setOnClickListener(this);
        txtSetStep= (TextView) findViewById(R.id.txt_setStep);
        btnEverydayStep= (Button) findViewById(R.id.btn_everydayStep);
        btnEverydayStep.setOnClickListener(this);
        txtSportTime= (TextView) findViewById(R.id.txt_setSportTime);
        btnEveryWeekSport= (Button) findViewById(R.id.btn_everyWeekSport);
        btnEveryWeekSport.setOnClickListener(this);
        txtPowerTime= (TextView) findViewById(R.id.txt_setPowerTime);
        btnEveryWeekPower= (Button) findViewById(R.id.btn_everyWeekPower);
        btnEveryWeekPower.setOnClickListener(this);
        cbDrinkWater=(CheckBox) findViewById(R.id.cb_drinkWater);

        cbDrinkWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbDrinkWater.setText("已选择");
                    Log.d(TAG,"选择喝水");
                }else{
                    cbDrinkWater.setText("选择");
                    Log.d(TAG,"不选择喝水");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getUpTime:
                TimePickerDialog dialog=new TimePickerDialog(this,3, new TimePickerDialog.OnTimeSetListener() {
//实现响应用户单击set按钮的事件方法
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        getUpHour = hourOfDay;
                        getUpMinute = minute;
                        if (getUpMinute < 10){
                            txtGetUpTime.setText(getUpHour+":"+"0"+getUpMinute);
                        }else {
                            txtGetUpTime.setText(getUpHour+":"+getUpMinute);
                        }
                        Log.d(TAG,"getUpHour:"+getUpHour+" getUpMinute:"+getUpMinute);
                    }

                }, 8, 0, true);
                dialog.show();
                break;
            case R.id.btn_sleepTime:
                TimePickerDialog dialog1=new TimePickerDialog(this,3, new TimePickerDialog.OnTimeSetListener() {
                    //实现响应用户单击set按钮的事件方法
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        sleepHour = hourOfDay;
                        sleepMinute = minute;
                        if (sleepMinute < 10){
                            txtSleepTime.setText(sleepHour+":"+"0"+sleepMinute);
                        }else {
                            txtSleepTime.setText(sleepHour+":"+sleepMinute);
                        }
                        Log.d(TAG,"sleepHour:"+sleepHour+" sleepMinute:"+sleepMinute);
                    }

                }, 23, 0, true);
                dialog1.show();
                break;
            case R.id.btn_everydayStep:
                final String items[] = {"5000", "6000", "7000","8000","9000","10000"};
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("请选择您的目标步数");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String everydayStep=items[which];
                        stepGoal=Integer.parseInt(everydayStep);
                    }});
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        txtSetStep.setText("每日行走"+stepGoal+"步");
                        Log.d(TAG,"每日目标步数："+stepGoal);
                        dialog.dismiss();

                    }});
                builder.create().show();
                break;
            case R.id.btn_everyWeekSport:
                final String items1[] = {"3", "4", "5", "6","7"};
                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setTitle("请选择您的目标运动次数");
                builder1.setSingleChoiceItems(items1, -1, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str_sportTime=items1[which];
                        sportTime=Integer.parseInt(str_sportTime);
                    }});
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        txtSportTime.setText("每周运动"+sportTime+"次");
                        Log.d(TAG,"目标运动次数："+sportTime);
                        dialog.dismiss();

                    }});
                builder1.create().show();
                break;
            case R.id.btn_everyWeekPower:
                final String items2[] = {"1", "2", "3"};
                AlertDialog.Builder builder2=new AlertDialog.Builder(this);
                builder2.setTitle("请选择您的目标力量训练次数");
                builder2.setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strPowerTime=items2[which];
                        powerTime=Integer.parseInt(strPowerTime);
                    }});
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        txtPowerTime.setText("其中力量训练"+powerTime+"次");
                        Log.d(TAG,"目标力量训练次数："+powerTime);
                        dialog.dismiss();
                    }});
                builder2.create().show();
                break;
            default:
                break;
        }

    }


    //dispatchTouchEvent函数根据点击位置判断是否需要隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            View currentFocus = getCurrentFocus();
            if(IsShouldHideKeyboard(currentFocus,ev)) {
                HideKeyboard(currentFocus);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    //IsShouldHideKeyboard是是否需要隐藏键盘的实现
    private boolean IsShouldHideKeyboard(View view, MotionEvent event) {
        if((view instanceof EditText)) {
            int[] coord = {0,0};
            view.getLocationInWindow(coord);
            int left = coord[0], top = coord[1], right = left + view.getWidth(), bottom = top + view.getHeight();
            int evX = (int) event.getRawX(), evY = (int) event.getRawY();
            return !((left <= evX && evX <= right) && (top <= evY && evY <= bottom));
        }
        return false;
    }

    //HideKeyboard函数实现键盘的隐藏
    public  void HideKeyboard(View v) {
        InputMethodManager manager = ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE));

        if (manager != null)
            manager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        v.clearFocus();
    }
}
