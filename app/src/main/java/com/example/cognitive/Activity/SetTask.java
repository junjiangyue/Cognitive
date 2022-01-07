package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class SetTask extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences sp1;
    private SharedPreferences spTestScore;
    private HashMap<String, String> stringHashMap;
    private String TAG="SetTask";
    private TextView txtGetUpTime;
    private TextView txtSleepTime;
    private TextView txtSetStep;
    private TextView txtSportTime;
    private TextView txtPowerTime;
    private CheckBox cbDrinkWater;
    private CheckBox cbRead;
    private CheckBox cbHobby;
    private CheckBox cbSmile;
    private CheckBox cbDiary;
    private FancyButton btnConfirmTask;
    private FancyButton btnCancelTask;
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
        setContentView(R.layout.activity_set_task);
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

        txtGetUpTime= (TextView) findViewById(R.id.txt_getUpTime);
        txtGetUpTime.setText("8:00");
        txtSleepTime= (TextView) findViewById(R.id.txt_sleepTime);
        txtSleepTime.setText("23:00");
        txtSetStep= (TextView) findViewById(R.id.txt_setStep);
        txtSetStep.setText("每日行走"+postStepGoal+"步");
        txtSportTime= (TextView) findViewById(R.id.txt_setSportTime);
        txtSportTime.setText("每周运动"+everyWeekSport+"次");
        txtPowerTime= (TextView) findViewById(R.id.txt_setPowerTime);
        txtPowerTime.setText("其中力量训练"+weeklyPowerSport+"次");
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
        if (memoryScore<=3){
            cbDiary.setChecked(true);
        }
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
        btnConfirmTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sp = SetTask.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                sp1= SetTask.this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
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
                //0不运动，1力量训练，2其他运动
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
                finish();
            }
        });
        btnCancelTask=(FancyButton) findViewById(R.id.btn_cancel_task);
        btnCancelTask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                finish();
            }
        });
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
