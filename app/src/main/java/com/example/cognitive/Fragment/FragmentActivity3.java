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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.example.cognitive.Activity.SetTask;
import com.example.cognitive.Activity.TaskHistory;
import com.example.cognitive.Activity.TimeCounter;
import com.example.cognitive.Activity.WalkingActivity;
import com.example.cognitive.Activity.WeeklyExercise;
import com.example.cognitive.Activity.WeeklyReport;
import com.example.cognitive.R;
import com.example.cognitive.SQLiteDB.DatabaseHelper;
import com.example.cognitive.Utils.DestroyActivityUtil;

import org.json.JSONArray;
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

import mehdi.sakout.fancybuttons.FancyButton;


public class FragmentActivity3 extends Fragment {
    private SharedPreferences sp;
    private SharedPreferences sp1;//????????????
    private SharedPreferences spFinishTask;//????????????
    private SharedPreferences spStepTask;
    private HashMap<String, String> stringHashMap;
    private HashMap<String, String> stringHashMap1;
    private HashMap<String, String> stringHashMap2;//??????????????????
    public Handler mhandler;
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
    private FancyButton start;
    private Sensor sensor;
    private SensorEventListener stepCounterListener;
    private TextView step_num;
    private static String TAG = "????????????";
    private SensorManager mSensorManager;
    private static final String[] permissions = {Manifest.permission.ACTIVITY_RECOGNITION};
    //?????????????????????
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
        stringHashMap2 = new HashMap<>();
        spStepTask=getActivity().getSharedPreferences("userStepTask", Context.MODE_PRIVATE);
        mhandler = new FragmentActivity3.mHandler();
        //????????????
        get_date=view.findViewById(R.id.get_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM???dd???");
        Date date = new Date(System.currentTimeMillis());
        get_date.setText(simpleDateFormat.format(date));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        todayDate=formatter.format(date);
        Log.d(TAG,todayDate);
        //????????????
        get_weekday=view.findViewById(R.id.get_weekday);
        String weekDay=getWeekDay();
        get_weekday.setText("??????"+weekDay);
        //???????????????
        imgRead=view.findViewById(R.id.img_read);
        imgHobby=view.findViewById(R.id.img_hobby);
        imgPowerSport=view.findViewById(R.id.img_power_sport);
        imgOtherSport=view.findViewById(R.id.img_other_sport);
        //????????????
        step_num=view.findViewById(R.id.step_num);

        llDailyTask=view.findViewById(R.id.ll_daily_task);
        llDailyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //????????????MainActivity.this???????????????????????????????????????????????????????????????
                intent.setClass(getActivity(), FinishHealthyTask.class);
                startActivity(intent);
            }
        });
        llSport=view.findViewById(R.id.ll_sport);
        llSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //????????????MainActivity.this???????????????????????????????????????????????????????????????
                intent.setClass(getActivity(), WeeklyExercise.class);
                startActivity(intent);
            }
        });
        llWeeklyReport=view.findViewById(R.id.ll_weekly_report);
        llWeeklyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //????????????MainActivity.this???????????????????????????????????????????????????????????????
                intent.setClass(getActivity(), WeeklyReport.class);
                startActivity(intent);
            }
        });

        //????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Log.d(TAG, "[??????]" + "ACTIVITY_RECOGNITION ?????????");
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                // ??????????????????
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACTIVITY_RECOGNITION)) {
                    //  ??????????????????????????????????????????????????????????????????????????????
                    Log.d(TAG, "[??????]" + "ACTIVITY_RECOGNITION ????????????????????????????????????????????????");
                } else {
                    //  ?????????????????????????????????
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1);
                    Log.d(TAG, "[??????]" + "ACTIVITY_RECOGNITION ??????????????????????????????????????????");
                }
