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

import com.example.cognitive.R;

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

public class WeeklyReport extends AppCompatActivity {
    private String TAG="WeeklyReport";
    private SharedPreferences sp;
    private SharedPreferences spWeekReport;
    private HashMap<String, String> stringHashMap;
    private Button btnHistoryTaskReport;
    private TextView txtTaskDay;
    private TextView txtDailyReal;
    private TextView txtSportReal;
    private TextView txtSleepFinish;
    private TextView txtGetupFinish;
    private TextView txtStepFinish;
    private TextView txtSportFinish;
    private TextView txtPowerFinish;
    private TextView txtSportTime;
    private int userID;
    private int dailyReal;
    private int sportReal;
    private int powerReal;
    private int stepReal;
    private int getupReal;
    private int sleepReal;
    private String beginDate;
    private String endDate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);
        spWeekReport=this.getSharedPreferences("userWeekReport", Context.MODE_PRIVATE);
        stringHashMap = new HashMap<>();
        btnHistoryTaskReport=(Button) findViewById(R.id.btn_history_task_report);
        txtTaskDay=findViewById(R.id.txt_taskDay);
        txtDailyReal=findViewById(R.id.txt_dailyReal);
        txtSportReal=findViewById(R.id.txt_sportReal);
        txtSleepFinish=findViewById(R.id.txt_sleepFinish);
        txtGetupFinish=findViewById(R.id.txt_getupFinish);
        txtStepFinish=findViewById(R.id.txt_stepFinish);
        txtSportFinish=findViewById(R.id.txt_sportFinish);
        txtPowerFinish=findViewById(R.id.txt_powerFinish);
        txtSportTime=findViewById(R.id.txt_sportTime);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap.put("userID", String.valueOf(userID));
        dailyReal=spWeekReport.getInt("dailyReal",0);
        txtDailyReal.setText("共"+dailyReal+"天完成了所有每日打卡");
        stringHashMap.put("dailyReal", String.valueOf(dailyReal));
        sportReal=spWeekReport.getInt("sportReal",0);
        txtSportReal.setText("每周运动打卡完成"+sportReal+"项");
        txtSportFinish.setText("运动"+sportReal+"次");
        stringHashMap.put("sportReal", String.valueOf(sportReal));
        powerReal=spWeekReport.getInt("powerReal",0);
        txtPowerFinish.setText("完成力量训练"+powerReal+"次");
        stringHashMap.put("powerReal", String.valueOf(powerReal));
        stepReal=spWeekReport.getInt("stepReal",0);
        txtStepFinish.setText(stepReal+"天达到目标步数");
        stringHashMap.put("stepReal", String.valueOf(stepReal));
        getupReal=spWeekReport.getInt("getupReal",0);
        txtGetupFinish.setText(getupReal+"天完成早起");
        stringHashMap.put("getupReal", String.valueOf(getupReal));
        sleepReal=spWeekReport.getInt("sleepReal",0);
        txtSleepFinish.setText(sleepReal+"天完成早睡");
        stringHashMap.put("sleepReal", String.valueOf(sleepReal));
        beginDate=spWeekReport.getString("beginDate",null);
        stringHashMap.put("beginDate", beginDate);
        endDate=spWeekReport.getString("endDate",null);
        stringHashMap.put("endDate", endDate);
        int sportTime=powerReal*20+(sportReal-powerReal)*30;
        txtSportTime.setText("总共运动"+sportTime+"分钟");

        //new Thread(postRun).start();
        btnHistoryTaskReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeeklyReport.this, WeeklyReportHistory.class);
                startActivity(intent);
            }
        });
        if(beginDate==null||endDate==null) {
            txtTaskDay.setText("本周还未开始健康打卡");
        } else {
            long days=getDay(beginDate,endDate)+1;
            txtTaskDay.setText("本周健康打卡"+days+"天");
        }
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        if(weekDay==2){
            new Thread(postRun).start();
        }
    }
    public long getDay(String str1,String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = simpleDateFormat.parse(str1);
            Date date2 = simpleDateFormat.parse(str2);
            long Maxtime =(date2.getTime()>date1.getTime())?date2.getTime():date1.getTime();
            long Mintime = (date2.getTime()>date1.getTime())?date1.getTime():date2.getTime();
            long l = Maxtime - Mintime;
            long l1 =l/(1000*60*60*24);
            return l1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            requestPost(stringHashMap);
        }
    };

    private void requestPost(HashMap<String, String> paramsMap){
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/SetWeekCheckServlet";
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
                            Log.d(TAG,"传打卡周报成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"传打卡周报失败");
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
