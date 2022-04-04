package com.example.cognitive.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.cognitive.Adapter.MyFrailPagerAdapter;
import com.example.cognitive.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class FrailTest extends AppCompatActivity {
    String TAG = WelcomeActivity.class.getCanonicalName();
    private ViewPager vpager_frail;
    private ArrayList<View> aList;
    //ViewPager适配器
    private MyFrailPagerAdapter mAdapter;
    public static FrailTest Test;
    public int score=0;
    public int strength_point=0;
    public int health_point=0;

    private HashMap<String,String> stringHashMap;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_test);
        vpager_frail = (ViewPager) findViewById(R.id.frail_viewpager);
        Test=this;

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();

        aList.add(li.inflate(R.layout.frail_1,null,false));
        aList.add(li.inflate(R.layout.frail_2,null,false));
        aList.add(li.inflate(R.layout.frail_3,null,false));
        aList.add(li.inflate(R.layout.frail_4,null,false));
        aList.add(li.inflate(R.layout.frail_5,null,false));

        mAdapter = new MyFrailPagerAdapter(aList);
        vpager_frail.setAdapter(mAdapter);
    }

    public void getFrailResult(View view)
    {
        int state = 0;
        for(int i=0;i<5;i++)
        {
            state+=mAdapter.state[i];
            Log.e("shuruqingkuang",String.valueOf(mAdapter.state[i]));
        }
        Log.e("state",String.valueOf(state));
        if(state==5 || (state==4 && mAdapter.state[3]==0)) {
            mAdapter.result[4] = ((float) (mAdapter.weight_pre - mAdapter.weight_cur) / (float) mAdapter.weight_cur > 0.05) ? 1 : 0;
            for (int i = 0; i < 5; i++) {
                if (i != 3) {
                    score += mAdapter.result[i];
                } else {
                    score += (mAdapter.result[i] > 5) ? 1 : 0;
                }
            }
            strength_point = mAdapter.result[0] + mAdapter.result[1] + mAdapter.result[2];
            health_point = ((mAdapter.result[3] > 5) ? 1 : 0) + mAdapter.result[4];

            sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
            int userID = sp.getInt("USER_ID", 0);

            stringHashMap = new HashMap<>();
            stringHashMap.put("user_id", Integer.toString(userID));
            stringHashMap.put("score", String.valueOf(score));
            stringHashMap.put("strength_score", String.valueOf(strength_point));
            stringHashMap.put("health_score", String.valueOf(health_point));

            //上传后端
            Log.i("postRun", "uploading...");
            new Thread(postRun).start();

            //Log.i("weight", "afterTextChanged: "+mAdapter.weight_cur);
            Intent intent = new Intent(FrailTest.this, FrailDefeatActivity.class);
            intent.putExtra("score", String.valueOf(score));
            intent.putExtra("strength_point", String.valueOf(strength_point));
            intent.putExtra("health_point", String.valueOf(health_point));
            //finish();
            startActivity(intent);
        }else{
            Toast.makeText(FrailTest.this,"还有题目未完成哦", Toast.LENGTH_LONG).show();
        }
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
            String baseUrl="http://101.132.97.43:8080/ServiceTest/servlet/FrailServlet";

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
                            Toast.makeText(FrailTest.this,"数据上传成功", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        default:
                            // 插入失败
                            Looper.prepare();
                            Toast.makeText(FrailTest.this,"数据上传失败", Toast.LENGTH_LONG).show();
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
                Toast.makeText(FrailTest.this,"Post方式请求失败，没接上", Toast.LENGTH_LONG).show();
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