package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cognitive.Activity.FinishHealthyTask;
import com.example.cognitive.Activity.SetHealthyTask;
import com.example.cognitive.R;
import com.example.cognitive.SQLiteDB.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class FragmentActivity3 extends Fragment {
    private LinearLayout title_test;
    private TextView get_date;
    private TextView get_weekday;
    private LinearLayout expandable_part;
    private TextView unfold_picture;
    private LinearLayout manage_disease;
    private LinearLayout disease;
    private TextView history_step;
    private TextView health_task;
    private TextView finish_health_task;
    private boolean isVisible = true;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener stepCounterListener;
    private TextView step_num;
    private static String TAG = "健康打卡";
    private SensorManager mSensorManager;
    private static final String[] permissions = {Manifest.permission.ACTIVITY_RECOGNITION};
    //步数的一堆东西
    private DatabaseHelper dbHelper;
    private int intStep;
    private int yesterdayStep;
    private int todayStep;
    private boolean result;
    int id;
    String stepTime;
    int stepNum;
    String todayDate;
    //0点广播
    //private ZeroBroadcastReceiver zeroBcReceiver;

    //@Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment3,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        title_test=view.findViewById(R.id.title_test);

        //获取日期
        get_date=view.findViewById(R.id.get_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        get_date.setText(simpleDateFormat.format(date));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        todayDate=formatter.format(date);
        Log.d(TAG,todayDate);
        //获取星期
        get_weekday=view.findViewById(R.id.get_weekday);
        String weekDay=getWeekDay();
        get_weekday.setText("星期"+weekDay);

        //获取步数
        step_num=view.findViewById(R.id.step_num);

        //获取权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.d(TAG, "[权限]" + "ACTIVITY_RECOGNITION 未获得");
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                // 检查权限状态
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACTIVITY_RECOGNITION)) {
                    //  用户彻底拒绝授予权限，一般会提示用户进入设置权限界面
                    Log.d(TAG, "[权限]" + "ACTIVITY_RECOGNITION 以拒绝，需要进入设置权限界面打开");
                } else {
                    //  用户未彻底拒绝授予权限
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
                    Log.d(TAG, "[权限]" + "ACTIVITY_RECOGNITION 未彻底拒绝拒绝，请求用户同意");
                }
