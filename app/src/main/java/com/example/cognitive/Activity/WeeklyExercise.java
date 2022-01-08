package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cognitive.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeeklyExercise extends AppCompatActivity {
    private SharedPreferences sp1;
    private SharedPreferences spWeekReport;
    private String TAG="WeeklyExercise";
    private TextView txtSportTaskTip;
    private CardView cvSport1;
    private CardView cvSport2;
    private CardView cvSport3;
    private CardView cvSport4;
    private CardView cvSport41;
    private CardView cvSport5;
    private CardView cvSport6;
    private CardView cvSport61;
    private CardView cvSport7;
    private ImageView imgMon;
    private ImageView imgTue;
    private ImageView imgWen;
    private ImageView imgThur;
    private ImageView imgThur1;
    private ImageView imgFri;
    private ImageView imgSat;
    private ImageView imgSat1;
    private ImageView imgSun;
    private int everyWeekSport;
    private int weeklyPowerSport;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_exercise);
        sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        spWeekReport=this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        everyWeekSport = sp1.getInt("everyWeekSport",4);
        Log.d(TAG,"everyWeekSport:"+everyWeekSport);
        weeklyPowerSport = sp1.getInt("weeklyPowerSport",2);
        Log.d(TAG,"weeklyPowerSport:"+weeklyPowerSport);
        txtSportTaskTip=(TextView) findViewById(R.id.txt_sport_task_tip);
        cvSport1=(CardView) findViewById(R.id.cv_sport1);
        cvSport2=(CardView) findViewById(R.id.cv_sport2);
        cvSport3=(CardView) findViewById(R.id.cv_sport3);
        cvSport4=(CardView) findViewById(R.id.cv_sport4);
        cvSport41=(CardView) findViewById(R.id.cv_sport4_1);
        cvSport5=(CardView) findViewById(R.id.cv_sport5);
        cvSport6=(CardView) findViewById(R.id.cv_sport6);
        cvSport61=(CardView) findViewById(R.id.cv_sport6_1);
        cvSport7=(CardView) findViewById(R.id.cv_sport7);
        int sport1;
        int sport2;
        int sport3;
        int sport4;
        int sport5;
        int sport6;
        int sport7;
        sport1=sp1.getInt("MonSport",0);
        sport2=sp1.getInt("TueSport",0);
        sport3=sp1.getInt("WenSport",0);
        sport4=sp1.getInt("ThurSport",0);
        sport5=sp1.getInt("FriSport",0);
        sport6=sp1.getInt("SatSport",0);
        sport7=sp1.getInt("SunSport",0);
        if(sport1==0&&sport2==0&&sport3==0&&sport4==0&&sport5==0&&sport6==0&&sport7==0) {

        } else {
            txtSportTaskTip.setVisibility(View.GONE);
        }
        if(sport1==0) {
            cvSport1.setVisibility(View.GONE);
        }
        if(sport2==0) {
            cvSport2.setVisibility(View.GONE);
        }
        if(sport3==0) {
            cvSport3.setVisibility(View.GONE);
        }
        if(sport4==0) {
            cvSport4.setVisibility(View.GONE);
            cvSport41.setVisibility(View.GONE);
        } else if (sport4==1) {
            cvSport41.setVisibility(View.GONE);
        } else {
            cvSport4.setVisibility(View.GONE);
        }
        if(sport5==0) {
            cvSport5.setVisibility(View.GONE);
        }
        if(sport6==0) {
            cvSport6.setVisibility(View.GONE);
            cvSport61.setVisibility(View.GONE);
        } else if (sport6==1) {
            cvSport61.setVisibility(View.GONE);
        } else {
            cvSport6.setVisibility(View.GONE);
        }
        if(sport7==0) {
            cvSport7.setVisibility(View.GONE);
        }
        int sportReal1=spWeekReport.getInt("MonReal",0);
        int sportReal2=spWeekReport.getInt("TueReal",0);
        int sportReal3=spWeekReport.getInt("WenReal",0);
        int sportReal4=spWeekReport.getInt("ThurReal",0);
        int sportReal5=spWeekReport.getInt("FriReal",0);
        int sportReal6=spWeekReport.getInt("SatReal",0);
        int sportReal7=spWeekReport.getInt("SunReal",0);

        /*if(weeklyPowerSport==1){
            cvSport4.setVisibility(View.GONE);
            cvSport6.setVisibility(View.GONE);
            if(everyWeekSport==3){
                cvSport2.setVisibility(View.GONE);
                cvSport41.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==4){
                cvSport2.setVisibility(View.GONE);
                cvSport41.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==5){
                cvSport2.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==6){
                cvSport2.setVisibility(View.GONE);
            }
        }
        if(weeklyPowerSport==2){
            cvSport41.setVisibility(View.GONE);
            cvSport6.setVisibility(View.GONE);
            if(everyWeekSport==3){
                cvSport2.setVisibility(View.GONE);
                cvSport3.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==4){
                cvSport2.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==5){
                cvSport3.setVisibility(View.GONE);
                cvSport61.setVisibility(View.GONE);
            } else if (everyWeekSport==6){
                cvSport2.setVisibility(View.GONE);
            }
        }
        if(weeklyPowerSport==3){
            cvSport41.setVisibility(View.GONE);
            cvSport61.setVisibility(View.GONE);
            if(everyWeekSport==3){
                cvSport2.setVisibility(View.GONE);
                cvSport3.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==4){
                cvSport3.setVisibility(View.GONE);
                cvSport5.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==5){
                cvSport3.setVisibility(View.GONE);
                cvSport7.setVisibility(View.GONE);
            } else if (everyWeekSport==6){
                cvSport5.setVisibility(View.GONE);
            }
        }*/
        cvSport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPowerSport dialogPowerSport = new DialogPowerSport(WeeklyExercise.this,new DialogPowerSport.DataBackListener()
                {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("powerSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgMon=findViewById(R.id.img_Mon);
                            imgMon.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport1.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int powerTime=spTaskTime.getInt("powerTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "力量训练");//设置参数
                            intent.putExtra("taskTime", 1200);//设置参数
                            intent.putExtra("finishTime", powerTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 31); // requestCode -> 31,power Mon
                        }
                    }
                });
                dialogPowerSport.show();
            }
        });
        cvSport2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(WeeklyExercise.this,new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgTue=findViewById(R.id.img_Tue);
                            imgTue.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport2.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 42); // requestCode -> 42,other sport Tue
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        cvSport3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(WeeklyExercise.this,new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgWen=findViewById(R.id.img_Wen);
                            imgWen.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport3.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 43); // requestCode -> 43,other sport Wen
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        cvSport4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPowerSport dialogPowerSport = new DialogPowerSport(WeeklyExercise.this,new DialogPowerSport.DataBackListener()
                {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("powerSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgThur=findViewById(R.id.img_Thur);
                            imgThur.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport4.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int powerTime=spTaskTime.getInt("powerTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "力量训练");//设置参数
                            intent.putExtra("taskTime", 1200);//设置参数
                            intent.putExtra("finishTime", powerTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 34); // requestCode -> 34,power Thu
                        }
                    }
                });
                dialogPowerSport.show();
            }
        });
        cvSport41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(WeeklyExercise.this,new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgThur1=findViewById(R.id.img_Thur1);
                            imgThur1.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport41.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 44); // requestCode -> 44,other sport Thu
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        cvSport5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(WeeklyExercise.this,new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgFri=findViewById(R.id.img_Fri);
                            imgFri.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport5.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 45); // requestCode -> 45,other sport Fri
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        cvSport6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPowerSport dialogPowerSport = new DialogPowerSport(WeeklyExercise.this,new DialogPowerSport.DataBackListener()
                {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("powerSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgSat=findViewById(R.id.img_Sat);
                            imgSat.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport6.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int powerTime=spTaskTime.getInt("powerTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "力量训练");//设置参数
                            intent.putExtra("taskTime", 1200);//设置参数
                            intent.putExtra("finishTime", powerTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 36); // requestCode -> 36,power Sat
                        }
                    }
                });
                dialogPowerSport.show();
            }
        });
        cvSport61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(WeeklyExercise.this,new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgSat1=findViewById(R.id.img_Sat1);
                            imgSat1.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport61.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 46); // requestCode -> 46,other sport Sat
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        cvSport7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogOtherSport dialogOtherSport = new DialogOtherSport(WeeklyExercise.this,new DialogOtherSport.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        int result = data;
                        if(result==1) {
                            /*SharedPreferences spFinishTask;
                            spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = spFinishTask.edit();
                            editor.putInt("otherSportFinish", 0);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            //获取当前日期
                            Date date = new Date(System.currentTimeMillis());
                            editor.putString("finishDate",simpleDateFormat.format(date));
                            editor.commit();
                            SharedPreferences spWeekReport;
                            spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                            imgSun=findViewById(R.id.img_Sun);
                            imgSun.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                            cvSport7.setOnClickListener(null);*/
                            SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                            int sportTime=spTaskTime.getInt("sportTime",0);
                            Intent intent = new Intent();
                            //前一个（MainActivity.this）是目前页面，后面一个是要跳转的下一个页面
                            intent.setClass(WeeklyExercise.this, TimeCounter.class);
                            intent.putExtra("taskName", "其他运动");//设置参数
                            intent.putExtra("taskTime", 1800);//设置参数
                            intent.putExtra("finishTime", sportTime);//设置参数
                            //startActivity(intent);
                            startActivityForResult(intent, 47); // requestCode -> 47,other sport Sun
                        }
                    }
                });
                dialogOtherSport.show();
            }
        });
        if(sportReal1==1){
            imgMon=findViewById(R.id.img_Mon);
            imgMon.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport1.setOnClickListener(null);
        }
        if(sportReal2==1){
            imgTue=findViewById(R.id.img_Tue);
            imgTue.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport2.setOnClickListener(null);
        }
        if(sportReal3==1){
            imgWen=findViewById(R.id.img_Wen);
            imgWen.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport3.setOnClickListener(null);
        }
        if(sportReal4==1){
            imgThur=findViewById(R.id.img_Thur);
            imgThur.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport4.setOnClickListener(null);
            imgThur1=findViewById(R.id.img_Thur1);
            imgThur1.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport41.setOnClickListener(null);
        }
        if(sportReal5==1){
            imgFri=findViewById(R.id.img_Fri);
            imgFri.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport5.setOnClickListener(null);
        }
        if(sportReal6==1){
            imgSat=findViewById(R.id.img_Sat);
            imgSat.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport6.setOnClickListener(null);
            imgSat1=findViewById(R.id.img_Sat1);
            imgSat1.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport61.setOnClickListener(null);
        }
        if(sportReal7==1){
            imgSun=findViewById(R.id.img_Sun);
            imgSun.setImageDrawable(getResources().getDrawable(R.drawable.finished));
            cvSport7.setOnClickListener(null);
        }
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        if(weekDay==1){
            cvSport1.setOnClickListener(null);
            cvSport1.setAlpha(0.5f);
            cvSport2.setOnClickListener(null);
            cvSport2.setAlpha(0.5f);
            cvSport3.setOnClickListener(null);
            cvSport3.setAlpha(0.5f);
            cvSport4.setOnClickListener(null);
            cvSport4.setAlpha(0.5f);
            cvSport41.setOnClickListener(null);
            cvSport41.setAlpha(0.5f);
            cvSport5.setOnClickListener(null);
            cvSport5.setAlpha(0.5f);
            cvSport6.setOnClickListener(null);
            cvSport6.setAlpha(0.5f);
            cvSport61.setOnClickListener(null);
            cvSport61.setAlpha(0.5f);
        } else if(weekDay==2) {
            cvSport2.setOnClickListener(null);
            cvSport2.setAlpha(0.5f);
            cvSport3.setOnClickListener(null);
            cvSport3.setAlpha(0.5f);
            cvSport4.setOnClickListener(null);
            cvSport4.setAlpha(0.5f);
            cvSport41.setOnClickListener(null);
            cvSport41.setAlpha(0.5f);
            cvSport5.setOnClickListener(null);
            cvSport5.setAlpha(0.5f);
            cvSport6.setOnClickListener(null);
            cvSport6.setAlpha(0.5f);
            cvSport61.setOnClickListener(null);
            cvSport61.setAlpha(0.5f);
            cvSport7.setOnClickListener(null);
            cvSport7.setAlpha(0.5f);
        } else if(weekDay==3) {
            cvSport1.setOnClickListener(null);
            cvSport1.setAlpha(0.5f);
            cvSport3.setOnClickListener(null);
            cvSport3.setAlpha(0.5f);
            cvSport4.setOnClickListener(null);
            cvSport4.setAlpha(0.5f);
            cvSport41.setOnClickListener(null);
            cvSport41.setAlpha(0.5f);
            cvSport5.setOnClickListener(null);
            cvSport5.setAlpha(0.5f);
            cvSport6.setOnClickListener(null);
            cvSport6.setAlpha(0.5f);
            cvSport61.setOnClickListener(null);
            cvSport61.setAlpha(0.5f);
            cvSport7.setOnClickListener(null);
            cvSport7.setAlpha(0.5f);
        } else if(weekDay==4) {
            cvSport1.setOnClickListener(null);
            cvSport1.setAlpha(0.5f);
            cvSport2.setOnClickListener(null);
            cvSport2.setAlpha(0.5f);
            cvSport4.setOnClickListener(null);
            cvSport4.setAlpha(0.5f);
            cvSport41.setOnClickListener(null);
            cvSport41.setAlpha(0.5f);
            cvSport5.setOnClickListener(null);
            cvSport5.setAlpha(0.5f);
            cvSport6.setOnClickListener(null);
            cvSport6.setAlpha(0.5f);
            cvSport61.setOnClickListener(null);
            cvSport61.setAlpha(0.5f);
            cvSport7.setOnClickListener(null);
            cvSport7.setAlpha(0.5f);
        } else if(weekDay==5) {
            cvSport1.setOnClickListener(null);
            cvSport1.setAlpha(0.5f);
            cvSport2.setOnClickListener(null);
            cvSport2.setAlpha(0.5f);
            cvSport3.setOnClickListener(null);
            cvSport3.setAlpha(0.5f);
            cvSport5.setOnClickListener(null);
            cvSport5.setAlpha(0.5f);
            cvSport6.setOnClickListener(null);
            cvSport6.setAlpha(0.5f);
            cvSport61.setOnClickListener(null);
            cvSport61.setAlpha(0.5f);
            cvSport7.setOnClickListener(null);
            cvSport7.setAlpha(0.5f);
        } else if(weekDay==6) {
            cvSport1.setOnClickListener(null);
            cvSport1.setAlpha(0.5f);
            cvSport2.setOnClickListener(null);
            cvSport2.setAlpha(0.5f);
            cvSport3.setOnClickListener(null);
            cvSport3.setAlpha(0.5f);
            cvSport4.setOnClickListener(null);
            cvSport4.setAlpha(0.5f);
            cvSport41.setOnClickListener(null);
            cvSport41.setAlpha(0.5f);
            cvSport6.setOnClickListener(null);
            cvSport6.setAlpha(0.5f);
            cvSport61.setOnClickListener(null);
            cvSport61.setAlpha(0.5f);
            cvSport7.setOnClickListener(null);
            cvSport7.setAlpha(0.5f);
        } else if(weekDay==7) {
            cvSport1.setOnClickListener(null);
            cvSport1.setAlpha(0.5f);
            cvSport2.setOnClickListener(null);
            cvSport2.setAlpha(0.5f);
            cvSport3.setOnClickListener(null);
            cvSport3.setAlpha(0.5f);
            cvSport4.setOnClickListener(null);
            cvSport4.setAlpha(0.5f);
            cvSport41.setOnClickListener(null);
            cvSport41.setAlpha(0.5f);
            cvSport5.setOnClickListener(null);
            cvSport5.setAlpha(0.5f);
            cvSport7.setOnClickListener(null);
            cvSport7.setAlpha(0.5f);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
// operation succeeded. 默认值是-1
        if (resultCode == 2) {
            if (requestCode == 31||requestCode == 34||requestCode == 36) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //设置结果显示框的显示数值
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                int trueFinish=spTaskTime.getInt("powerTime",0);
                trueFinish=trueFinish+finishTime;
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("powerTime",trueFinish);
                editorTime.commit();
            }
            if (requestCode == 42||requestCode == 43||requestCode == 44||requestCode == 45||requestCode == 46||requestCode == 47) {
                int finishTime = data.getIntExtra("finishTime", 0);
                Log.e(TAG,"finishTime:"+finishTime);
                //设置结果显示框的显示数值
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                int trueFinish=spTaskTime.getInt("sportTime",0);
                trueFinish=trueFinish+finishTime;
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",trueFinish);
                editorTime.commit();
            }
        }
        if (resultCode==1) {
            if (requestCode == 31) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("powerSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgMon=findViewById(R.id.img_Mon);
                imgMon.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport1.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("powerTime",0);
                editorTime.commit();
            }
            if (requestCode == 42) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgTue=findViewById(R.id.img_Tue);
                imgTue.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport2.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
            if(requestCode==43) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgWen=findViewById(R.id.img_Wen);
                imgWen.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport3.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
            if (requestCode == 34) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("powerSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgThur=findViewById(R.id.img_Thur);
                imgThur.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport4.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("powerTime",0);
                editorTime.commit();
            }
            if(requestCode==44) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgThur1=findViewById(R.id.img_Thur1);
                imgThur1.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport41.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
            if(requestCode==45) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgFri=findViewById(R.id.img_Fri);
                imgFri.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport5.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
            if (requestCode == 36) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("powerSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgSat=findViewById(R.id.img_Sat);
                imgSat.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport6.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("powerTime",0);
                editorTime.commit();
            }
            if(requestCode==46) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgSat1=findViewById(R.id.img_Sat1);
                imgSat1.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport61.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
            if(requestCode==47) {
                SharedPreferences spFinishTask;
                spFinishTask=WeeklyExercise.this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("otherSportFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                SharedPreferences spWeekReport;
                spWeekReport=WeeklyExercise.this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
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
                imgSun=findViewById(R.id.img_Sun);
                imgSun.setImageDrawable(getResources().getDrawable(R.drawable.finished));
                cvSport7.setOnClickListener(null);
                SharedPreferences spTaskTime=WeeklyExercise.this.getSharedPreferences("userTaskTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorTime=spTaskTime.edit();
                editorTime.putInt("sportTime",0);
                editorTime.commit();
            }
        }
    }
}
