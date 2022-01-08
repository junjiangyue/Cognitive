package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cognitive.R;
import com.example.cognitive.Utils.AppManager;
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

public class FinishHealthyTask extends AppCompatActivity implements View.OnClickListener {
    private String TAG="FinishHealthyTask";
    private SharedPreferences sp;//账号
    private SharedPreferences sp1;//打卡设置
    private SharedPreferences spFinishTask;//完成打卡
    private SharedPreferences spStepTask;//步数
    private HashMap<String, String> stringHashMap;
    private HashMap<String, String> stringHashMap1;
    private TextView txtSetHealthyTask;
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
    private Button btnTaskHistory;
    private int userID;
    private String getupTime;
    private String sleepTime;
    private int postStepGoal;
    private int drinkGoal;
    private int readGoal;
    private int hobbyGoal;
    private int smileGoal;
    private int diaryGoal;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_healthy_task);
        AppManager.getAppManager().addActivity(this);//界面栈
        DestroyActivityUtil.addActivity(FinishHealthyTask.this);
        stringHashMap = new HashMap<>();
        stringHashMap1 = new HashMap<>();
        txtSetHealthyTask=(TextView) findViewById(R.id.txt_setHealthyTask);
        txtSetHealthyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                intent.setClass(FinishHealthyTask.this,SetHealthyTask.class);
                startActivity(intent);
            }
        });
        //获取打卡项目设置
        sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
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
        cvGetup=(CardView) findViewById(R.id.cv_getup);
        txtGetupReal= (TextView) findViewById(R.id.txt_GetupReal);
        //txtGetupReal.setText("6:45");
        txtGetupGoal= (TextView) findViewById(R.id.txt_GetupGoal);
        txtGetupGoal.setText(" / "+getupTime);
        cvSleep=(CardView) findViewById(R.id.cv_sleep);
        txtSleepReal= (TextView) findViewById(R.id.txt_SleepReal);
        //txtSleepReal.setText("22:45");
        txtSleepGoal= (TextView) findViewById(R.id.txt_SleepGoal);
        txtSleepGoal.setText(" / "+sleepTime);
        cvStep=(CardView) findViewById(R.id.cv_step);
        txtStepReal= (TextView) findViewById(R.id.txt_StepReal);
        //txtStepReal.setText("4567");
        spStepTask=this.getSharedPreferences("userStepTask", Context.MODE_PRIVATE);
        int step;
        step=spStepTask.getInt("stepFinish",0);
        txtStepReal.setText(String.valueOf(step));
        txtStepGoal= (TextView) findViewById(R.id.txt_StepGoal);
        txtStepGoal.setText(" / "+postStepGoal);
        cvWater=(CardView) findViewById(R.id.cv_water);
        txtWaterReal= (TextView) findViewById(R.id.txt_WaterReal);
        //txtWaterReal.setText("1.5L");
        txtWaterGoal= (TextView) findViewById(R.id.txt_WaterGoal);
        txtWaterGoal.setText(" / 1.5L");
        /*if(drinkGoal==1) {
            cvWater.setVisibility(View.GONE);
        }*/
        cvRead=(CardView) findViewById(R.id.cv_read);
        txtReadReal= (TextView) findViewById(R.id.txt_ReadReal);
        txtReadReal.setText("0");
        txtReadGoal= (TextView) findViewById(R.id.txt_ReadGoal);
        txtReadGoal.setText("分钟 / 30分钟");
        /*if(readGoal==1) {
            cvRead.setVisibility(View.GONE);
        }*/
        cvHobby=(CardView) findViewById(R.id.cv_hobby);
        txtHobbyReal= (TextView) findViewById(R.id.txt_HobbyReal);
        txtHobbyReal.setText("0");
        txtHobbyGoal= (TextView) findViewById(R.id.txt_HobbyGoal);
        txtHobbyGoal.setText("分钟 / 45分钟");
        /*if(hobbyGoal==1) {
            cvHobby.setVisibility(View.GONE);
        }*/
        cvSmile=(CardView) findViewById(R.id.cv_smile);
        txtSmileReal= (TextView) findViewById(R.id.txt_SmileReal);
        txtSmileReal.setText("0");
        txtSmileGoal= (TextView) findViewById(R.id.txt_SmileGoal);
        txtSmileGoal.setText(" / 1次");
        /*if(smileGoal==1) {
            cvSmile.setVisibility(View.GONE);
        }*/
        cvDiary=(CardView) findViewById(R.id.cv_diary);
        txtDairyReal= (TextView) findViewById(R.id.txt_DairyReal);
        txtDairyReal.setText("0");
        txtDairyGoal= (TextView) findViewById(R.id.txt_DairyGoal);
        txtDairyGoal.setText(" / 1篇");
        /*if(diaryGoal==1) {
            cvDiary.setVisibility(View.GONE);
        }*/
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        int sport=0;
        if(weekDay==1){
            sport=sp1.getInt("SunSport",0);
        } else if(weekDay==2) {
            sport=sp1.getInt("MonSport",0);
        } else if(weekDay==3) {
            sport=sp1.getInt("TueSport",0);
        } else if(weekDay==4) {
            sport=sp1.getInt("WenSport",0);
        } else if(weekDay==5) {
            sport=sp1.getInt("ThurSport",0);
        } else if(weekDay==6) {
            sport=sp1.getInt("FriSport",0);
        } else if(weekDay==7) {
            sport=sp1.getInt("SatSport",0);
        }
        cvPowerSport=(CardView) findViewById(R.id.cv_power_sport);
        cvOtherSport=(CardView) findViewById(R.id.cv_other_sport);
        if(sport==0){
            cvPowerSport.setVisibility(View.GONE);
            cvOtherSport.setVisibility(View.GONE);
        } else if (sport==1){
            cvOtherSport.setVisibility(View.GONE);
        } else if (sport==2){
            cvPowerSport.setVisibility(View.GONE);
        }

        //是否设置过打卡
        txtSetTip=findViewById(R.id.txt_set_tip);
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
                DialogGetup dialogGetup = new DialogGetup(FinishHealthyTask.this,new DialogGetup.DataBackListener() {
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
                            spWeekReport=FinishHealthyTask.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            Log.d(TAG,"完成早起");
                            String getupFinish=spFinishTask.getString("getupFinish", null);
                            isDate2Bigger(getupFinish,getupTime);
                            imgGetup=findViewById(R.id.img_getup);
                            if(isDate2Bigger(getupFinish,getupTime)){
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
                DialogSleep dialogSleep = new DialogSleep(FinishHealthyTask.this,new DialogSleep.DataBackListener() {
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
                            spWeekReport=FinishHealthyTask.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
                            int sleepReal=spWeekReport.getInt("sleepReal",0);
                            if(isDate2Bigger(simpleDateFormat1.format(now),sleepTime)){
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
                            isDate2Bigger(sleepFinish,sleepTime);
                            imgSleep=findViewById(R.id.img_sleep);
                            if(isDate2Bigger(sleepFinish,sleepTime)){
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
                Intent intent = new Intent(FinishHealthyTask.this, DialogWalk.class);
                startActivity(intent);
            }
        });
        cvWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogWater dialogWater = new DialogWater(FinishHealthyTask.this,new DialogWater.DataBackListener() {
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
                            imgWater=findViewById(R.id.img_water);
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
                DialogRead dialogRead = new DialogRead(FinishHealthyTask.this,new DialogRead.DataBackListener() {
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
                            imgRead=findViewById(R.id.img_read);
                            imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtReadReal.setText("30");
                            cvRead.setOnClickListener(null);
                            cvRead.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int readTime=spTaskTime.getInt("readTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(FinishHealthyTask.this, TimeCounter.class);
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
                DialogHobby dialogHobby = new DialogHobby(FinishHealthyTask.this,new DialogHobby.DataBackListener() {
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
                            imgHobby=findViewById(R.id.img_hobby);
                            imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            txtHobbyReal.setText("45");
                            cvHobby.setOnClickListener(null);
                            cvHobby.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int hobbyTime=spTaskTime.getInt("hobbyTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(FinishHealthyTask.this, TimeCounter.class);
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
                DialogSmile dialogSmile = new DialogSmile(FinishHealthyTask.this,new DialogSmile.DataBackListener() {
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
                            imgSmile=findViewById(R.id.img_smile);
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
                DialogDiary dialogDiary = new DialogDiary(FinishHealthyTask.this,new DialogDiary.DataBackListener() {
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
                            imgDiary=findViewById(R.id.img_diary);
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
                DialogPowerSport dialogPowerSport = new DialogPowerSport(FinishHealthyTask.this,new DialogPowerSport.DataBackListener()
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
                            spWeekReport=FinishHealthyTask.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgPowerSport=findViewById(R.id.img_power_sport);
                            imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvPowerSport.setOnClickListener(null);
                            cvPowerSport.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int powerTime=spTaskTime.getInt("powerTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(FinishHealthyTask.this, TimeCounter.class);
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
                DialogOtherSport dialogOtherSport = new DialogOtherSport(FinishHealthyTask.this,new DialogOtherSport.DataBackListener() {
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
                            spWeekReport=FinishHealthyTask.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgOtherSport=findViewById(R.id.img_other_sport);
                            imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvOtherSport.setOnClickListener(null);
                            cvOtherSport.setAlpha(0.6f);*/
                            SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(FinishHealthyTask.this, TimeCounter.class);
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

        btnTaskHistory=(Button) findViewById(R.id.btn_task_history);
        btnTaskHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, TaskHistory.class);
                startActivity(intent);
            }
        });

        //判断某一项是否打卡了
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String today=simpleDateFormat.format(date);
        Log.d(TAG,"查日期："+today);
        spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        if(spFinishTask.getString("finishDate",null)==null) {
            Log.d(TAG,"userFinishTask为空");
        } else {
            String finishDate=spFinishTask.getString("finishDate",null);
            Log.d(TAG,"获取日期："+finishDate);

            if(finishDate.equals(today)) {
                if(spFinishTask.getInt("diaryFinish", 1)==0){
                    Log.d(TAG,"完成日记");
                    imgDiary=findViewById(R.id.img_diary);
                    imgDiary.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtDairyReal.setText("1");
                    cvDiary.setOnClickListener(null);
                    cvDiary.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("hobbyFinish", 1)==0){
                    Log.d(TAG,"完成兴趣爱好");
                    imgHobby=findViewById(R.id.img_hobby);
                    imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtHobbyReal.setText("45");
                    cvHobby.setOnClickListener(null);
                    cvHobby.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("smileFinish", 1)==0){
                    Log.d(TAG,"完成微笑");
                    imgSmile=findViewById(R.id.img_smile);
                    imgSmile.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtSmileReal.setText("1");
                    cvSmile.setOnClickListener(null);
                    cvSmile.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("readFinish", 1)==0){
                    Log.d(TAG,"完成阅读");
                    imgRead=findViewById(R.id.img_read);
                    imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtReadReal.setText("30");
                    cvRead.setOnClickListener(null);
                    cvRead.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("waterFinish", 1)==0){
                    Log.d(TAG,"完成饮水");
                    imgWater=findViewById(R.id.img_water);
                    imgWater.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    txtWaterReal.setText("1.5L");
                    cvWater.setOnClickListener(null);
                    cvWater.setAlpha(0.6f);
                }
                if(spFinishTask.getString("getupFinish", null)!=null){
                    Log.d(TAG,"完成早起");
                    String getupFinish=spFinishTask.getString("getupFinish", null);
                    isDate2Bigger(getupFinish,getupTime);
                    imgGetup=findViewById(R.id.img_getup);
                    if(isDate2Bigger(getupFinish,getupTime)){
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
                    isDate2Bigger(sleepFinish,sleepTime);
                    imgSleep=findViewById(R.id.img_sleep);
                    if(isDate2Bigger(sleepFinish,sleepTime)){
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
                    imgPowerSport=findViewById(R.id.img_power_sport);
                    imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                    cvPowerSport.setOnClickListener(null);
                    cvPowerSport.setAlpha(0.6f);
                }
                if(spFinishTask.getInt("otherSportFinish", 1)==0) {
                    Log.d(TAG,"完成其他运动");
                    imgOtherSport=findViewById(R.id.img_other_sport);
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
            imgStep=findViewById(R.id.img_step);
            imgStep.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvStep.setAlpha(0.6f);
        }

        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap.put("userID", String.valueOf(userID));
        new Thread(postRun).start();//获取打卡设置

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

    private void uploadTaskHistory(){
        int taskNum=0;//总任务数
        int taskRealNum=0;//完成任务数
        SharedPreferences spWeekReport;
        spWeekReport=this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        Log.d(TAG,"传打卡记录");
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap1.put("userID", String.valueOf(userID));
        String getupFinish=spFinishTask.getString("getupFinish", null);
        stringHashMap1.put("getupTime", getupFinish);
        taskNum=taskNum+1;
        if(isDate2Bigger(getupFinish,getupTime)){
            taskRealNum=taskRealNum+1;
        }
        String sleepFinish=spFinishTask.getString("sleepFinish", null);
        stringHashMap1.put("sleepTime", sleepFinish);
        taskNum=taskNum+1;
        if(isDate2Bigger(sleepFinish,sleepTime)){
            taskRealNum=taskRealNum+1;
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
    @Override
    public void onClick(View view) {

    }
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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetCheckServlet";
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
                            Log.d(TAG,"获取打卡设置成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"获取打卡设置失败");
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
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                imgRead=findViewById(R.id.img_read);
                imgRead.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                txtReadReal.setText("30");
                cvRead.setOnClickListener(null);
                cvRead.setAlpha(0.6f);
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                imgHobby=findViewById(R.id.img_hobby);
                imgHobby.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                txtHobbyReal.setText("45");
                cvHobby.setOnClickListener(null);
                cvHobby.setAlpha(0.6f);
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                spWeekReport=FinishHealthyTask.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgPowerSport=findViewById(R.id.img_power_sport);
                imgPowerSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvPowerSport.setOnClickListener(null);
                cvPowerSport.setAlpha(0.6f);
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
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
                spWeekReport=FinishHealthyTask.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgOtherSport=findViewById(R.id.img_other_sport);
                imgOtherSport.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvOtherSport.setOnClickListener(null);
                cvOtherSport.setAlpha(0.6f);
                SharedPreferences spTaskTime=FinishHealthyTask.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
        }
    }


}
