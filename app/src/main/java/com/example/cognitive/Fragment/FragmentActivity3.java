package com.example.cognitive.Fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cognitive.R;

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
    private boolean isVisible = true;

    /*private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener stepCounterListener;*/
    private TextView step_num;
    private String TAG = "get step";
    private SensorManager mSensorManager;
    private static final String[] permissions = {Manifest.permission.ACTIVITY_RECOGNITION};
    //步数的一堆东西

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
        //获取星期
        get_weekday=view.findViewById(R.id.get_weekday);
        String weekDay=getWeekDay();
        get_weekday.setText("星期"+weekDay);

        //获取步数
        step_num=view.findViewById(R.id.step_num);
        /*sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(sensor==null){
            Log.e("sensor","没有sensor");
        }
        stepCounterListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float count = event.values[0];
                step_num.setText("总步伐计数:"+count);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) { }
        };
        sensorManager.registerListener(stepCounterListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);*/

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



        startSensor();


        //step_num.setText("今天走了"+sensor.toString()+"步");



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

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        private float step, stepDetector;
        private int intStep;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            /**
             * 计步计数传感器传回的历史累积总步数
             */
            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                step = sensorEvent.values[0];
                Log.d(TAG, "STEP_COUNTER:" + step);
                intStep=(int)step;
                step_num.setText("今天走了"+intStep+"步");
            }

            /**
             * 计步检测传感器检测到的步行动作是否有效？
             */
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


}