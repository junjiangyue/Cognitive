package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cognitive.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lucasurbas.listitemview.ListItemView;

public class TestHistory extends AppCompatActivity {
    String TAG = MainActivity.class.getCanonicalName();

    private LinearLayout historyRecord1;
    private LinearLayout historyRecord2;
    private LinearLayout historyRecord3;
    private LinearLayout historyRecord4;
    private LinearLayout historyRecord5;
    private LinearLayout historyRecord6;

    private SharedPreferences sp;
    private HashMap<String,String> stringHashMap;
    private List<Map<String,String>> historyTest=new ArrayList<Map<String,String>>();
    //private Map<String,Object> map=new HashMap<String,Object>();
    public Handler mhandler;
    private Handler mhandlerPost = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        historyRecord1=findViewById(R.id.history_record1);
        historyRecord2=findViewById(R.id.history_record2);
        historyRecord3=findViewById(R.id.history_record3);
        historyRecord4=findViewById(R.id.history_record4);
        historyRecord5=findViewById(R.id.history_record5);
        historyRecord6=findViewById(R.id.history_record6);



        sp=getSharedPreferences("userInfo",MODE_PRIVATE);
        int userID = sp.getInt("USER_ID", 0);

        stringHashMap=new HashMap<>();
        stringHashMap.put("userid",Integer.toString(userID));

        mhandler=new mHandler();
        new Thread(postRun).start();

    }

        Runnable postRun = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(stringHashMap);
        }
    };

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
                            Looper.prepare();
                            Toast.makeText(TestHistory.this,"Post方式请求成功", Toast.LENGTH_LONG).show();
                            String data = jsonObject.optString("data");
                            Log.i("jsontest",data);
                            processStringToList(data);

                            //JSONObject jsonObject2 = new JSONObject(data);
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);//用activity中的handler发送消息
                            Looper.loop();
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

    public void processStringToList(String data)
    {
        //StringBuilder temp= new StringBuilder();
        data=data.replace("[{","");
        data=data.replace("}]","");
        String str="\\},\\{";
        String [] temp_arr=data.split(str);
        for(String i:temp_arr)
        {
            Map<String,String> map=new HashMap<>();
            Pattern pattern=Pattern.compile("\\d+(:|-)*(\\d)*(:|-)*(\\d)*|FRAIL|AD-8");
            Matcher matcher=pattern.matcher(i);
            int cnt=0;
            while(matcher.find())
            {
                Log.w("jsontest", matcher.group());
                switch (cnt)
                {
                    case 0:
                        map.put("memoryScore",matcher.group());
                        break;
                    case 1:
                        map.put("cognitionScore",matcher.group());
                        break;
                    case 2:
                        map.put("strengthScore",matcher.group());
                        break;
                    case 3:
                        map.put("testScore",matcher.group());
                        break;
                    case 4:
                        map.put("testTime",matcher.group());
                        break;
                    case 5:
                        map.put("healthScore",matcher.group());
                        break;
                    case 6:
                        map.put("testDate",matcher.group());
                        break;
                    case 7:
                        map.put("judgementScore",matcher.group());
                        break;
                    case 8:
                        map.put("testName",matcher.group());
                        break;
                    default:
                        break;
                }
                cnt++;
            }
            historyTest.add(map);
            //Log.i("jsontest", i + "\n");
        }


        //}
    }

//
    class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            TextView historyName1 = findViewById(R.id.history_name1);
            TextView historyName2 = findViewById(R.id.history_name2);
            TextView historyName3 = findViewById(R.id.history_name3);
            TextView historyName4 = findViewById(R.id.history_name4);
            TextView historyName5 = findViewById(R.id.history_name5);
            TextView historyName6 = findViewById(R.id.history_name6);

            TextView historyScore1 = findViewById(R.id.history_score1);
            TextView historyScore2 = findViewById(R.id.history_score2);
            TextView historyScore3 = findViewById(R.id.history_score3);
            TextView historyScore4 = findViewById(R.id.history_score4);
            TextView historyScore5 = findViewById(R.id.history_score5);
            TextView historyScore6 = findViewById(R.id.history_score6);

            TextView historyDatetime1 = findViewById(R.id.history_datetime1);
            TextView historyDatetime2 = findViewById(R.id.history_datetime2);
            TextView historyDatetime3 = findViewById(R.id.history_datetime3);
            TextView historyDatetime4 = findViewById(R.id.history_datetime4);
            TextView historyDatetime5 = findViewById(R.id.history_datetime5);
            TextView historyDatetime6 = findViewById(R.id.history_datetime6);

            TextView [] nameTextViews={historyName1, historyName2, historyName3, historyName4, historyName5, historyName6};
            TextView [] scoreTextViews={historyScore1,historyScore2,historyScore3,historyScore4,historyScore5,historyScore6};
            TextView [] datetimeTextViews={historyDatetime1,historyDatetime2,historyDatetime3,historyDatetime4,historyDatetime5,historyDatetime6};

            for(int i = 0; i<(Math.min(historyTest.size(), 6)); i++)
            {
                String s_name =historyTest.get(i).get("testName");
                String s_score=historyTest.get(i).get("testScore");
                String s_datetime=historyTest.get(i).get("testDate")+" "+historyTest.get(i).get("testTime");
                nameTextViews[i].setText(s_name);
                scoreTextViews[i].setText("分数："+s_score);
                datetimeTextViews[i].setText("时间："+s_datetime);
            }
        }
    }
}