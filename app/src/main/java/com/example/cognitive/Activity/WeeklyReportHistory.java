package com.example.cognitive.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cognitive.Adapter.HistoryAdapter;
import com.example.cognitive.Adapter.ReportAdapter;
import com.example.cognitive.Bean.History;
import com.example.cognitive.Bean.Report;
import com.example.cognitive.R;

import org.json.JSONArray;
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

public class WeeklyReportHistory extends AppCompatActivity {
    private SharedPreferences sp;
    private HashMap<String, String> stringHashMap;
    private String TAG="WeeklyReportHistory";

    private int userID;

    private ListView listView;
    public Handler mhandler;
    private List<Report> data;
    private ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report_history);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        stringHashMap = new HashMap<>();
        data = new ArrayList<Report>();
        listView =findViewById(R.id.week_report_list);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);

        Intent getIntent=getIntent();
        if(getIntent.getStringExtra("USER_ID")!=null)
        {
            userID = Integer.parseInt(getIntent.getStringExtra("USER_ID"));
        }

        stringHashMap.put("userID", String.valueOf(userID));
        mhandler = new mHandler();
        new Thread(postRun).start();
        adapter = new ReportAdapter(WeeklyReportHistory.this, data);

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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetWeekCheckServlet";
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
                        JSONArray dataList=jsonObject.getJSONArray("data");
                        if(dataList.length()==0) {
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("dataListLength",0); //往Bundle中存放数据
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                        } else {
                            ArrayList<String> beginDateList = new ArrayList<>();
                            ArrayList<String> endDateList = new ArrayList<>();
                            ArrayList<Integer> dailyRealList = new ArrayList<>();
                            ArrayList<Integer> sportRealList = new ArrayList<>();
                            ArrayList<Integer> getupRealList = new ArrayList<>();
                            ArrayList<Integer> sleepRealList = new ArrayList<>();
                            ArrayList<Integer> powerRealList = new ArrayList<>();
                            ArrayList<Integer> stepRealList = new ArrayList<>();
                            for (int i = 0; i < dataList.length(); i++) {
                                JSONObject object=dataList.getJSONObject(i);
                                Report datas = new Report();
                                datas.setBeginDate(object.optString("beginDate"));
                                datas.setEndDate(object.optString("endDate"));
                                datas.setDailyCheck(String.valueOf(object.optInt("dailyReal")));
                                datas.setSportCheck(String.valueOf(object.optInt("sportReal")));
                                data.add(datas);

                                // 给具体的页面传值用的
                                beginDateList.add(object.optString("beginDate"));
                                endDateList.add(object.optString("endDate"));
                                dailyRealList.add(object.optInt("dailyReal"));
                                sportRealList.add(object.optInt("sportReal"));
                                getupRealList.add(object.optInt("getupReal"));
                                sleepRealList.add(object.optInt("sleepReal"));
                                powerRealList.add(object.optInt("powerReal"));
                                stepRealList.add(object.optInt("stepReal"));
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("dataListLength",dataList.length()); //往Bundle中存放数据
                            bundle.putStringArrayList("beginDateList",beginDateList);
                            bundle.putStringArrayList("endDateList",endDateList);
                            bundle.putIntegerArrayList("dailyRealList",dailyRealList);
                            bundle.putIntegerArrayList("sportRealList",sportRealList);
                            bundle.putIntegerArrayList("getupRealList",getupRealList);
                            bundle.putIntegerArrayList("sleepRealList",sleepRealList);
                            bundle.putIntegerArrayList("powerRealList",powerRealList);
                            bundle.putIntegerArrayList("stepRealList",stepRealList);
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                        }
                        Log.d(TAG,"返回结果："+code);
                    }
                    switch (code){
                        case 0 : // 上传失败
                            Looper.prepare();
                            Log.d(TAG,"请求打卡周报成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"请求打卡周报失败");
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
    class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int dataListLength = msg.getData().getInt("dataListLength");
            ArrayList<String> beginDateList;
            beginDateList=msg.getData().getStringArrayList("beginDateList");
            ArrayList<String> endDateList;
            endDateList=msg.getData().getStringArrayList("endDateList");
            ArrayList<Integer> dailyRealList;
            dailyRealList=msg.getData().getIntegerArrayList("dailyRealList");
            ArrayList<Integer> sportRealList;
            sportRealList=msg.getData().getIntegerArrayList("sportRealList");
            ArrayList<Integer> getupRealList;
            getupRealList=msg.getData().getIntegerArrayList("getupRealList");
            ArrayList<Integer> sleepRealList;
            sleepRealList=msg.getData().getIntegerArrayList("sleepRealList");
            ArrayList<Integer> powerRealList;
            powerRealList=msg.getData().getIntegerArrayList("powerRealList");
            ArrayList<Integer> stepRealList;
            stepRealList=msg.getData().getIntegerArrayList("stepRealList");
            String[] weekReportDate=new String[dataListLength];
            for(int i=0;i<dataListLength;i++) {
                weekReportDate[i]=beginDateList.get(i)+"  ~  "+endDateList.get(i);
            }

            if(dataListLength==0) {
                TextView txt_week_tip=findViewById(R.id.txt_week_tip);
                txt_week_tip.setVisibility(View.VISIBLE);
            }
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG,"dailyReal:"+ dailyRealList.get(i));
                    Log.d(TAG,"sportReal:"+ sportRealList.get(i));
                    Log.d(TAG,"getupReal:"+ getupRealList.get(i));
                    Log.d(TAG,"sleepReal:"+ sleepRealList.get(i));
                    Log.d(TAG,"powerReal:"+ powerRealList.get(i));
                    Log.d(TAG,"stepReal:"+ stepRealList.get(i));
                    Intent intent  = new Intent(WeeklyReportHistory.this,OneWeekReport.class);
                    intent.putExtra("beginDate", beginDateList.get(i));//设置参数
                    intent.putExtra("endDate", endDateList.get(i));//设置参数
                    intent.putExtra("dailyReal", dailyRealList.get(i));//设置参数
                    intent.putExtra("sportReal", sportRealList.get(i));//设置参数
                    intent.putExtra("getupReal", getupRealList.get(i));//设置参数
                    intent.putExtra("sleepReal", sleepRealList.get(i));//设置参数
                    intent.putExtra("powerReal", powerRealList.get(i));//设置参数
                    intent.putExtra("stepReal", stepRealList.get(i));//设置参数
                    startActivity(intent);
                }
            });
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
