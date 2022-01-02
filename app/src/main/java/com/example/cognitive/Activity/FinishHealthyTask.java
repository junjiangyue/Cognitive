package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cognitive.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class FinishHealthyTask extends AppCompatActivity implements View.OnClickListener {
    private String TAG="FinishHealthyTask";
    private SharedPreferences sp;
    private SharedPreferences sp1;
    private HashMap<String, String> stringHashMap;
    private HashMap<String, String> stringHashMap1;
    private CardView cvGetup;
    private TextView txtGetupReal;
    private TextView txtGetupGoal;
    private CardView cvSleep;
    private TextView txtSleepReal;
    private TextView txtSleepGoal;
    private CardView cvStep;
    private TextView txtStepReal;
    private TextView txtStepGoal;
    private CardView cvWater;
    private TextView txtWaterReal;
    private TextView txtWaterGoal;
    private CardView cvRead;
    private TextView txtReadReal;
    private TextView txtReadGoal;
    private CardView cvHobby;
    private TextView txtHobbyReal;
    private TextView txtHobbyGoal;
    private CardView cvSmile;
    private TextView txtSmileReal;
    private TextView txtSmileGoal;
    private CardView cvDiary;
    private TextView txtDairyReal;
    private TextView txtDairyGoal;
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
        stringHashMap = new HashMap<>();
        stringHashMap1 = new HashMap<>();
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
        cvGetup=(CardView) findViewById(R.id.cv_getup);
        txtGetupReal= (TextView) findViewById(R.id.txt_GetupReal);
        txtGetupReal.setText("6:45");
        txtGetupGoal= (TextView) findViewById(R.id.txt_GetupGoal);
        txtGetupGoal.setText(" / "+getupTime);
        cvSleep=(CardView) findViewById(R.id.cv_sleep);
        txtSleepReal= (TextView) findViewById(R.id.txt_SleepReal);
        txtSleepReal.setText("22:45");
        txtSleepGoal= (TextView) findViewById(R.id.txt_SleepGoal);
        txtSleepGoal.setText(" / "+sleepTime);
        cvStep=(CardView) findViewById(R.id.cv_step);
        txtStepReal= (TextView) findViewById(R.id.txt_StepReal);
        txtStepReal.setText("4567");
        txtStepGoal= (TextView) findViewById(R.id.txt_StepGoal);
        txtStepGoal.setText(" / "+postStepGoal+"步");
        cvWater=(CardView) findViewById(R.id.cv_water);
        txtWaterReal= (TextView) findViewById(R.id.txt_WaterReal);
        txtWaterReal.setText("1.5L");
        txtWaterGoal= (TextView) findViewById(R.id.txt_WaterGoal);
        txtWaterGoal.setText(" / 1.5L");
        if(drinkGoal==1) {
            cvWater.setVisibility(View.GONE);
        }
        cvRead=(CardView) findViewById(R.id.cv_read);
        txtReadReal= (TextView) findViewById(R.id.txt_ReadReal);
        txtReadReal.setText("20");
        txtReadGoal= (TextView) findViewById(R.id.txt_ReadGoal);
        txtReadGoal.setText("分钟 / 30分钟");
        if(readGoal==1) {
            cvRead.setVisibility(View.GONE);
        }
        cvHobby=(CardView) findViewById(R.id.cv_hobby);
        txtHobbyReal= (TextView) findViewById(R.id.txt_HobbyReal);
        txtHobbyReal.setText("70");
        txtHobbyGoal= (TextView) findViewById(R.id.txt_HobbyGoal);
        txtHobbyGoal.setText("分钟 / 45分钟");
        if(hobbyGoal==1) {
            cvHobby.setVisibility(View.GONE);
        }
        cvSmile=(CardView) findViewById(R.id.cv_smile);
        txtSmileReal= (TextView) findViewById(R.id.txt_SmileReal);
        txtSmileReal.setText("0");
        txtSmileGoal= (TextView) findViewById(R.id.txt_SmileGoal);
        txtSmileGoal.setText(" / 1次");
        if(smileGoal==1) {
            cvSmile.setVisibility(View.GONE);
        }
        cvDiary=(CardView) findViewById(R.id.cv_diary);
        txtDairyReal= (TextView) findViewById(R.id.txt_DairyReal);
        txtDairyReal.setText("0");
        txtDairyGoal= (TextView) findViewById(R.id.txt_DairyGoal);
        txtDairyGoal.setText(" / 1篇");
        if(diaryGoal==1) {
            cvDiary.setVisibility(View.GONE);
        }
        cvGetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogGetup.class);
                startActivity(intent);
            }
        });
        cvSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogSleep.class);
                startActivity(intent);
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
                Intent intent = new Intent(FinishHealthyTask.this, DialogWater.class);
                startActivity(intent);
            }
        });
        cvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogRead.class);
                startActivity(intent);
            }
        });
        cvHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogHobby.class);
                startActivity(intent);
            }
        });
        cvSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogSmile.class);
                startActivity(intent);
            }
        });
        cvDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishHealthyTask.this, DialogDiary.class);
                startActivity(intent);
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

        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap.put("userID", String.valueOf(userID));
        new Thread(postRun).start();//获取打卡设置
        uploadTaskHistory();
    }

    private void uploadTaskHistory(){
        Log.d(TAG,"传打卡记录");
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap1.put("userID", String.valueOf(userID));
        stringHashMap1.put("getupTime", "7:10:00");
        stringHashMap1.put("sleepTime", "22:10:00");
        stringHashMap1.put("stepRealNum", String.valueOf(6789));
        stringHashMap1.put("readReal", String.valueOf(0));
        stringHashMap1.put("drinkReal", String.valueOf(0));
        stringHashMap1.put("hobbyReal", String.valueOf(1));
        stringHashMap1.put("smileReal", String.valueOf(0));
        stringHashMap1.put("diaryReal", String.valueOf(2));
        stringHashMap1.put("taskDate", "2021-12-27");
        new Thread(postRunHistory).start();//传每日打卡记录
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



}
