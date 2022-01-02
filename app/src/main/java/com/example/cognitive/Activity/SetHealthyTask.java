package com.example.cognitive.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
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
        stringHashMap = new HashMap<>();
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
                    drinkGoal=0;
                }else{
                    cbDrinkWater.setText("选择");
                    Log.d(TAG,"不选择喝水");
                    drinkGoal=1;
                }
            }
        });
        cbRead=(CheckBox) findViewById(R.id.cb_read);
        cbRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbRead.setText("已选择");
                    Log.d(TAG,"选择读书");
                    readGoal=0;
                }else{
                    cbRead.setText("选择");
                    Log.d(TAG,"不选择读书");
                    readGoal=1;
                }
            }
        });
        cbHobby=(CheckBox) findViewById(R.id.cb_hobby);
        cbHobby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbHobby.setText("已选择");
                    Log.d(TAG,"选择兴趣活动");
                    hobbyGoal=0;
                }else{
                    cbHobby.setText("选择");
                    Log.d(TAG,"不选择兴趣活动");
                    hobbyGoal=1;
                }
            }
        });
        cbSmile=(CheckBox) findViewById(R.id.cb_smile);
        cbSmile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbSmile.setText("已选择");
                    Log.d(TAG,"选择每日微笑");
                    smileGoal=0;
                }else{
                    cbSmile.setText("选择");
                    Log.d(TAG,"不选择每日微笑");
                    smileGoal=1;
                }
            }
        });
        cbDiary=(CheckBox) findViewById(R.id.cb_diary);
        cbDiary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbDiary.setText("已选择");
                    Log.d(TAG,"选择记日记");
                    diaryGoal=0;
                }else{
                    cbDiary.setText("选择");
                    Log.d(TAG,"不选择记日记");
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
                        postStepGoal=stepGoal;
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
                        everyWeekSport=sportTime;
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
                editor.commit();
                new Thread(postRun).start();
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
                            Log.d(TAG,"传打卡设置成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"传打卡设置失败");
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
}
