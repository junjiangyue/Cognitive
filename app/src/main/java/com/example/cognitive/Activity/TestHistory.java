package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cognitive.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import com.lucasurbas.listitemview.ListItemView;

public class TestHistory extends AppCompatActivity {
    String TAG = MainActivity.class.getCanonicalName();
    public TextView historyTestName1;
    public TextView historyTestTime1;
    public TextView historyTestScore1;
    public TextView historyTestStrength1;
    public TextView historyTestHealth1;
    public TextView historyTestMemory1;
    public TextView historyTestJudgement1;
    public TextView historyTestCognition1;

    private ListItemView historyRecord1;
    private ListItemView historyRecord2;

    private String userPhone;

    private String testName;
    private String testTime;
    private String testScore;
    private String testStrength;
    private String testHealth;
    private String testMemory;
    private String testJudgement;
    private String testCognition;

    private SharedPreferences sp;
    private HashMap<String,String> stringHashMap;
    public Handler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        historyRecord1=findViewById(R.id.history_record1);
        historyRecord1.setSubtitle("测试时间：2021-12-19\n测试项目：AD-8\n测试结果：4分\n衰弱评级：衰弱");

        historyRecord2=findViewById(R.id.history_record2);
        historyRecord2.setSubtitle("测试时间：2021-12-19\n测试项目：FRAIL\n测试结果：0分\n衰弱评级：健康");
//        historyTestName1=findViewById(R.id.history_test_name1);
//        historyTestTime1=findViewById(R.id.history_test_time1);
//        historyTestScore1=findViewById(R.id.history_test_score1);
//        historyTestStrength1=findViewById(R.id.history_test_strength1);
//        historyTestHealth1=findViewById(R.id.history_test_health1);
//        historyTestMemory1=findViewById(R.id.history_test_memory1);
//        historyTestJudgement1=findViewById(R.id.history_test_judgement1);
//        historyTestCognition1=findViewById(R.id.history_test_cognition1);

//        sp=getSharedPreferences("userInfo",MODE_PRIVATE);
//        userPhone=sp.getString("USER_PHONE",null);
//        Log.i("sptest",userPhone);
//
//        stringHashMap=new HashMap<>();
//        stringHashMap.put("userphone",userPhone);
//
//        mhandler=new mHandler();

        //new Thread(postRun).start();

    }

    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(stringHashMap);
        }
    };
    /**
     * post提交数据
     *
     * @param paramsMap
     */
    private void requestPost(HashMap<String, String> paramsMap) {
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetHistoryServlet";
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
                    }
                    switch (code){
                        case 200 :
                            //获取用户信息
                            String data = jsonObject.optString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            testName=jsonObject2.optString("testname");
                            testTime=jsonObject2.optString("testtime");
                            testScore=jsonObject2.optString("testscore");
                            testStrength=jsonObject2.optString("teststrength");
                            testHealth=jsonObject2.optString("testhealth");
                            testMemory=jsonObject2.optString("testmemory");
                            testJudgement=jsonObject2.optString("testjudgement");
                            testCognition=jsonObject2.optString("testcognition");
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("testname",testName);
                            bundle.putString("testtime",testTime);
                            bundle.putString("testscore",testScore);
                            bundle.putString("teststrength",testStrength);
                            bundle.putString("testhealth",testHealth);
                            bundle.putString("testmemory",testMemory);
                            bundle.putString("testjudgement",testJudgement);
                            bundle.putString("testcognition",testCognition);
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);//用activity中的handler发送消息
                            break;
                        default:
                            Looper.prepare();
                            Toast.makeText(TestHistory.this,"数据库错误", Toast.LENGTH_LONG).show();
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
                Toast.makeText(TestHistory.this,"Post方式请求失败", Toast.LENGTH_LONG).show();
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

    class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
//            String username = msg.getData().getString("username");//接受msg传递过来的参数
//            String testscore=msg.getData().getString("score");
            String testname=msg.getData().getString("testname");
            String testtime=msg.getData().getString("testtime");
            String testscore=msg.getData().getString("testscore");
            String strength=msg.getData().getString("teststrength");
            String health=msg.getData().getString("testhealth");
            String memory=msg.getData().getString("testmemory");
            String judgement=msg.getData().getString("testjudgement");
            String cognition=msg.getData().getString("testcognition");

//            historyTestName1.setText(testname);
//            historyTestScore1.setText(testscore);
//            historyTestTime1.setText(testtime);
//            historyTestStrength1.setText(strength);
//            historyTestHealth1.setText(health);
//            historyTestMemory1.setText(memory);
//            historyTestJudgement1.setText(judgement);
//            historyTestCognition1.setText(cognition);
        }
    }
}