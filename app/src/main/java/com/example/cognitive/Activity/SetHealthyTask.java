package com.example.cognitive.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
    private EditText etEverydayStep;
    private Button btnEverydayStep;
    private int getUpHour=8;
    private int getUpMinute=0;
    private int sleepHour=23;
    private int sleepMinute=0;
    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_healthy_task);
        btnGetUpTime= (Button) findViewById(R.id.btn_getUpTime);
        txtGetUpTime= (TextView) findViewById(R.id.txt_getUpTime);
        txtGetUpTime.setText("8时0分");
        btnGetUpTime.setOnClickListener(this);
        btnSleepTime= (Button) findViewById(R.id.btn_sleepTime);
        txtSleepTime= (TextView) findViewById(R.id.txt_sleepTime);
        txtSleepTime.setText("23时0分");
        btnSleepTime.setOnClickListener(this);
        etEverydayStep= (EditText) findViewById(R.id.et_everydayStep);
        btnEverydayStep= (Button) findViewById(R.id.btn_everydayStep);
        btnEverydayStep.setOnClickListener(this);

    }


    /**
     * 时间选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar,int hour, int minute) {
        final int[] getHour = new int[1];
        final int[] getMinute= new int[1];
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(hourOfDay + "时" + minute  + "分");
                        getHour[0] =hourOfDay;
                        getMinute[0]=minute;

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
        hour=getHour[0];
        minute=getMinute[0];
        Log.d("SetHealthyTask","选择的hour"+hour);
        Log.d("SetHealthyTask","选择的minute"+minute);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getUpTime:
                showTimePickerDialog(this,  3, txtGetUpTime, calendar,getUpHour,getUpMinute);
                Log.d(TAG,"getUpHour:"+getUpHour+"  getUpMinute:"+getUpMinute);
                break;
            case R.id.btn_sleepTime:
                showTimePickerDialog(this,  3, txtSleepTime, calendar,sleepHour,sleepMinute);
                Log.d(TAG,"sleepHour:"+sleepHour+"  sleepMinute:"+sleepMinute);
                break;
            case R.id.btn_everydayStep:
                String strEverydayStep=etEverydayStep.getText().toString();
                int intEverydayStep=Integer.parseInt(strEverydayStep);
                Log.d(TAG,"每日目标步数："+intEverydayStep);
                etEverydayStep.clearFocus();
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