//                return;
            }else{

                Log.d(TAG, "[权限]" + "ACTIVITY_RECOGNITION ready");
            }
        }else{

        }

        //开启传感器
        startSensor();
        //数据库操作
        dbHelper=new DatabaseHelper(this.getContext(),"database1",null,2);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor= db.query("Step",null,null,null,null,null,null);
        if(cursor.getCount()==0){ //表内无数据，则插入数据
            ContentValues values=new ContentValues();
            values.put("id",1);
            values.put("step_num",0);
            values.put("step_time",todayDate);
            db.insert("Step",null,values);
            values.clear();
            values.put("id",2);
            values.put("step_num",0);
            values.put("step_time",todayDate);
            db.insert("Step",null,values);
            values.clear();
            Log.d(TAG,"插入");
            //cursor.close();
        }
        /*ContentValues values=new ContentValues();
        values.put("step_num",1000);
        values.put("step_time","2021-12-09");
        db.update("Step",values,"id=?", new String[] {"2"});
        values.clear();
        Log.d(TAG,"更新");*/
        if(cursor.moveToFirst()){ //查询数据库所有步数
            do{
                id=cursor.getInt(cursor.getColumnIndex("id"));
                stepNum=cursor.getInt(cursor.getColumnIndex("step_num"));
                stepTime=cursor.getString(cursor.getColumnIndex("step_time"));
                Log.d(TAG,"id:"+id);
                Log.d(TAG,"step_num:"+stepNum);
                Log.d(TAG,"step_time:"+stepTime);
            } while (cursor.moveToNext());
            Log.d(TAG,"查询数据库中所有数据");
        }



        //查今日数据
        cursor = db.query("Step",null,"id=?", new String[] {"2"},null,null,null);
        cursor.moveToFirst();
        id=cursor.getInt(cursor.getColumnIndex("id"));
        stepNum=cursor.getInt(cursor.getColumnIndex("step_num"));
        stepTime=cursor.getString(cursor.getColumnIndex("step_time"));
        Log.d(TAG,"id:"+id);
        Log.d(TAG,"step_num:"+stepNum);
        Log.d(TAG,"step_time:"+stepTime);
        result=isDate2Bigger(stepTime,todayDate);
        Log.d(TAG,"比较2的日期result:"+result);
        if(result==true){ //新的一天，把2的步数与日期更新到1
            ContentValues values=new ContentValues();
            values.put("step_num",stepNum);
            values.put("step_time",stepTime);
            db.update("Step",values,"id=?", new String[] {"1"});
            values.clear();
            Log.d(TAG,"更新1");
        }
        //查昨日数据
        cursor = db.query("Step",null,"id=?", new String[] {"1"},null,null,null);
        cursor.moveToFirst();
        id=cursor.getInt(cursor.getColumnIndex("id"));
        stepNum=cursor.getInt(cursor.getColumnIndex("step_num"));
        stepTime=cursor.getString(cursor.getColumnIndex("step_time"));
        Log.d(TAG,"id:"+id);
        Log.d(TAG,"step_num:"+stepNum);
        Log.d(TAG,"step_time:"+stepTime);
        yesterdayStep=stepNum;
        Log.d(TAG,"查询后yesterdayStep:"+yesterdayStep);
        cursor.close();


        //我的步数图片的展开与收起
        expandable_part=view.findViewById(R.id.expandable_part);
        expandable_part.setVisibility(View.GONE);
        unfold_picture=view.findViewById(R.id.unfold_picture);
        unfold_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    expandable_part.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
                    unfold_picture.setText("收起图片");
                } else {
                    expandable_part.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
                    unfold_picture.setText("展开图片");
                    isVisible = true;
                }
            }
        });

        //慢性病管理的展开与收起
        manage_disease=view.findViewById(R.id.manage_disease);
        manage_disease.setVisibility(View.GONE);
        disease=view.findViewById(R.id.disease);
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    manage_disease.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
                } else {
                    manage_disease.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
                    isVisible = true;
                }
            }
        });

        //跳转历史步数
        history_step=view.findViewById(R.id.history_step);
        history_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, new EverydayStepNum(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        //跳转设置健康打卡
        health_task=view.findViewById(R.id.health_task);
        health_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(),SetHealthyTask.class);
                startActivity(intent);
            }
        });

        //跳转健康打卡
        finish_health_task=view.findViewById(R.id.finish_health_task);
        finish_health_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(), FinishHealthyTask.class);
                startActivity(intent);
            }
        });

    }

    //获取星期
    public String getWeekDay(){
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        if(weekDay==1){
            return "天";
        } else if(weekDay==2) {
            return "一";
        } else if(weekDay==3) {
            return "二";
        } else if(weekDay==4) {
            return "三";
        } else if(weekDay==5) {
            return "四";
        } else if(weekDay==6) {
            return "五";
        } else if(weekDay==7) {
            return "六";
        } else {
            return null;
        }
    }

    //开启传感器
    private void startSensor() {
        mSensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);

        Sensor mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (mSensorManager == null || mStepCounterSensor == null || mStepDetectorSensor == null) {
            throw new UnsupportedOperationException("设备不支持");
        }

        mSensorManager.registerListener(mSensorEventListener, mStepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //传感器事件监听
    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        private float step, stepDetector;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

             //计步计数传感器传回的历史累积总步数

            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                step = sensorEvent.values[0];
                Log.d(TAG, "STEP_COUNTER:" + step);
                intStep=(int)step;
                Log.d(TAG, "传感器的Step:" + intStep);

                //更新数据库2信息
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("step_num",intStep);
                values.put("step_time",todayDate);
                db.update("Step",values,"id=?", new String[] {"2"});
                values.clear();
                Log.d(TAG,"更新2");
                if(intStep<yesterdayStep) {
                    todayStep=intStep;
                } else {
                    todayStep=intStep-yesterdayStep;
                }
                Log.d(TAG, "计算的Step:" + todayStep);
                step_num.setText("今天走了"+todayStep+"步");
                timedBroadcast();
            }


             //计步检测传感器检测到的步行动作是否有效？

            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                stepDetector = sensorEvent.values[0];
                Log.d(TAG, "STEP_DETECTOR:" + stepDetector);
                if (stepDetector == 1.0) {
                    Log.d(TAG, "一次有效的步行");
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 申请成功
                    Log.d(TAG, "[权限]" + "ACTIVITY_RECOGNITION 申请成功");
                } else {
                    // 申请失败
                    Log.d(TAG, "[权限]" + "ACTIVITY_RECOGNITION 申请失败");
                }
            }
        }

    }


    public void timedBroadcast() {
        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE,34);
        calendar.set(Calendar.SECOND,00);
        Intent intent=new Intent("zero_store_step");
        intent.addFlags(0x01000000);
        //intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("intStep",intStep);
        PendingIntent pi=PendingIntent.getBroadcast(this.getContext(),0,intent,0);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        //am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() +10 * 1000,  AlarmManager.INTERVAL_DAY, pi);

    }

    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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