//                return;
            }else{

                Log.d(TAG, "[??????]" + "ACTIVITY_RECOGNITION ready");
            }
        }else{

        }

        //???????????????
        startSensor();
        //???????????????
        dbHelper=new DatabaseHelper(this.getContext(),"database1",null,2);
        dbHelper.getWritableDatabase();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor= db.query("Step",null,null,null,null,null,null);
        if(cursor.getCount()==0){ //?????????????????????????????????
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
            Log.d(TAG,"??????");
            //cursor.close();
        }
        else if(cursor.getCount()==1) {
            ContentValues values=new ContentValues();
            values.put("id",2);
            values.put("step_num",0);
            values.put("step_time",todayDate);
            db.insert("Step",null,values);
            values.clear();
            Log.d(TAG,"??????");
        }
        if(cursor.moveToFirst()){ //???????????????????????????
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
            Log.d(TAG,"??????????????????????????????");
        }

        // ????????????
        start = view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WalkingActivity.class);
                startActivity(intent);
            }
        });
        //???????????????
        cursor = db.query("Step",null,"id=?", new String[] {"2"},null,null,null);
        cursor.moveToFirst();
        id=cursor.getInt(cursor.getColumnIndex("id"));
        stepNum=cursor.getInt(cursor.getColumnIndex("step_num"));
        stepTime=cursor.getString(cursor.getColumnIndex("step_time"));
        Log.d(TAG,"id:"+id);
        Log.d(TAG,"step_num:"+stepNum);
        Log.d(TAG,"step_time:"+stepTime);
        result=isDate2Bigger(stepTime,todayDate);
        Log.d(TAG,"??????2?????????result:"+result);
        if(result==true){ //??????????????????2???????????????????????????1
            ContentValues values=new ContentValues();
            values.put("step_num",stepNum);
            values.put("step_time",stepTime);
            db.update("Step",values,"id=?", new String[] {"1"});
            values.clear();
            Log.d(TAG,"??????1");
        }
        //???????????????
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
        Log.d(TAG,"?????????yesterdayStep:"+yesterdayStep);
        cursor.close();


        //??????????????????
        history_step=view.findViewById(R.id.step_num);
        history_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //????????????MainActivity.this???????????????????????????????????????????????????????????????
                intent.setClass(getActivity(), HistoryStep.class);
                startActivity(intent);
            }
        });
        //??????
        txtDistance=view.findViewById(R.id.txt_distance);
        //?????????
        txtCalorie=view.findViewById(R.id.txt_calorie);

        //??????????????????
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

        //????????????
        //????????????????????????
        sp1= getActivity().getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        //?????????????????????
        txtSetTip=view.findViewById(R.id.txt_set_tip);
        boolean setOrNot= sp1.getBoolean("setOrNot", false);
        if (setOrNot==true){
            txtSetTip.setVisibility(View.GONE);
        } else {
            getHealthyTask();
            /*cvGetup.setVisibility(View.GONE);
            cvSleep.setVisibility(View.GONE);
            cvStep.setVisibility(View.GONE);
            cvWater.setVisibility(View.GONE);
            cvRead.setVisibility(View.GONE);
            cvHobby.setVisibility(View.GONE);
            cvSmile.setVisibility(View.GONE);
            cvDiary.setVisibility(View.GONE);
            cvPowerSport.setVisibility(View.GONE);
            cvOtherSport.setVisibility(View.GONE);*/
        }
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


        //??????????????????
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
        if(drinkGoal==1) {
            cvWater.setVisibility(View.GONE);
        }
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
        txtReadGoal.setText("?????? / 30??????");
        if(readGoal==1) {
            cvRead.setVisibility(View.GONE);
        }
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
        txtHobbyGoal.setText("?????? / 45??????");
        if(hobbyGoal==1) {
            cvHobby.setVisibility(View.GONE);
        }
        cvSmile=(CardView) view.findViewById(R.id.cv_smile);
        txtSmileReal= (TextView) view.findViewById(R.id.txt_SmileReal);
        txtSmileReal.setText("0");
        txtSmileGoal= (TextView) view.findViewById(R.id.txt_SmileGoal);
        txtSmileGoal.setText(" / 1???");
        if(smileGoal==1) {
            cvSmile.setVisibility(View.GONE);
        }
        cvDiary=(CardView) view.findViewById(R.id.cv_diary);
        txtDairyReal= (TextView) view.findViewById(R.id.txt_DairyReal);
        txtDairyReal.setText("0");
        txtDairyGoal= (TextView) view.findViewById(R.id.txt_DairyGoal);
        txtDairyGoal.setText(" / 1???");
        if(diaryGoal==1) {
            cvDiary.setVisibility(View.GONE);
        }
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



        //?????????????????????
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            //?????????
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
                            Log.d(TAG,"????????????");
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            //?????????
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
                            Log.d(TAG,"????????????");
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"????????????");
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"????????????");
                            imgRead=view.findViewById(R.id.img_read);
                            imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtReadReal.setText("30");
                            cvRead.setOnClickListener(null);
                            cvRead.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int readTime=spTaskTime.getInt("readTime",0);
                            Intent intent = new Intent();
                            //????????????MainActivity.this???????????????????????????????????????????????????????????????
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "??????");//????????????
                            intent.putExtra("taskTime", 1800);//????????????
                            intent.putExtra("finishTime", readTime);//????????????
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"??????????????????");
                            imgHobby=view.findViewById(R.id.img_hobby);
                            imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtHobbyReal.setText("45");
                            cvHobby.setOnClickListener(null);
                            cvHobby.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int hobbyTime=spTaskTime.getInt("hobbyTime",0);
                            Intent intent = new Intent();
                            //????????????MainActivity.this???????????????????????????????????????????????????????????????
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "????????????");//????????????
                            intent.putExtra("taskTime", 2700);//????????????
                            intent.putExtra("finishTime", hobbyTime);//????????????
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"????????????");
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
                            //??????????????????
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            Log.d(TAG,"????????????");
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
                            //??????????????????
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
                            Log.d(TAG,"??????????????????");
                            imgPowerSport=view.findViewById(R.id.img_power_sport);
                            imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvPowerSport.setOnClickListener(null);
                            cvPowerSport.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int powerTime=spTaskTime.getInt("powerTime",0);
                            Intent intent = new Intent();
                            //????????????MainActivity.this???????????????????????????????????????????????????????????????
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "????????????");//????????????
                            intent.putExtra("taskTime", 1200);//????????????
                            intent.putExtra("finishTime", powerTime);//????????????
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
                            //??????????????????
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
                            Log.d(TAG,"??????????????????");
                            imgOtherSport=view.findViewById(R.id.img_other_sport);
                            imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvOtherSport.setOnClickListener(null);
                            cvOtherSport.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //????????????MainActivity.this???????????????????????????????????????????????????????????????
                            intent.setClass(getActivity(), TimeCounter.class);
                            intent.putExtra("taskName", "????????????");//????????????
                            intent.putExtra("taskTime", 1800);//????????????
                            intent.putExtra("finishTime", sportTime);//????????????
                            //startActivity(intent);
                            startActivityForResult(intent, 4); // requestCode -> 4,other sport
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        //??????????????????????????????
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(System.currentTimeMillis());
        String today=simpleDateFormat1.format(date1);
        Log.d(TAG,"????????????"+today);
        spFinishTask=getActivity().getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        if(spFinishTask.getString("finishDate",null)==null) {
            Log.d(TAG,"userFinishTask??????");
        } else {
            String finishDate=spFinishTask.getString("finishDate",null);
            Log.d(TAG,"???????????????"+finishDate);

            if(finishDate.equals(today)) {
                if(spFinishTask.getInt("diaryFinish", 1)==0){
                    Log.d(TAG,"????????????");
                    imgDiary=view.findViewById(R.id.img_diary);
                    imgDiary.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtDairyReal.setText("1");
                    cvDiary.setOnClickListener(null);
                    cvDiary.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("hobbyFinish", 1)==0){
                    Log.d(TAG,"??????????????????");
                    imgHobby=view.findViewById(R.id.img_hobby);
                    imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtHobbyReal.setText("45");
                    cvHobby.setOnClickListener(null);
                    cvHobby.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("smileFinish", 1)==0){
                    Log.d(TAG,"????????????");
                    imgSmile=view.findViewById(R.id.img_smile);
                    imgSmile.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtSmileReal.setText("1");
                    cvSmile.setOnClickListener(null);
                    cvSmile.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("readFinish", 1)==0){
                    Log.d(TAG,"????????????");
                    imgRead=view.findViewById(R.id.img_read);
                    imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtReadReal.setText("30");
                    cvRead.setOnClickListener(null);
                    cvRead.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("waterFinish", 1)==0){
                    Log.d(TAG,"????????????");
                    imgWater=view.findViewById(R.id.img_water);
                    imgWater.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtWaterReal.setText("1.5L");
                    cvWater.setOnClickListener(null);
                    cvWater.setAlpha(0.6f);
                }
                if(spFinishTask.getString("getupFinish", null)!=null){
                    Log.d(TAG,"????????????");
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
                    Log.d(TAG,"????????????");
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
                    Log.d(TAG,"??????????????????");
                    imgPowerSport=view.findViewById(R.id.img_power_sport);
                    imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    cvPowerSport.setOnClickListener(null);
                    cvPowerSport.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("otherSportFinish", 1)==0) {
                    Log.d(TAG,"??????????????????");
                    imgOtherSport=view.findViewById(R.id.img_other_sport);
                    imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    cvOtherSport.setOnClickListener(null);
                    cvOtherSport.setAlpha(0.6f);
                }
                //????????????
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("stepFinish", step);
                editor.commit();
            } else {
                reSetTaskTime();
                uploadTaskHistory();
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.clear();
                editor.commit();
            }
        }
        //??????????????????
        if(step>=postStepGoal){
            imgStep=view.findViewById(R.id.img_step);
            imgStep.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvStep.setAlpha(0.6f);
        }
    }

    //????????????
    public String getWeekDay(){
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        if(weekDay==1){
            return "???";
        } else if(weekDay==2) {
            return "???";
        } else if(weekDay==3) {
            return "???";
        } else if(weekDay==4) {
            return "???";
        } else if(weekDay==5) {
            return "???";
        } else if(weekDay==6) {
            return "???";
        } else if(weekDay==7) {
            return "???";
        } else {
            return null;
        }
    }

    //???????????????
    private void startSensor() {
        mSensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);

        Sensor mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (mSensorManager == null || mStepCounterSensor == null || mStepDetectorSensor == null) {
            throw new UnsupportedOperationException("???????????????");
        }

        mSensorManager.registerListener(mSensorEventListener, mStepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListener, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //?????????????????????
    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        private float step, stepDetector;

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

             //???????????????????????????????????????????????????

            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                step = sensorEvent.values[0];
                Log.d(TAG, "STEP_COUNTER:" + step);
                intStep=(int)step;
                Log.d(TAG, "????????????Step:" + intStep);

                //???????????????2??????
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("step_num",intStep);
                values.put("step_time",todayDate);
                db.update("Step",values,"id=?", new String[] {"2"});
                values.clear();
                Log.d(TAG,"??????2");
                if(intStep<yesterdayStep) {
                    todayStep=intStep;
                } else {
                    todayStep=intStep-yesterdayStep;
                }
                Log.d(TAG, "?????????Step:" + todayStep);
                step_num.setText("????????????"+todayStep+"???");
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
                txtDistance.setText("???????????????"+distance+"??????");
                double calorie1;
                calorie1=todayStep/20;
                calorie=(int) calorie1;
                Log.d(TAG,"calorie:"+calorie);
                txtCalorie.setText("?????????"+calorie+"?????????");
            }


             //????????????????????????????????????????????????????????????

            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                stepDetector = sensorEvent.values[0];
                Log.d(TAG, "STEP_DETECTOR:" + stepDetector);
                if (stepDetector == 1.0) {
                    Log.d(TAG, "?????????????????????");
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
                    // ????????????
                    Log.d(TAG, "[??????]" + "ACTIVITY_RECOGNITION ????????????");
                } else {
                    // ????????????
                    Log.d(TAG, "[??????]" + "ACTIVITY_RECOGNITION ????????????");
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
    /*????????????*/
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
            //????????????
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
            // ????????????????????????byte??????
//            byte[] postData = params.getBytes();
            // ????????????URL??????
            URL url = new URL(baseUrl);
            // ????????????HttpURLConnection??????
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // ????????????????????????
            urlConn.setConnectTimeout(5 * 1000);
            //?????????????????????????????????
            urlConn.setReadTimeout(5 * 1000);
            // Post?????????????????????????????? ??????false
            urlConn.setDoOutput(true);
            //???????????????????????? ?????????true
            urlConn.setDoInput(true);
            // Post????????????????????????
            urlConn.setUseCaches(false);
            // ?????????Post??????
            urlConn.setRequestMethod("POST");
            //?????????????????????????????????????????????
            urlConn.setInstanceFollowRedirects(true);
            //????????????Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post????????????????????????
            // ????????????
            urlConn.connect();

            // ??????????????????
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // ????????????????????????
            if (urlConn.getResponseCode() == 200) {
                // ?????????????????????
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post?????????????????????result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                        Log.d(TAG,"???????????????"+code);
                    }
                    switch (code){
                        case 0 : // ????????????
                            Looper.prepare();
                            Log.d(TAG,"???????????????");
                            //Toast.makeText(this.getContext(),"?????????????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // ????????????
                            Looper.prepare();
                            Log.d(TAG,"???????????????");
                            //Toast.makeText(this.getContext(),"?????????????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        default:
                            //Toast.makeText(RegisterActivity.this,"??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post??????????????????");
                //Toast.makeText(this.getContext(),"??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // ????????????
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    /**
     * ??????????????????????????????
     *
     * @param is ???????????????????????????
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
        int taskNum=0;//????????????
        int taskRealNum=0;//???????????????
        SharedPreferences spWeekReport;
        spWeekReport=getActivity().getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        Log.d(TAG,"???????????????");
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
        new Thread(postRunHistory).start();//?????????????????????
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
            //????????????
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
            // ????????????????????????byte??????
//            byte[] postData = params.getBytes();
            // ????????????URL??????
            URL url = new URL(baseUrl);
            // ????????????HttpURLConnection??????
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // ????????????????????????
            urlConn.setConnectTimeout(5 * 1000);
            //?????????????????????????????????
            urlConn.setReadTimeout(5 * 1000);
            // Post?????????????????????????????? ??????false
            urlConn.setDoOutput(true);
            //???????????????????????? ?????????true
            urlConn.setDoInput(true);
            // Post????????????????????????
            urlConn.setUseCaches(false);
            // ?????????Post??????
            urlConn.setRequestMethod("POST");
            //?????????????????????????????????????????????
            urlConn.setInstanceFollowRedirects(true);
            //????????????Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post????????????????????????
            // ????????????
            urlConn.connect();

            // ??????????????????
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // ????????????????????????
            if (urlConn.getResponseCode() == 200) {
                // ?????????????????????
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post?????????????????????result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                        Log.d(TAG,"???????????????"+code);
                    }
                    switch (code){
                        case 0 : // ????????????
                            Looper.prepare();
                            Log.d(TAG,"?????????????????????");
                            //Toast.makeText(this.getContext(),"?????????????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // ????????????
                            Looper.prepare();
                            Log.d(TAG,"????????????????????????");
                            //Toast.makeText(this.getContext(),"?????????????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        default:
                            //Toast.makeText(RegisterActivity.this,"??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post??????????????????");
                //Toast.makeText(this.getContext(),"??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // ????????????
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
// RESULT_OK?????????????????????activity?????????????????????????????????Standard activity result:
// operation succeeded. ????????????-1
        if (resultCode == 2) {
            if (requestCode == 1) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //????????????????????????????????????
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
                //????????????????????????????????????
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
                //????????????????????????????????????
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
                //????????????????????????????????????
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
                //??????????????????
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Log.d(TAG,"????????????");
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
                //??????????????????
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Log.d(TAG,"??????????????????");
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
                //??????????????????
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
                Log.d(TAG,"??????????????????");
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
                //??????????????????
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
                Log.d(TAG,"??????????????????");
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

    //????????????????????????
    public void reSetTaskTime() {
        SharedPreferences spTaskTime=getActivity().getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorTime=spTaskTime.edit();
        editorTime.putInt("readTime",0);
        editorTime.putInt("hobbyTime",0);
        editorTime.putInt("powerTime",0);
        editorTime.putInt("sportTime",0);
        editorTime.commit();
    }

    //????????????????????????
    Runnable postRunTask = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPostTask(stringHashMap2);
        }
    };
    private void requestPostTask(HashMap<String, String> paramsMap){
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetCheckServlet";
            //????????????
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
            // ????????????????????????byte??????
//            byte[] postData = params.getBytes();
            // ????????????URL??????
            URL url = new URL(baseUrl);
            // ????????????HttpURLConnection??????
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // ????????????????????????
            urlConn.setConnectTimeout(5 * 1000);
            //?????????????????????????????????
            urlConn.setReadTimeout(5 * 1000);
            // Post?????????????????????????????? ??????false
            urlConn.setDoOutput(true);
            //???????????????????????? ?????????true
            urlConn.setDoInput(true);
            // Post????????????????????????
            urlConn.setUseCaches(false);
            // ?????????Post??????
            urlConn.setRequestMethod("POST");
            //?????????????????????????????????????????????
            urlConn.setInstanceFollowRedirects(true);
            //????????????Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post????????????????????????
            // ????????????
            urlConn.connect();

            // ??????????????????
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // ????????????????????????
            if (urlConn.getResponseCode() == 200) {
                // ?????????????????????
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post?????????????????????result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                        JSONArray dataList=jsonObject.getJSONArray("data");
                        if(dataList.length()==0) {
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("dataList",0); //???Bundle???????????????
                            msg.setData(bundle);//mes??????Bundle????????????
                            mhandler.sendMessage(msg);
                        } else {
                            int stepGoal;
                            int diaryGoal;
                            int drinkGoal;
                            int smileGoal;
                            int readGoal;
                            int hobbyGoal;
                            int everyWeekSport;
                            int weeklyPowerSport;
                            String getupTimeGoal;
                            String sleepTimeGoal;
                            int temp=dataList.length()-1;
                            JSONObject object=dataList.getJSONObject(temp);
                            stepGoal=object.getInt("stepGoal");
                            diaryGoal=object.getInt("diaryGoal");
                            drinkGoal=object.getInt("drinkGoal");
                            smileGoal=object.getInt("smileGoal");
                            readGoal=object.getInt("readGoal");
                            hobbyGoal=object.getInt("hobbyGoal");
                            everyWeekSport=object.getInt("everyWeekSport");
                            weeklyPowerSport=object.getInt("weeklyPowerSport");
                            getupTimeGoal=object.getString("getupTime");
                            sleepTimeGoal=object.getString("sleepTime");
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("dataList",1); //???Bundle???????????????
                            bundle.putInt("stepGoal",stepGoal); //???Bundle???????????????
                            bundle.putInt("diaryGoal",diaryGoal); //???Bundle???????????????
                            bundle.putInt("drinkGoal",drinkGoal); //???Bundle???????????????
                            bundle.putInt("smileGoal",smileGoal); //???Bundle???????????????
                            bundle.putInt("readGoal",readGoal); //???Bundle???????????????
                            bundle.putInt("hobbyGoal",hobbyGoal); //???Bundle???????????????
                            bundle.putInt("everyWeekSport",everyWeekSport); //???Bundle???????????????
                            bundle.putInt("weeklyPowerSport",weeklyPowerSport); //???Bundle???????????????
                            bundle.putString("getupTimeGoal",getupTimeGoal); //???Bundle???????????????
                            bundle.putString("sleepTimeGoal",sleepTimeGoal); //???Bundle???????????????
                            msg.setData(bundle);//mes??????Bundle????????????
                            mhandler.sendMessage(msg);
                        }
                            Log.d(TAG,"???????????????"+code);
                    }
                    switch (code){
                        case 0 : // ????????????
                            Looper.prepare();
                            Log.d(TAG,"????????????????????????");
                            //Toast.makeText(this.getContext(),"?????????????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // ????????????
                            Looper.prepare();
                            Log.d(TAG,"????????????????????????");
                            //Toast.makeText(this.getContext(),"?????????????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        default:
                            //Toast.makeText(RegisterActivity.this,"??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post??????????????????");
                //Toast.makeText(this.getContext(),"??????????????????????????????????????????", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // ????????????
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    public void getHealthyTask() {
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap2.put("userID", String.valueOf(userID));
        new Thread(postRunTask).start();//??????????????????
    }
    class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int dataList = msg.getData().getInt("dataList");
            if(dataList==1) {
                txtSetTip.setVisibility(View.GONE);
                int stepGoal = msg.getData().getInt("stepGoal");
                int diaryGoal = msg.getData().getInt("diaryGoal");
                int drinkGoal = msg.getData().getInt("drinkGoal");
                int smileGoal = msg.getData().getInt("smileGoal");
                int readGoal = msg.getData().getInt("readGoal");
                int hobbyGoal = msg.getData().getInt("hobbyGoal");
                int everyWeekSport = msg.getData().getInt("everyWeekSport");
                int weeklyPowerSport = msg.getData().getInt("weeklyPowerSport");
                String getupTimeGoal = msg.getData().getString("getupTimeGoal");
                String sleepTimeGoal = msg.getData().getString("sleepTimeGoal");
                sp1= getActivity().getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                editor.putString("getupTime", getupTimeGoal);
                editor.putString("sleepTime", sleepTimeGoal);
                editor.putInt("stepGoal", stepGoal);
                editor.putInt("everyWeekSport", everyWeekSport);
                editor.putInt("weeklyPowerSport", weeklyPowerSport);
                editor.putInt("drinkGoal", drinkGoal);
                editor.putInt("readGoal", readGoal);
                editor.putInt("hobbyGoal", hobbyGoal);
                editor.putInt("smileGoal", smileGoal);
                editor.putInt("diaryGoal", diaryGoal);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String setDate=formatter.format(date);
                editor.putString("setDate", setDate);
                editor.putBoolean("setOrNot", true);
                //0????????????1???????????????2????????????
                if(weeklyPowerSport==1) {
                    editor.putInt("MonSport", 1);
                    if(everyWeekSport==3) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 2);
                        editor.putInt("ThurSport", 0);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SatSport", 0);
                        editor.putInt("SunSport", 0);
                    } else if(everyWeekSport==4) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 2);
                        editor.putInt("ThurSport", 0);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SatSport", 0);
                        editor.putInt("SunSport", 2);
                    } else if(everyWeekSport==5) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 2);
                        editor.putInt("ThurSport", 2);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SatSport", 0);
                        editor.putInt("SunSport", 2);
                    } else if(everyWeekSport==6) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 2);
                        editor.putInt("ThurSport", 2);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SatSport", 2);
                        editor.putInt("SunSport", 2);
                    }
                }
                else if(weeklyPowerSport==2) {
                    editor.putInt("MonSport", 1);
                    editor.putInt("ThurSport", 1);
                    if(everyWeekSport==3) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 0);
                        editor.putInt("FriSport", 0);
                        editor.putInt("SatSport", 2);
                        editor.putInt("SunSport", 0);
                    } else if(everyWeekSport==4) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 2);
                        editor.putInt("FriSport", 0);
                        editor.putInt("SatSport", 2);
                        editor.putInt("SunSport", 0);
                    } else if(everyWeekSport==5) {
                        editor.putInt("TueSport", 2);
                        editor.putInt("WenSport", 0);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SatSport", 0);
                        editor.putInt("SunSport", 2);
                    } else if(everyWeekSport==6) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 2);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SatSport", 2);
                        editor.putInt("SunSport", 2);
                    }
                }
                else if(weeklyPowerSport==3) {
                    editor.putInt("MonSport", 1);
                    editor.putInt("ThurSport", 1);
                    editor.putInt("SatSport", 1);
                    if(everyWeekSport==3) {
                        editor.putInt("TueSport", 0);
                        editor.putInt("WenSport", 0);
                        editor.putInt("FriSport", 0);
                        editor.putInt("SunSport", 0);
                    } else if(everyWeekSport==4) {
                        editor.putInt("TueSport", 2);
                        editor.putInt("WenSport", 0);
                        editor.putInt("FriSport", 0);
                        editor.putInt("SunSport", 0);
                    } else if(everyWeekSport==5) {
                        editor.putInt("TueSport", 2);
                        editor.putInt("WenSport", 0);
                        editor.putInt("FriSport", 2);
                        editor.putInt("SunSport", 0);
                    } else if(everyWeekSport==6) {
                        editor.putInt("TueSport", 2);
                        editor.putInt("WenSport", 2);
                        editor.putInt("FriSport", 0);
                        editor.putInt("SunSport", 2);
                    }
                }
                editor.commit();
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
        }
    }
}