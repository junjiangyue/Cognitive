package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.cognitive.Adapter.MyAD8PagerAdapter;
import com.example.cognitive.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class AD8Test extends AppCompatActivity {
    String TAG = WelcomeActivity.class.getCanonicalName();
    private ViewPager vpager_ad8;
    private ArrayList<View> aList;
    //ViewPager适配器
    private MyAD8PagerAdapter mAdapter;
    private SharedPreferences sp;
    public static AD8Test Test;
    public int score=0;
    public int judgement_point=0;
    public int memory_point=0;
    public int cognition_point=0;
    private HashMap<String, String> stringHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad8_test);
        vpager_ad8=(ViewPager)findViewById(R.id.ad8_viewpager);
        Test=this;

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        aList.add(li.inflate(R.layout.ad8_1,null,false));
        aList.add(li.inflate(R.layout.ad8_2,null,false));
        aList.add(li.inflate(R.layout.ad8_3,null,false));
        aList.add(li.inflate(R.layout.ad8_4,null,false));
        aList.add(li.inflate(R.layout.ad8_5,null,false));
        aList.add(li.inflate(R.layout.ad8_6,null,false));
        aList.add(li.inflate(R.layout.ad8_7,null,false));
        aList.add(li.inflate(R.layout.ad8_8,null,false));


        mAdapter = new MyAD8PagerAdapter(aList);
        vpager_ad8.setAdapter(mAdapter);
    }

    public void getAD8Result(View v)
    {
        for(int i=0;i<8;i++)
        {
            score+=mAdapter.result[i];
        }

        judgement_point=mAdapter.result[0];
        memory_point=mAdapter.result[2]+mAdapter.result[4]+mAdapter.result[6]+mAdapter.result[7];
        cognition_point=mAdapter.result[1]+mAdapter.result[3]+mAdapter.result[5];

        sp=this.getSharedPreferences("userInfo",MODE_PRIVATE);
        String userPhone=sp.getString("USER_PHONE",null);
        Log.i("sptest","userPhone:"+userPhone);
        Log.i("sptest","score:"+String.valueOf(score));

        stringHashMap=new HashMap<>();
        stringHashMap.put("user_phone",userPhone);
        stringHashMap.put("score",String.valueOf(score));
        stringHashMap.put("judgement_score",String.valueOf(judgement_point));
        stringHashMap.put("memory_score",String.valueOf(memory_point));
        stringHashMap.put("cognition_score",String.valueOf(cognition_point));

        Log.i("backend-test",userPhone+" "+String.valueOf(score));

        //上传后端
        new Thread(postRun).start();

        Intent intent=new Intent(AD8Test.this,AD8ResultPage.class);
        intent.putExtra("ad8_score",String.valueOf(score));
        intent.putExtra("ad8_judgement",String.valueOf(judgement_point));
        intent.putExtra("ad8_memory",String.valueOf(memory_point));
        intent.putExtra("ad8_cognition",String.valueOf(cognition_point));
        startActivity(intent);


    }
    Runnable postRun=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(stringHashMap);
        }
    };

    private void requestPost(HashMap<String,String> paramsMap)
    {
        int code=20;
        try {
            String baseUrl="http://101.132.97.43:8080/ServiceTest/servlet/AD8Servlet";

            StringBuilder tempParams=new StringBuilder();
            int pos=0;
            for(String key:paramsMap.keySet()){
                if(pos>0)
                {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s",key, URLEncoder.encode(paramsMap.get(key),"utf-8")));
                pos++;
            }
            String params=tempParams.toString();

            //新建URL对象
            URL url=new URL(baseUrl);
            //打开一个HttpURLConnection连接
            HttpURLConnection urlConn=(HttpURLConnection) url.openConnection();
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

            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post方式请求成功，result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                    }
                    switch (code){
                        case 200 :
                            //插入成功
                            Looper.prepare();
                            Toast.makeText(AD8Test.this,"数据上传成功", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        default:
                            // 插入失败
                            Looper.prepare();
                            Toast.makeText(AD8Test.this,"数据上传失败", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post方式请求失败");
                Toast.makeText(AD8Test.this,"Post方式请求失败，没接上", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            urlConn.disconnect();
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }

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