package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cognitive.Activity.ChangeNameDialog;
import com.example.cognitive.Activity.DialogDiary;
import com.example.cognitive.Activity.DialogGetup;
import com.example.cognitive.Activity.DialogHobby;
import com.example.cognitive.Activity.DialogOtherSport;
import com.example.cognitive.Activity.DialogPowerSport;
import com.example.cognitive.Activity.DialogRead;
import com.example.cognitive.Activity.DialogSleep;
import com.example.cognitive.Activity.DialogSmile;
import com.example.cognitive.Activity.DialogWalk;
import com.example.cognitive.Activity.DialogWater;
import com.example.cognitive.Activity.FinishHealthyTask;
import com.example.cognitive.Activity.HistoryStep;
import com.example.cognitive.Activity.LoginActivity;
import com.example.cognitive.Activity.PersonalSetting;
import com.example.cognitive.Activity.SetHealthyTask;
import com.example.cognitive.Activity.TimeCounter;
import com.example.cognitive.Activity.WalkingActivity;
import com.example.cognitive.Activity.WeeklyExercise;
import com.example.cognitive.Activity.WeeklyReport;
import com.example.cognitive.R;
import com.example.cognitive.SQLiteDB.DatabaseHelper;
import com.example.cognitive.Utils.DestroyActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class FragmentActivity3 extends Fragment {
    private SharedPreferences sp;
    private SharedPreferences sp1;//打卡设置
    private SharedPreferences spFinishTask;//完成打卡
    private SharedPreferences spStepTask;
    private HashMap<String, String> stringHashMap;
    private HashMap<String, String> stringHashMap1;
    private int userID;
    private LinearLayout title_test;
    private TextView get_date;
    private TextView get_weekday;
    private TextView history_step;
    private boolean isVisible = true;
    private LinearLayout llDailyTask;
    private LinearLayout llSport;
    private LinearLayout llWeeklyReport;
    private TextView txtDistance;
    private TextView txtCalorie;
    private TextView txtSetTip;
    private CardView cvGetup;
    private TextView txtGetupReal;
    private TextView txtGetupGoal;
    private ImageView imgGetup;
    private CardView cvSleep;
    private TextView txtSleepReal;
    private TextView txtSleepGoal;
    private ImageView imgSleep;
    private CardView cvStep;
    private TextView txtStepReal;
    private TextView txtStepGoal;
    private ImageView imgStep;
    private CardView cvWater;
    private TextView txtWaterReal;
    private TextView txtWaterGoal;
    private ImageView imgWater;
    private CardView cvRead;
    private TextView txtReadReal;
    private TextView txtReadGoal;
    private ImageView imgRead;
    private CardView cvHobby;
    private TextView txtHobbyReal;
    private TextView txtHobbyGoal;
    private ImageView imgHobby;
    private CardView cvSmile;
    private TextView txtSmileReal;
    private TextView txtSmileGoal;
    private ImageView imgSmile;
    private CardView cvDiary;
    private TextView txtDairyReal;
    private TextView txtDairyGoal;
    private ImageView imgDiary;
    private CardView cvPowerSport;
    private ImageView imgPowerSport;
    private CardView cvOtherSport;
    private ImageView imgOtherSport;
    private SensorManager sensorManager;
    private Button start;
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
    int yestSQLStep1;
    int yestSQLStep2;
    int yestSQLStep;
    String yestTime;
    private double distance;
    private int calorie;
    private String getupTime;
    private String sleepTime;
    private int postStepGoal;
    private int drinkGoal;
    private int readGoal;
    private int hobbyGoal;
    private int smileGoal;
    private int diaryGoal;
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
        DestroyActivityUtil.addActivity(getActivity());
        stringHashMap = new HashMap<>();
        stringHashMap1 = new HashMap<>();
        spStepTask=getActivity().getSharedPreferences("userStepTask", Context.MODE_PRIVATE);
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
        //初始化图片
        imgRead=view.findViewById(R.id.img_read);
        imgHobby=view.findViewById(R.id.img_hobby);
        imgPowerSport=view.findViewById(R.id.img_power_sport);
        imgOtherSport=view.findViewById(R.id.img_other_sport);
        //获取步数
        step_num=view.findViewById(R.id.step_num);

        llDailyTask=view.findViewById(R.id.ll_daily_task);
        llDailyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(), FinishHealthyTask.class);
                startActivity(intent);
            }
        });
        llSport=view.findViewById(R.id.ll_sport);
        llSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(), WeeklyExercise.class);
                startActivity(intent);
            }
        });
        llWeeklyReport=view.findViewById(R.id.ll_weekly_report);
        llWeeklyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(), WeeklyReport.class);
                startActivity(intent);
            }
        });

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
        else if(cursor.getCount()==1) {
            ContentValues values=new ContentValues();
            values.put("id",2);
            values.put("step_num",0);
            values.put("step_time",todayDate);
            db.insert("Step",null,values);
            values.clear();
            Log.d(TAG,"插入");
        }
        if(cursor.moveToFirst()){ //查询数据库所有步数
            do{
                id=cursor.getInt(cursor.getColumnIndex("id"));
                stepNum=cursor.getInt(cursor.getColumnIndex("step_num"));
                stepTime=cursor.getString(cursor.getColumnIndex("step_time"));
                Log.d(TAG,"id:"+id);
                Log.d(TAG,"step_num:"+stepNum);
                Log.d(TAG,"step_time:"+stepTime);
                if(id==1) {
                    yestSQLStep1=stepNum;
                    Log.d(TAG,"yestSQLStep1:"+yestSQLStep1);
                } else if(id==2) {
                    yestSQLStep2=stepNum;
                    Log.d(TAG,"yestSQLStep2:"+yestSQLStep2);
                }
            } while (cursor.moveToNext());
            Log.d(TAG,"查询数据库中所有数据");
        }

        // 走路定位
        start = view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalkingActivity.class);
                startActivity(intent);
            }
        });
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
        yestTime=stepTime;
        Log.d(TAG,"id:"+id);
        Log.d(TAG,"step_num:"+stepNum);
        Log.d(TAG,"step_time:"+stepTime);
        yesterdayStep=stepNum;
        Log.d(TAG,"查询后yesterdayStep:"+yesterdayStep);
        cursor.close();


        //跳转历史步数
        history_step=view.findViewById(R.id.step_num);
        history_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(getActivity(), HistoryStep.class);
                startActivity(intent);
            }
        });

        //距离
        txtDistance=view.findViewById(R.id.txt_distance);
        //卡路里
        txtCalorie=view.findViewById(R.id.txt_calorie);

        if(result==true){
            yestSQLStep=yestSQLStep2-yestSQLStep1;
            if(yestSQLStep<0) {
                yestSQLStep=yestSQLStep2;
            }
            Log.d(TAG,"yestSQLStep:"+yestSQLStep);
            sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            userID=sp.getInt("USER_ID",0);
            Log.d(TAG,"userID:"+userID);
            stringHashMap.put("userID", String.valueOf(userID));
            stringHashMap.put("stepNum",String.valueOf(yestSQLStep));
            stringHashMap.put("stepDate",yestTime);
            new Thread(postRun).start();
        }

        //打卡显示
        //获取打卡项目设置
        sp1= getActivity().getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        getupTime= sp1.getString("getupTime","7:00");
        getupTime=getupTime.substring(0,getupTime.length()-3);
        Log.d(TAG,"getupTime:"+getupTime);
        sleepTime= sp1.getString("sleepTime","23:00");
        sleepTime=sleepTime.substring(0,sleepTime.length()-3);
        Log.d(TAG,"sleepTime:"+sleepTime);
        postStepGoal = sp1.getInt("stepGoal",7000);
        Log.d(TAG,"postStepGoal:"+postStepGoal);
        drinkGoal = sp1.getInt("drinkGoal",0);
        Log.d(TAG,"drinkGoal:"+drinkGoal);
        readGoal = sp1.getInt("readGoal",0);
        Log.d(TAG,"readGoal:"+readGoal);
        hobbyGoal = sp1.getInt("hobbyGoal",0);
        Log.d(TAG,"hobbyGoal:"+hobbyGoal);
        smileGoal = sp1.getInt("smileGoal",0);
        Log.d(TAG,"smileGoal:"+smileGoal);
        diaryGoal = sp1.getInt("diaryGoal",0);
        Log.d(TAG,"diaryGoal:"+diaryGoal);
        boolean setOrNot= sp1.getBoolean("setOrNot", false);

        //打卡项目显示
        cvGetup=(CardView) view.findViewById(R.id.cv_getup);
        txtGetupReal= (TextView) view.findViewById(R.id.txt_GetupReal);
        //txtGetupReal.setText("6:45");
        txtGetupGoal= (TextView) view.findViewById(R.id.txt_GetupGoal);
        txtGetupGoal.setText(" / "+getupTime);
        cvSleep=(CardView) view.findViewById(R.id.cv_sleep);
        txtSleepReal= (TextView) view.findViewById(R.id.txt_SleepReal);
        //txtSleepReal.setText("22:45");
        txtSleepGoal= (TextView) view.findViewById(R.id.txt_SleepGoal);
        txtSleepGoal.setText(" / "+sleepTime);
        cvStep=(CardView) view.findViewById(R.id.cv_step);
        txtStepReal= (TextView) view.findViewById(R.id.txt_StepReal);
        //txtStepReal.setText("4567");
        int step;
        step=spStepTask.getInt("stepFinish",0);
        txtStepReal.setText(String.valueOf(step));
        txtStepGoal= (TextView) view.findViewById(R.id.txt_StepGoal);
        txtStepGoal.setText(" / "+postStepGoal);
        cvWater=(CardView) view.findViewById(R.id.cv_water);
        txtWaterReal= (TextView) view.findViewById(R.id.txt_WaterReal);
        //txtWaterReal.setText("1.5L");
        txtWaterGoal= (TextView) view.findViewById(R.id.txt_WaterGoal);
        txtWaterGoal.setText(" / 1.5L");
        /*if(drinkGoal==1) {
            cvWater.setVisibility(View.GONE);
        }*/
        cvRead=(CardView) view.findViewById(R.id.cv_read);
        txtReadReal= (TextView) view.findViewById(R.id.txt_ReadReal);
        SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
        int readTime=spTaskTime.getInt("readTime",0);
        if(readTime!=0) {
            int minuteFinish=readTime/60;
            txtReadReal.setText(String.valueOf(minuteFinish));
        } else {
            txtReadReal.setText("0");
        }
        txtReadGoal= (TextView) view.findViewById(R.id.txt_ReadGoal);
        txtReadGoal.setText("分钟 / 30分钟");
        /*if(readGoal==1) {
            cvRead.setVisibility(View.GONE);
        }*/
        cvHobby=(CardView) view.findViewById(R.id.cv_hobby);
        txtHobbyReal= (TextView) view.findViewById(R.id.txt_HobbyReal);
        spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
        int hobbyTime=spTaskTime.getInt("hobbyTime",0);
        if(hobbyTime!=0) {
            int minuteFinish=hobbyTime/60;
            txtHobbyReal.setText(String.valueOf(minuteFinish));
        } else {
            txtHobbyReal.setText("0");
        }
        txtHobbyGoal= (TextView) view.findViewById(R.id.txt_HobbyGoal);
        txtHobbyGoal.setText("分钟 / 45分钟");
        /*if(hobbyGoal==1) {
            cvHobby.setVisibility(View.GONE);
        }*/
        cvSmile=(CardView) view.findViewById(R.id.cv_smile);
        txtSmileReal= (TextView) view.findViewById(R.id.txt_SmileReal);
        txtSmileReal.setText("0");
        txtSmileGoal= (TextView) view.findViewById(R.id.txt_SmileGoal);
        txtSmileGoal.setText(" / 1次");
        /*if(smileGoal==1) {
            cvSmile.setVisibility(View.GONE);
        }*/
        cvDiary=(CardView) view.findViewById(R.id.cv_diary);
        txtDairyReal= (TextView) view.findViewById(R.id.txt_DairyReal);
        txtDairyReal.setText("0");
        txtDairyGoal= (TextView) view.findViewById(R.id.txt_DairyGoal);
        txtDairyGoal.setText(" / 1篇");
        /*if(diaryGoal==1) {
            cvDiary.setVisibility(View.GONE);
        }*/
        Calendar instance = Calendar.getInstance();
        int weekday = instance.get(Calendar.DAY_OF_WEEK);
        int sport=0;
        if(weekday==1){
            sport=sp1.getInt("SunSport",0);
        } else if(weekday==2) {
            sport=sp1.getInt("MonSport",0);
        } else if(weekday==3) {
            sport=sp1.getInt("TueSport",0);
        } else if(weekday==4) {
            sport=sp1.getInt("WenSport",0);
        } else if(weekday==5) {
            sport=sp1.getInt("ThurSport",0);
        } else if(weekday==6) {
            sport=sp1.getInt("FriSport",0);
        } else if(weekday==7) {
            sport=sp1.getInt("SatSport",0);
        }
        cvPowerSport=(CardView) view.findViewById(R.id.cv_power_sport);
        cvOtherSport=(CardView) view.findViewById(R.id.cv_other_sport);
        if(sport==0){
            cvPowerSport.setVisibility(View.GONE);
            cvOtherSport.setVisibility(View.GONE);
        } else if (sport==1){
            cvOtherSport.setVisibility(View.GONE);
        } else if (sport==2){
            cvPowerSport.setVisibility(View.GONE);
        }

        //是否设置过打卡
        txtSetTip=view.findViewById(R.id.txt_set_tip);
        if (setOrNot==true){
            txtSetTip.setVisibility(View.GONE);
        } else {
            cvGetup.setVisibility(View.GONE);
            cvSleep.setVisibility(View.GONE);
            cvStep.setVisibility(View.GONE);
            cvWater.setVisibility(View.GONE);
            cvRead.setVisibility(View.GONE);
            cvHobby.setVisibility(View.GONE);
            cvSmile.setVisibility(View.GONE);
            cvDiary.setVisibility(View.GONE);
            cvPowerSport.setVisibility(View.GONE);
            cvOtherSport.setVisibility(View.GONE);
        }

        //打卡项目对话框
        cvGetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogGetup dialogGetup = new DialogGetup(getActivity(),new DialogGetup.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
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
                            SharedPreferences spWeekReport;
                            spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
                            int getupReal=spWeekReport.getInt("getupReal",0);
                            if(isTime2Bigger(simpleDateFormat1.format(now),getupTime)){
                                getupReal=getupReal+1;
                            }
                            SharedPreferences.Editor editor1 = spWeekReport.edit();
                            editor1.putInt("getupReal",getupReal);
                            editor1.putString("endDate",simpleDateFormat.format(date));
                            if(spWeekReport.getString("beginDate",null)==null){
                                editor1.putString("beginDate",simpleDateFormat.format(date));
                            }
                            editor1.commit();
                            Log.d(TAG,"完成早起");
                            String getupFinish=spFinishTask.getString("getupFinish", null);
                            isTime2Bigger(getupFinish,getupTime);
                            imgGetup=view.findViewById(R.id.img_getup);
                            if(isTime2Bigger(getupFinish,getupTime)){
                                imgGetup.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            } else {
                                imgGetup.setImageDrawable(getResources().getDrawable(R.drawable.unfinished));
                            }
                            txtGetupReal.setText(getupFinish);
                            cvGetup.setOnClickListener(null);
                            cvGetup.setAlpha(0.6f);
                        }
                    }
                });
                dialogGetup.show();
            }
        });
        cvSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSleep dialogSleep = new DialogSleep(getActivity(),new DialogSleep.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                            Date now=new Date(System.currentTimeMillis());
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putString("sleepFinish", simpleDateFormat1.format(now));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            //存周报
                            String sleepTime= sp1.getString("sleepTime","23:00");
                            sleepTime=sleepTime.substring(0,sleepTime.length()-3);
                            SharedPreferences spWeekReport;
                            spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
                            int sleepReal=spWeekReport.getInt("sleepReal",0);
                            if(isTime2Bigger(simpleDateFormat1.format(now),sleepTime)){
                                sleepReal=sleepReal+1;
                            }
                            SharedPreferences.Editor editor1 = spWeekReport.edit();
                            editor1.putInt("sleepReal",sleepReal);
                            editor1.putString("endDate",simpleDateFormat.format(date));
                            if(spWeekReport.getString("beginDate",null)==null){
                                editor1.putString("beginDate",simpleDateFormat.format(date));
                            }
                            editor1.commit();
                            Log.d(TAG,"完成早睡");
                            String sleepFinish=spFinishTask.getString("sleepFinish", null);
                            isTime2Bigger(sleepFinish,sleepTime);
                            imgSleep=view.findViewById(R.id.img_sleep);
                            if(isTime2Bigger(sleepFinish,sleepTime)){
                                imgSleep.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            } else {
                                imgSleep.setImageDrawable(getResources().getDrawable(R.drawable.unfinished));
                            }
                            txtSleepReal.setText(sleepFinish);
                            cvSleep.setOnClickListener(null);
                            cvSleep.setAlpha(0.6f);
                        }
                    }
                });
                dialogSleep.show();
            }
        });
        cvStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogWalk.class);
                startActivity(intent);
            }
        });
        cvWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWater dialogWater = new DialogWater(getActivity(),new DialogWater.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("waterFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"完成饮水");
                            imgWater=view.findViewById(R.id.img_water);
                            imgWater.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtWaterReal.setText("1.5L");
                            cvWater.setOnClickListener(null);
                            cvWater.setAlpha(0.6f);
                        }
                    }
                });
                dialogWater.show();
            }
        });
        cvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogRead dialogRead = new DialogRead(getActivity(),new DialogRead.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("readFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"完成阅读");
                            imgRead=view.findViewById(R.id.img_read);
                            imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtReadReal.setText("30");
                            cvRead.setOnClickListener(null);
                            cvRead.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int readTime=spTaskTime.getInt("readTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "阅读");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", readTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 1); // requestCode -> 1,read
                        }
                    }
                });
                dialogRead.show();
            }
        });
        cvHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), DialogHobby.class);
                startActivity(intent);*/
                DialogHobby dialogHobby = new DialogHobby(getActivity(),new DialogHobby.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("hobbyFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"完成兴趣爱好");
                            imgHobby=view.findViewById(R.id.img_hobby);
                            imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtHobbyReal.setText("45");
                            cvHobby.setOnClickListener(null);
                            cvHobby.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int hobbyTime=spTaskTime.getInt("hobbyTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "兴趣爱好");//设置参数
                            intent.putExtra("taskTime", 2700);//设置参数
                            intent.putExtra("finishTime", hobbyTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 2); // requestCode -> 2,hobby
                        }
                    }
                });
                dialogHobby.show();
            }
        });
        cvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSmile dialogSmile = new DialogSmile(getActivity(),new DialogSmile.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("smileFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"完成微笑");
                            imgSmile=view.findViewById(R.id.img_smile);
                            imgSmile.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtSmileReal.setText("1");
                            cvSmile.setOnClickListener(null);
                            cvSmile.setAlpha(0.6f);
                        }
                    }
                });
                dialogSmile.show();
            }
        });
        cvDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), DialogDiary.class);
                startActivity(intent);*/
                DialogDiary dialogDiary = new DialogDiary(getActivity(),new DialogDiary.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("diaryFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"完成日记");
                            imgDiary=view.findViewById(R.id.img_diary);
                            imgDiary.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtDairyReal.setText("1");
                            cvDiary.setOnClickListener(null);
                            cvDiary.setAlpha(0.6f);
                        }
                    }
                });
                dialogDiary.show();
            }
        });
        cvPowerSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPowerSport dialogPowerSport = new DialogPowerSport(getActivity(),new DialogPowerSport.DataBackListener()
                {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("powerSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            Log.d(TAG,"完成力量训练");
                            imgPowerSport=view.findViewById(R.id.img_power_sport);
                            imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvPowerSport.setOnClickListener(null);
                            cvPowerSport.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int powerTime=spTaskTime.getInt("powerTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "力量训练");//设置参数
                            intent.putExtra("taskTime", 1200);//设置参数
                            intent.putExtra("finishTime", powerTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 3); // requestCode -> 3,power
                        }
                    }
                });
                dialogPowerSport.show();
            }
        });
        cvOtherSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(getActivity(),new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
                            int sportReal=spWeekReport.getInt("sportReal",0);
                            sportReal=sportReal+1;
                            SharedPreferences.Editor editor1 = spWeekReport.edit();
                            editor1.putInt("sportReal",sportReal);
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
                            Log.d(TAG,"完成其他运动");
                            imgOtherSport=view.findViewById(R.id.img_other_sport);
                            imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvOtherSport.setOnClickListener(null);
                            cvOtherSport.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 4); // requestCode -> 4,other sport
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        //判断某一项是否打卡了
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(System.currentTimeMillis());
        String today=simpleDateFormat1.format(date1);
        Log.d(TAG,"查日期："+today);
        spFinishTask=getActivity().getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        if(spFinishTask.getString("finishDate",null)==null) {
            Log.d(TAG,"userFinishTask为空");
        } else {
            String finishDate=spFinishTask.getString("finishDate",null);
            Log.d(TAG,"获取日期："+finishDate);

            if(finishDate.equals(today)) {
                if(spFinishTask.getInt("diaryFinish", 1)==0){
                    Log.d(TAG,"完成日记");
                    imgDiary=view.findViewById(R.id.img_diary);
                    imgDiary.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtDairyReal.setText("1");
                    cvDiary.setOnClickListener(null);
                    cvDiary.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("hobbyFinish", 1)==0){
                    Log.d(TAG,"完成兴趣爱好");
                    imgHobby=view.findViewById(R.id.img_hobby);
                    imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtHobbyReal.setText("45");
                    cvHobby.setOnClickListener(null);
                    cvHobby.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("smileFinish", 1)==0){
                    Log.d(TAG,"完成微笑");
                    imgSmile=view.findViewById(R.id.img_smile);
                    imgSmile.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtSmileReal.setText("1");
                    cvSmile.setOnClickListener(null);
                    cvSmile.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("readFinish", 1)==0){
                    Log.d(TAG,"完成阅读");
                    imgRead=view.findViewById(R.id.img_read);
                    imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtReadReal.setText("30");
                    cvRead.setOnClickListener(null);
                    cvRead.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("waterFinish", 1)==0){
                    Log.d(TAG,"完成饮水");
                    imgWater=view.findViewById(R.id.img_water);
                    imgWater.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtWaterReal.setText("1.5L");
                    cvWater.setOnClickListener(null);
                    cvWater.setAlpha(0.6f);
                }
                if(spFinishTask.getString("getupFinish", null)!=null){
                    Log.d(TAG,"完成早起");
                    String getupFinish=spFinishTask.getString("getupFinish", null);
                    isTime2Bigger(getupFinish,getupTime);
                    imgGetup=view.findViewById(R.id.img_getup);
                    if(isTime2Bigger(getupFinish,getupTime)){
                        imgGetup.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    } else {
                        imgGetup.setImageDrawable(getResources().getDrawable(R.drawable.unfinished));
                    }
                    txtGetupReal.setText(getupFinish);
                    cvGetup.setOnClickListener(null);
                    cvGetup.setAlpha(0.6f);
                }
                if(spFinishTask.getString("sleepFinish", null)!=null){
                    Log.d(TAG,"完成早睡");
                    String sleepFinish=spFinishTask.getString("sleepFinish", null);
                    isTime2Bigger(sleepFinish,sleepTime);
                    imgSleep=view.findViewById(R.id.img_sleep);
                    if(isTime2Bigger(sleepFinish,sleepTime)){
                        imgSleep.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    } else {
                        imgSleep.setImageDrawable(getResources().getDrawable(R.drawable.unfinished));
                    }
                    txtSleepReal.setText(sleepFinish);
                    cvSleep.setOnClickListener(null);
                    cvSleep.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("powerSportFinish", 1)==0) {
                    Log.d(TAG,"完成力量训练");
                    imgPowerSport=view.findViewById(R.id.img_power_sport);
                    imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    cvPowerSport.setOnClickListener(null);
                    cvPowerSport.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("otherSportFinish", 1)==0) {
                    Log.d(TAG,"完成其他运动");
                    imgOtherSport=view.findViewById(R.id.img_other_sport);
                    imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    cvOtherSport.setOnClickListener(null);
                    cvOtherSport.setAlpha(0.6f);
                }
                //记录步数
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("stepFinish", step);
                editor.commit();
            } else {
                uploadTaskHistory();
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.clear();
                /*editor.putString("getupTime",null);
                editor.putString("sleepTime",null);
                editor.putInt("stepFinish", 0);
                editor.putInt("waterFinish", 1);
                editor.putInt("readFinish", 1);
                editor.putInt("hobbyFinish", 1);
                editor.putInt("smileFinish", 1);
                editor.putInt("diaryFinish", 1);
                editor.putInt("powerSportFinish", 1);
                editor.putInt("otherSportFinish", 1);*/
                editor.commit();
            }
        }
        //步数是否完成
        if(step>=postStepGoal){
            imgStep=view.findViewById(R.id.img_step);
            imgStep.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvStep.setAlpha(0.6f);
        }
    }

    //获取星期
    public String getWeekDay(){
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        if(weekDay==1){
            return "日";
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
                txtStepReal.setText(String.valueOf(todayStep));
                SharedPreferences.Editor editor = spStepTask.edit();
                editor.putInt("stepFinish", todayStep);
                editor.commit();
                double distance1;
                distance1=todayStep*0.6/1000;
                java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
                String s = df.format(distance1);
                distance=Double.parseDouble(s);
                Log.d(TAG,"distance:"+distance);
                txtDistance.setText("相当于走了"+distance+"公里");
                double calorie1;
                calorie1=todayStep/20;
                calorie=(int) calorie1;
                Log.d(TAG,"calorie:"+calorie);
                txtCalorie.setText("消耗了"+calorie+"卡路里");
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
        if(dt1 == null||dt2 == null) {
            isBigger = false;
        } else {
            if (dt1.getTime() >= dt2.getTime()) {
                isBigger = false;
            } else if (dt1.getTime() < dt2.getTime()) {
                isBigger = true;
            }
        }

        return isBigger;
    }
    /*上传步数*/
    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(stringHashMap);
        }
    };

    private void requestPost(HashMap<String, String> paramsMap){
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/UploadStepServlet";
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos >0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            Log.e(TAG,"params--post-->>"+params);
            // 请求的参数转换为byte数组
//            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            //配置请求Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post请求不能设置这个
            // 开始连接
            urlConn.connect();

            // 发送请求参数
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post方式请求成功，result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                        Log.d(TAG,"返回结果："+code);
                    }
                    switch (code){
                        case 0 : // 上传失败
                            Looper.prepare();
                            Log.d(TAG,"传步数成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"传步数失败");
                            //Toast.makeText(this.getContext(),"信息修改成功！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        default:
                            //Toast.makeText(RegisterActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post方式请求失败");
                //Toast.makeText(this.getContext(),"手机号或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    private void uploadTaskHistory(){
        int taskNum=0;//总任务数
        int taskRealNum=0;//完成任务数
        SharedPreferences spWeekReport;
        spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        Log.d(TAG,"传打卡记录");
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap1.put("userID", String.valueOf(userID));
        String getupFinish=spFinishTask.getString("getupFinish", null);
        stringHashMap1.put("getupTime", getupFinish);
        taskNum=taskNum+1;
        if(getupFinish!=null&&getupTime!=null) {
            if(isDate2Bigger(getupFinish,getupTime)){
                taskRealNum=taskRealNum+1;
            }
        }

        String sleepFinish=spFinishTask.getString("sleepFinish", null);
        stringHashMap1.put("sleepTime", sleepFinish);
        taskNum=taskNum+1;
        if(sleepFinish!=null&&sleepTime!=null){
            if(isDate2Bigger(sleepFinish,sleepTime)){
                taskRealNum=taskRealNum+1;
            }
        }

        int stepReal=spFinishTask.getInt("stepFinish",0);
        stringHashMap1.put("stepRealNum", String.valueOf(stepReal));
        taskNum=taskNum+1;
        if(stepReal>=postStepGoal) {
            int stepDays=spWeekReport.getInt("stepReal",0);
            stepDays=stepDays+1;
            SharedPreferences.Editor editor1 = spWeekReport.edit();
            editor1.putInt("stepReal",stepDays);
            editor1.commit();
            taskRealNum=taskRealNum+1;
        }
        if(drinkGoal==1) {
            stringHashMap1.put("drinkReal", String.valueOf(2));
        } else {
            int drinkReal=spFinishTask.getInt("waterFinish", 1);
            stringHashMap1.put("drinkReal", String.valueOf(drinkReal));
            taskNum=taskNum+1;
            if(drinkReal==0) {
                taskRealNum=taskRealNum+1;
            }
        }
        if(readGoal==1) {
            stringHashMap1.put("readReal", String.valueOf(2));
        } else {
            int readReal=spFinishTask.getInt("readFinish", 1);
            stringHashMap1.put("readReal", String.valueOf(readReal));
            taskNum=taskNum+1;
            if(readReal==0) {
                taskRealNum=taskRealNum+1;
            }
        }
        if(hobbyGoal==1) {
            stringHashMap1.put("hobbyReal", String.valueOf(2));
        } else {
            int hobbyReal=spFinishTask.getInt("hobbyFinish", 1);
            stringHashMap1.put("hobbyReal", String.valueOf(hobbyReal));
            taskNum=taskNum+1;
            if(hobbyReal==0) {
                taskRealNum=taskRealNum+1;
            }
        }
        if(smileGoal==1) {
            stringHashMap1.put("smileReal", String.valueOf(2));
        } else {
            int smileReal=spFinishTask.getInt("smileFinish", 1);
            stringHashMap1.put("smileReal", String.valueOf(smileReal));
            taskNum=taskNum+1;
            if(smileReal==0) {
                taskRealNum=taskRealNum+1;
            }
        }
        if(diaryGoal==1) {
            stringHashMap1.put("diaryReal", String.valueOf(2));
        } else {
            int diaryReal=spFinishTask.getInt("diaryFinish", 1);
            stringHashMap1.put("diaryReal", String.valueOf(diaryReal));
            taskNum=taskNum+1;
            if(diaryReal==0) {
                taskRealNum=taskRealNum+1;
            }
        }
        stringHashMap1.put("taskDate", spFinishTask.getString("finishDate",null));
        new Thread(postRunHistory).start();//传每日打卡记录
        if(taskNum==taskRealNum) {
            int dailyReal=spWeekReport.getInt("dailyReal",0);
            dailyReal=dailyReal+1;
            SharedPreferences.Editor editor1 = spWeekReport.edit();
            editor1.putInt("dailyReal",dailyReal);
            editor1.commit();
        }
    }
    Runnable postRunHistory = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPostHistory(stringHashMap1);
        }
    };
    private void requestPostHistory(HashMap<String, String> paramsMap){
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/UploadHistoryCheckServlet";
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos >0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            Log.e(TAG,"params--post-->>"+params);
            // 请求的参数转换为byte数组
//            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            //配置请求Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post请求不能设置这个
            // 开始连接
            urlConn.connect();

            // 发送请求参数
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post方式请求成功，result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                        Log.d(TAG,"返回结果："+code);
                    }
                    switch (code){
                        case 0 : // 上传失败
                            Looper.prepare();
                            Log.d(TAG,"传打卡历史成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"获取打卡历史失败");
                            //Toast.makeText(this.getContext(),"信息修改成功！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        default:
                            //Toast.makeText(RegisterActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post方式请求失败");
                //Toast.makeText(this.getContext(),"手机号或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    public static boolean isTime2Bigger(String str1, String str2) {
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
// operation succeeded. 默认值是-1
        if (resultCode == 2) {
            if (requestCode == 1) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //设置结果显示框的显示数值
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                int trueFinish=spTaskTime.getInt("readTime",0);
                trueFinish=trueFinish+finishTime;
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("readTime",trueFinish);
                editorTime.commit();
                int minuteFinish=trueFinish/60;
                txtReadReal.setText(String.valueOf(minuteFinish));
            }
            if (requestCode == 2) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //设置结果显示框的显示数值
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                int trueFinish=spTaskTime.getInt("hobbyTime",0);
                trueFinish=trueFinish+finishTime;
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("hobbyTime",trueFinish);
                editorTime.commit();
                int minuteFinish=trueFinish/60;
                txtHobbyReal.setText(String.valueOf(minuteFinish));
            }
            if (requestCode == 3) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //设置结果显示框的显示数值
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                int trueFinish=spTaskTime.getInt("powerTime",0);
                trueFinish=trueFinish+finishTime;
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("powerTime",trueFinish);
                editorTime.commit();
            }
            if (requestCode == 4) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //设置结果显示框的显示数值
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                int trueFinish=spTaskTime.getInt("sportTime",0);
                trueFinish=trueFinish+finishTime;
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",trueFinish);
                editorTime.commit();
            }
        }
        if (resultCode==1) {
            if (requestCode == 1) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("readFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Log.d(TAG,"完成阅读");
                //imgRead=view.findViewById(R.id.img_read);
                imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                txtReadReal.setText("30");
                cvRead.setOnClickListener(null);
                cvRead.setAlpha(0.6f);
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("readTime",0);
                editorTime.commit();
            }
            if (requestCode == 2) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("hobbyFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Log.d(TAG,"完成兴趣爱好");
                //imgHobby=view.findViewById(R.id.img_hobby);
                imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                txtHobbyReal.setText("45");
                cvHobby.setOnClickListener(null);
                cvHobby.setAlpha(0.6f);
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("hobbyTime",0);
                editorTime.commit();
            }
            if(requestCode==3) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("powerSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                Log.d(TAG,"完成力量训练");
                //imgPowerSport=view.findViewById(R.id.img_power_sport);
                imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvPowerSport.setOnClickListener(null);
                cvPowerSport.setAlpha(0.6f);
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("powerTime",0);
                editorTime.commit();
            }
            if(requestCode==4) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
                int sportReal=spWeekReport.getInt("sportReal",0);
                sportReal=sportReal+1;
                SharedPreferences.Editor editor1 = spWeekReport.edit();
                editor1.putInt("sportReal",sportReal);
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
                Log.d(TAG,"完成其他运动");
                //imgOtherSport=view.findViewById(R.id.img_other_sport);
                imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvOtherSport.setOnClickListener(null);
                cvOtherSport.setAlpha(0.6f);
                SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
        }
    }

}