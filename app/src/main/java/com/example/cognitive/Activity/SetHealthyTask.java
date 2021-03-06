package com.example.cognitive.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
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
import androidx.appcompat.widget.Toolbar;

import com.example.cognitive.R;
import com.example.cognitive.Utils.AppManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;

public class SetHealthyTask extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private SharedPreferences sp1;
    private SharedPreferences spTestScore;
    private HashMap<String, String> stringHashMap;
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
    private CheckBox cbRead;
    private CheckBox cbHobby;
    private CheckBox cbSmile;
    private CheckBox cbDiary;
    private FancyButton btnConfirmTask;
    private int getUpHour=8;
    private int getUpMinute=0;
    private int sleepHour=23;
    private int sleepMinute=0;
    private int stepGoal;
    private int sportTime;
    private int powerTime;
    private int userID;
    private String getupTime="8:00:00";
    private String sleepTime="23:00:00";
    private int postStepGoal=7000;
    private int everyWeekSport=4;
    private int weeklyPowerSport=2;
    private int drinkGoal=0;
    private int readGoal=0;
    private int hobbyGoal=0;
    private int smileGoal=0;
    private int diaryGoal=0;
    private String setDate;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_healthy_task);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//???????????????????????????
        getSupportActionBar().setHomeButtonEnabled(true); //?????????????????????
        stringHashMap = new HashMap<>();
        spTestScore= this.getSharedPreferences("userTestScore", Context.MODE_PRIVATE);
        int strengthScore=spTestScore.getInt("strengthScore", 3);
        strengthScore=3-strengthScore;
        Log.d(TAG,"strengthScore:"+strengthScore);
        int healthScore=spTestScore.getInt("healthScore", 2);
        healthScore=2-healthScore;
        Log.d(TAG,"healthScore:"+healthScore);
        int judgementScore=spTestScore.getInt("judgementScore", 1);
        Log.d(TAG,"judgementScore:"+judgementScore);
        int memoryScore=spTestScore.getInt("memoryScore", 5);
        memoryScore=5-memoryScore;
        Log.d(TAG,"memoryScore:"+memoryScore);
        int cognitionScore=spTestScore.getInt("cognitionScore", 4);
        Log.d(TAG,"cognitionScore:"+cognitionScore);
        if(strengthScore==3) {
            postStepGoal=7000;
            everyWeekSport=5;
            weeklyPowerSport=2;
        } else if(strengthScore==2) {
            postStepGoal=6000;
            everyWeekSport=4;
            weeklyPowerSport=2;
        } else if(strengthScore==1) {
            postStepGoal=6000;
            everyWeekSport=4;
            weeklyPowerSport=1;
        } else if(strengthScore==0) {
            postStepGoal=5000;
            everyWeekSport=3;
            weeklyPowerSport=1;
        }

        btnGetUpTime= (Button) findViewById(R.id.btn_getUpTime);
        txtGetUpTime= (TextView) findViewById(R.id.txt_getUpTime);
        txtGetUpTime.setText("8:00");
        btnGetUpTime.setOnClickListener(this);
        btnSleepTime= (Button) findViewById(R.id.btn_sleepTime);
        txtSleepTime= (TextView) findViewById(R.id.txt_sleepTime);
        txtSleepTime.setText("23:00");
        btnSleepTime.setOnClickListener(this);
        txtSetStep= (TextView) findViewById(R.id.txt_setStep);
        txtSetStep.setText("????????????"+postStepGoal+"???");
        btnEverydayStep= (Button) findViewById(R.id.btn_everydayStep);
        btnEverydayStep.setOnClickListener(this);
        txtSportTime= (TextView) findViewById(R.id.txt_setSportTime);
        txtSportTime.setText("????????????"+everyWeekSport+"???");
        btnEveryWeekSport= (Button) findViewById(R.id.btn_everyWeekSport);
        btnEveryWeekSport.setOnClickListener(this);
        txtPowerTime= (TextView) findViewById(R.id.txt_setPowerTime);
        txtPowerTime.setText("??????????????????"+weeklyPowerSport+"???");
        btnEveryWeekPower= (Button) findViewById(R.id.btn_everyWeekPower);
        btnEveryWeekPower.setOnClickListener(this);
        cbDrinkWater=(CheckBox) findViewById(R.id.cb_drinkWater);
        cbDrinkWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbDrinkWater.setText("?????????");
                    Log.d(TAG,"????????????");
                    drinkGoal=0;
                }else{
                    cbDrinkWater.setText("??????");
                    Log.d(TAG,"???????????????");
                    drinkGoal=1;
                }
            }
        });
        cbRead=(CheckBox) findViewById(R.id.cb_read);
        cbRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbRead.setText("?????????");
                    Log.d(TAG,"????????????");
                    readGoal=0;
                }else{
                    cbRead.setText("??????");
                    Log.d(TAG,"???????????????");
                    readGoal=1;
                }
            }
        });
        cbHobby=(CheckBox) findViewById(R.id.cb_hobby);
        cbHobby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbHobby.setText("?????????");
                    Log.d(TAG,"??????????????????");
                    hobbyGoal=0;
                }else{
                    cbHobby.setText("??????");
                    Log.d(TAG,"?????????????????????");
                    hobbyGoal=1;
                }
            }
        });
        cbSmile=(CheckBox) findViewById(R.id.cb_smile);
        cbSmile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbSmile.setText("?????????");
                    Log.d(TAG,"??????????????????");
                    smileGoal=0;
                }else{
                    cbSmile.setText("??????");
                    Log.d(TAG,"?????????????????????");
                    smileGoal=1;
                }
            }
        });
        cbDiary=(CheckBox) findViewById(R.id.cb_diary);
        if (memoryScore<=3){
            cbDiary.setChecked(true);
        }
        cbDiary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbDiary.setText("?????????");
                    Log.d(TAG,"???????????????");
                    diaryGoal=0;
                }else{
                    cbDiary.setText("??????");
                    Log.d(TAG,"??????????????????");
                    diaryGoal=1;
                }
            }
        });
        btnConfirmTask=(FancyButton) findViewById(R.id.btn_confirm_task);
        btnConfirmTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getUpTime:
                TimePickerDialog dialog=new TimePickerDialog(this,3, new TimePickerDialog.OnTimeSetListener() {
//????????????????????????set?????????????????????
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
                        String str;
                        str=String.valueOf(getUpHour);
                        getupTime=str+":";
                        str=String.valueOf(getUpMinute);
                        if (getUpMinute < 10){
                            getupTime=getupTime+"0"+str+":00";
                        }else {
                            getupTime=getupTime+str+":00";
                        }
                    }

                }, 8, 0, true);
                dialog.show();
                break;
            case R.id.btn_sleepTime:
                TimePickerDialog dialog1=new TimePickerDialog(this,3, new TimePickerDialog.OnTimeSetListener() {
                    //????????????????????????set?????????????????????
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
                        String str;
                        str=String.valueOf(sleepHour);
                        sleepTime=str+":";
                        str=String.valueOf(sleepMinute);
                        if (getUpMinute < 10){
                            sleepTime=sleepTime+"0"+str+":00";
                        }else {
                            sleepTime=sleepTime+str+":00";
                        }
                    }

                }, 23, 0, true);
                dialog1.show();
                break;
            case R.id.btn_everydayStep:
                final String items[] = {"5000", "6000", "7000","8000","9000","10000"};
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("???????????????????????????");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String everydayStep=items[which];
                        stepGoal=Integer.parseInt(everydayStep);
                    }});
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        txtSetStep.setText("????????????"+stepGoal+"???");
                        Log.d(TAG,"?????????????????????"+stepGoal);
                        postStepGoal=stepGoal;
                        dialog.dismiss();

                    }});
                builder.create().show();
                break;
            case R.id.btn_everyWeekSport:
                final String items1[] = {"3", "4", "5", "6"};
                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setTitle("?????????????????????????????????");
                builder1.setSingleChoiceItems(items1, -1, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str_sportTime=items1[which];
                        sportTime=Integer.parseInt(str_sportTime);
                    }});
                builder1.setPositiveButton("??????", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        txtSportTime.setText("????????????"+sportTime+"???");
                        Log.d(TAG,"?????????????????????"+sportTime);
                        everyWeekSport=sportTime;
                        dialog.dismiss();
                    }});
                builder1.create().show();
                break;
            case R.id.btn_everyWeekPower:
                final String items2[] = {"1", "2", "3"};
                AlertDialog.Builder builder2=new AlertDialog.Builder(this);
                builder2.setTitle("???????????????????????????????????????");
                builder2.setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strPowerTime=items2[which];
                        powerTime=Integer.parseInt(strPowerTime);
                    }});
                builder2.setPositiveButton("??????", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        txtPowerTime.setText("??????????????????"+powerTime+"???");
                        Log.d(TAG,"???????????????????????????"+powerTime);
                        weeklyPowerSport=powerTime;
                        dialog.dismiss();
                    }});
                builder2.create().show();
                break;
            case R.id.btn_confirm_task:
                sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                userID=sp.getInt("USER_ID",0);
                Log.d(TAG,"userID:"+userID);
                stringHashMap.put("userID", String.valueOf(userID));
                Log.d(TAG,"getupTime:"+getupTime);
                stringHashMap.put("getupTime", getupTime);
                editor.putString("getupTime", getupTime);
                Log.d(TAG,"sleepTime:"+sleepTime);
                stringHashMap.put("sleepTime", sleepTime);
                editor.putString("sleepTime", sleepTime);
                Log.d(TAG,"stepGoal:"+postStepGoal);
                stringHashMap.put("stepGoal", String.valueOf(postStepGoal));
                editor.putInt("stepGoal", postStepGoal);
                Log.d(TAG,"everyWeekSport:"+everyWeekSport);
                stringHashMap.put("everyWeekSport", String.valueOf(everyWeekSport));
                editor.putInt("everyWeekSport", everyWeekSport);
                Log.d(TAG,"weeklyPowerSport:"+weeklyPowerSport);
                stringHashMap.put("weeklyPowerSport", String.valueOf(weeklyPowerSport));
                editor.putInt("weeklyPowerSport", weeklyPowerSport);
                Log.d(TAG,"drinkGoal:"+drinkGoal);
                stringHashMap.put("drinkGoal", String.valueOf(drinkGoal));
                editor.putInt("drinkGoal", drinkGoal);
                Log.d(TAG,"readGoal:"+readGoal);
                stringHashMap.put("readGoal", String.valueOf(readGoal));
                editor.putInt("readGoal", readGoal);
                Log.d(TAG,"hobbyGoal:"+hobbyGoal);
                stringHashMap.put("hobbyGoal", String.valueOf(hobbyGoal));
                editor.putInt("hobbyGoal", hobbyGoal);
                Log.d(TAG,"smileGoal:"+smileGoal);
                stringHashMap.put("smileGoal", String.valueOf(smileGoal));
                editor.putInt("smileGoal", smileGoal);
                Log.d(TAG,"diaryGoal:"+diaryGoal);
                stringHashMap.put("diaryGoal", String.valueOf(diaryGoal));
                editor.putInt("diaryGoal", diaryGoal);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                setDate=formatter.format(date);
                Log.d(TAG,"setDate:"+setDate);
                stringHashMap.put("setDate", setDate);
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

                new Thread(postRun).start();
                Intent intent = new Intent(SetHealthyTask.this, FinishHealthyTask.class);
                AppManager.getAppManager().finishActivity(FinishHealthyTask.class);
                startActivity(intent);
                finish();

            default:
                break;
        }

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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/SetCheckServlet";
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
                            Log.d(TAG,"?????????????????????");
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
