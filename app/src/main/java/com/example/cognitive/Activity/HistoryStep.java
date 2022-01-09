package com.example.cognitive.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cognitive.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

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

public class HistoryStep extends AppCompatActivity {
    private SharedPreferences sp;
    private HashMap<String, String> stringHashMap;
    private ListView listView;
    private BarChart stepBarChart;//条形图
    List<BarEntry>barList=new ArrayList<>();//实例化一个List用来存储数据
    List<String>barDateList=new ArrayList<>();
    public Handler mhandler;
    private String TAG="HistoryStep";
    private int userID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_everyday_step_num);
        stringHashMap = new HashMap<>();
        mhandler = new mHandler();
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_expandable_list_item_1,data
        );*/
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        listView =findViewById(R.id.step_list);
        //listView.setAdapter(adapter);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID=sp.getInt("USER_ID",0);
        Log.d(TAG,"userID:"+userID);
        stringHashMap.put("userID", String.valueOf(userID));
        new Thread(postRun).start();
        //setStepBarChart();
    }
    public void setStepBarChart() {
        stepBarChart=findViewById(R.id.step_BarChart);
        BarDataSet barDataSet=new BarDataSet(barList,"每日步数");
        BarData barData=new BarData(barDataSet);
        stepBarChart.setData(barData);
        stepBarChart.requestLayout();

        stepBarChart.getDescription().setEnabled(false);//隐藏右下角英文
        stepBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//X轴的位置 默认为上面
        stepBarChart.getAxisRight().setEnabled(false);//隐藏右侧Y轴   默认是左右两侧都有Y轴
        stepBarChart.setScaleXEnabled(false);
        stepBarChart.setScaleYEnabled(false);
        //柱子
//        barDataSet.setColor(Color.BLACK);  //柱子的颜色
        //图例
        //Legend legend=stepBarChart.getLegend();
        //legend.setEnabled(true);    //是否显示图例

        XAxis xAxis=stepBarChart.getXAxis();
        xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.setValueFormatter(new IAxisValueFormatter() {   //X轴自定义坐标
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                if (v==1){
                    String str=barDateList.get(0);
                    str=str.substring(5);
                    return str;
                }
                if (v==2){
                    String str=barDateList.get(1);
                    str=str.substring(5);
                    return str;
                }
                if (v==3){
                    String str=barDateList.get(2);
                    str=str.substring(5);
                    return str;
                }
                if (v==4){
                    String str=barDateList.get(3);
                    str=str.substring(5);
                    return str;
                }
                if (v==5){
                    String str=barDateList.get(4);
                    str=str.substring(5);
                    return str;
                }
                if (v==6){
                    String str=barDateList.get(5);
                    str=str.substring(5);
                    return str;
                }
                if (v==7){
                    String str=barDateList.get(6);
                    str=str.substring(5);
                    return str;
                }
                return "";//注意这里需要改成 ""
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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetStepServlet";
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
                            ArrayList<String> dateList = new ArrayList<>();
                            ArrayList<String> stepList = new ArrayList<>();
                            for (int i = 0; i < dataList.length(); i++) {
                                JSONObject object=dataList.getJSONObject(i);
                                dateList.add(object.optString("stepDate"));
                                stepList.add(object.optString("stepNum"));
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("dataListLength",dataList.length()); //往Bundle中存放数据
                            bundle.putStringArrayList("dateList",dateList);
                            bundle.putStringArrayList("stepList",stepList);
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                        }
                        Log.d(TAG,"返回结果："+code);
                    }
                    switch (code){
                        case 0 : // 上传失败
                            Looper.prepare();
                            Log.d(TAG,"获取步数成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"获取步数失败");
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
                //Toast.makeText(this,"手机号或密码错误，请重新登录", Toast.LENGTH_LONG).show();
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
            ArrayList<String> dateList = new ArrayList<>();
            dateList=msg.getData().getStringArrayList("dateList");
            ArrayList<String> stepList = new ArrayList<>();
            stepList=msg.getData().getStringArrayList("stepList");
            String[] stepDate=new String[dataListLength];
            for(int i=0;i<dataListLength;i++) {
                stepDate[i]=dateList.get(i)+"               "+stepList.get(i)+"步";
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    HistoryStep.this, android.R.layout.simple_expandable_list_item_1,stepDate
            );
            //listView =findViewById(R.id.step_list);
            listView.setAdapter(adapter);
            if(dataListLength==0) {
                TextView txt_step_tip=findViewById(R.id.txt_step_tip);
                txt_step_tip.setVisibility(View.VISIBLE);
            }
            if(dataListLength<7) {
                for(int i=dataListLength-1;i>=0;i--) {
                    int stepNum=Integer.parseInt(stepList.get(i));
                    barList.add(new BarEntry(dataListLength-i,stepNum));
                    barDateList.add(dateList.get(i));
                }
            } else {
                for(int i=6;i>=0;i--) {
                    int stepNum=Integer.parseInt(stepList.get(i));
                    barList.add(new BarEntry(7-i,stepNum));
                    barDateList.add(dateList.get(i));
                }
            }

            setStepBarChart();
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
