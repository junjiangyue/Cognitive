package com.example.cognitive.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import java.util.Calendar;
import java.util.HashMap;

import mehdi.sakout.fancybuttons.FancyButton;

public class TaskHistory extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences sp1;//打卡设置
    private HashMap<String, String> stringHashMap;
    private String TAG="TaskHistory";
    public Handler mhandler;
    private FancyButton btnChooseHistoryDate;
    private TextView txtChooseDate;
    private CardView cvGetup;
    private TextView txtGetupReal;
    private TextView txtGetupGoal;
    private CardView cvSleep;
    private TextView txtSleepReal;
    private TextView txtSleepGoal;
    private CardView cvStep;
    private TextView txtStepReal;
    private TextView txtStepGoal;
    private CardView cvDrink;
    private TextView txtDrinkReal;
    private TextView txtDrinkGoal;
    private CardView cvRead;
    private TextView txtReadReal;
    private TextView txtReadGoal;
    private CardView cvHobby;
    private TextView txtHobbyReal;
    private TextView txtHobbyGoal;
    private CardView cvSmile;
    private TextView txtSmileReal;
    private TextView txtSmileGoal;
    private CardView cvDairy;
    private TextView txtDairyReal;
    private TextView txtDairyGoal;
    private int userID;
    private int stepRealNum;
    private String getupTime;
    private String sleepTime;
    private int drinkReal;
    private int diaryReal;
    private int smileReal;
    private int readReal;
    private int hobbyReal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);
        stringHashMap = new HashMap<>();
        mhandler = new mHandler();
        TextView txtNoTaskTip;
        txtNoTaskTip=findViewById(R.id.txt_noTaskTip);
        txtNoTaskTip.setVisibility(View.GONE);
        cvGetup=findViewById(R.id.cv_getup);
        cvSleep=findViewById(R.id.cv_sleep);
        cvStep=findViewById(R.id.cv_step);
        cvDrink=findViewById(R.id.cv_water);
        cvRead=findViewById(R.id.cv_read);
        cvHobby=findViewById(R.id.cv_hobby);
        cvSmile=findViewById(R.id.cv_smile);
        cvDairy=findViewById(R.id.cv_diary);
        sp1= this.getSharedPreferences("userHealthyTask", Context.MODE_PRIVATE);
        String getupGoal= sp1.getString("getupTime","7:00");
        if(getupGoal.length()>5) {
            getupGoal=getupGoal.substring(0,getupGoal.length()-3);
        }
        txtGetupGoal=findViewById(R.id.txt_GetupGoal);
        txtGetupGoal.setText(" / "+getupGoal);
        String sleepGoal= sp1.getString("sleepTime","23:00");
        if(sleepGoal.length()>5) {
            sleepGoal=sleepGoal.substring(0,sleepGoal.length()-3);
        }
        txtSleepGoal=findViewById(R.id.txt_SleepGoal);
        txtSleepGoal.setText(" / "+sleepGoal);
        int stepGoal = sp1.getInt("stepGoal",7000);
        txtStepGoal=findViewById(R.id.txt_StepGoal);
        txtStepGoal.setText(" / "+stepGoal);
        int drinkGoal = sp1.getInt("drinkGoal",0);
        txtDrinkGoal=findViewById(R.id.txt_WaterGoal);
        if(drinkGoal==0) {
            txtDrinkGoal.setText(" / 1.5L");
        } else {
            cvDrink.setVisibility(View.GONE);
        }
        int readGoal = sp1.getInt("readGoal",0);
        txtReadGoal=findViewById(R.id.txt_ReadGoal);
        if (readGoal==0) {
            txtReadGoal.setText("分钟 / 30分钟");
        } else {
            cvDrink.setVisibility(View.GONE);
        }
        int hobbyGoal = sp1.getInt("hobbyGoal",0);
        txtHobbyGoal=findViewById(R.id.txt_HobbyGoal);
        if (hobbyGoal==0) {
            txtHobbyGoal.setText("分钟 / 45分钟");
        } else {
            cvHobby.setVisibility(View.GONE);
        }
        int smileGoal = sp1.getInt("smileGoal",0);
        txtSmileGoal=findViewById(R.id.txt_SmileGoal);
        if(smileGoal==0){
            txtSmileGoal.setText(" / 1次");
        } else {
            cvSmile.setVisibility(View.GONE);
        }
        int dairyGoal = sp1.getInt("diaryGoal",0);
        txtDairyGoal=findViewById(R.id.txt_DairyGoal);
        if(dairyGoal==0){
            txtDairyGoal.setText(" / 1篇");
        } else {
            cvDairy.setVisibility(View.GONE);
        }
        txtGetupReal=findViewById(R.id.txt_GetupReal);
        txtSleepReal=findViewById(R.id.txt_SleepReal);
        txtStepReal=findViewById(R.id.txt_StepReal);
        txtDrinkReal=findViewById(R.id.txt_WaterReal);
        txtReadReal=findViewById(R.id.txt_ReadReal);
        txtHobbyReal=findViewById(R.id.txt_HobbyReal);
        txtSmileReal=findViewById(R.id.txt_SmileReal);
        txtDairyReal=findViewById(R.id.txt_DairyReal);
        txtChooseDate=findViewById(R.id.txt_chooseHistoryDate);
        Calendar calendar = Calendar.getInstance();
        btnChooseHistoryDate=findViewById(R.id.btn_chooseHistoryDate);
        btnChooseHistoryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(TaskHistory.this,0, new DatePickerDialog.OnDateSetListener() {
                    //实现响应用户单击set按钮的事件方法
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        txtChooseDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                        sp = TaskHistory.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        userID=sp.getInt("USER_ID",0);
                        Log.d(TAG,"userID:"+userID);
                        stringHashMap.put("userID", String.valueOf(userID));
                        String strMonth;
                        String strDay;
                        if((monthOfYear + 1)<10) {
                            strMonth="0"+String.valueOf((monthOfYear + 1));
                        } else {
                            strMonth=String.valueOf((monthOfYear + 1));
                        }
                        if(dayOfMonth<10){
                            strDay="0"+String.valueOf(dayOfMonth);
                        } else {
                            strDay=String.valueOf(dayOfMonth);
                        }
                        String taskDate=String.valueOf(year)+"-"+strMonth+"-"+strDay;
                        Log.d(TAG,"taskDate:"+taskDate);
                        stringHashMap.put("taskDate", taskDate);
                        new Thread(postRun).start();
                    }


                }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();

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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetHistoryCheckServlet";
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
                            bundle.putInt("dataList",0); //往Bundle中存放数据
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                        } else {
                            for (int i = 0; i < 1; i++) {
                                JSONObject object=dataList.getJSONObject(i);
                                stepRealNum=object.getInt("stepRealNum");
                                getupTime=object.optString("getupTime");
                                sleepTime=object.optString("sleepTime");
                                drinkReal=object.getInt("drinkReal");
                                diaryReal=object.getInt("diaryReal");
                                smileReal=object.getInt("smileReal");
                                readReal=object.getInt("readReal");
                                hobbyReal=object.getInt("hobbyReal");
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putInt("dataList",1); //往Bundle中存放数据
                            Log.d(TAG,"stepRealNum:"+stepRealNum);
                            bundle.putInt("stepRealNum",stepRealNum); //往Bundle中存放数据
                            Log.d(TAG,"getupTime:"+getupTime);
                            bundle.putString("getupTime",getupTime); //往Bundle中put数据
                            Log.d(TAG,"sleepTime:"+sleepTime);
                            bundle.putString("sleepTime",sleepTime); //往Bundle中put数据
                            Log.d(TAG,"drinkReal:"+drinkReal);
                            bundle.putInt("drinkReal",drinkReal); //往Bundle中存放数据
                            Log.d(TAG,"diaryReal:"+diaryReal);
                            bundle.putInt("diaryReal",diaryReal); //往Bundle中存放数据
                            Log.d(TAG,"smileReal:"+smileReal);
                            bundle.putInt("smileReal",smileReal); //往Bundle中存放数据
                            Log.d(TAG,"readReal:"+readReal);
                            bundle.putInt("readReal",readReal); //往Bundle中存放数据
                            Log.d(TAG,"hobbyReal:"+hobbyReal);
                            bundle.putInt("hobbyReal",hobbyReal); //往Bundle中存放数据
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                        }
                        Log.d(TAG,"返回结果："+code);

                    }
                    switch (code){
                        case 0 : // 上传失败
                            Looper.prepare();
                            Log.d(TAG,"请求打卡历史成功");
                            //Toast.makeText(this.getContext(),"信息修改失败！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 1: // 上传成功
                            Looper.prepare();
                            Log.d(TAG,"请求打卡历史失败");
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
            TextView txtNoTaskTip;
            txtNoTaskTip=findViewById(R.id.txt_noTaskTip);
            int dataList = msg.getData().getInt("dataList");
            if(dataList==1) {
                txtNoTaskTip.setVisibility(View.GONE);
                cvGetup.setVisibility(View.VISIBLE);
                cvSleep.setVisibility(View.VISIBLE);
                cvStep.setVisibility(View.VISIBLE);
                cvDairy.setVisibility(View.VISIBLE);
                cvDrink.setVisibility(View.VISIBLE);
                cvSmile.setVisibility(View.VISIBLE);
                cvHobby.setVisibility(View.VISIBLE);
                cvRead.setVisibility(View.VISIBLE);
                int stepNum = msg.getData().getInt("stepRealNum");
                txtStepReal.setText(String.valueOf(stepNum));
                String getupTime = msg.getData().getString("getupTime");
                txtGetupReal.setText(getupTime);
                String sleepTime = msg.getData().getString("sleepTime");
                txtSleepReal.setText(sleepTime);
                int drinkReal= msg.getData().getInt("drinkReal");
                if(drinkReal==0){
                    txtDrinkReal.setText("1.5L");
                } else {
                    txtDrinkReal.setText("0L");
                }
                int diaryReal= msg.getData().getInt("diaryReal");
                if(diaryReal==0) {
                    txtDairyReal.setText("1");
                } else {
                    txtDairyReal.setText("0");
                }
                int smileReal= msg.getData().getInt("smileReal");
                if(smileReal==0){
                    txtSmileReal.setText("1");
                } else {
                    txtSmileReal.setText("0");
                }
                int readReal= msg.getData().getInt("readReal");
                if (readReal==0) {
                    txtReadReal.setText("30");
                } else {
                    txtReadReal.setText("0");
                }
                int hobbyReal= msg.getData().getInt("hobbyReal");
                if (hobbyReal==0) {
                    txtHobbyReal.setText("45");
                } else {
                    txtHobbyReal.setText("0");
                }
            } else {
                txtNoTaskTip.setVisibility(View.VISIBLE);
                cvGetup.setVisibility(View.GONE);
                cvSleep.setVisibility(View.GONE);
                cvStep.setVisibility(View.GONE);
                cvDairy.setVisibility(View.GONE);
                cvDrink.setVisibility(View.GONE);
                cvSmile.setVisibility(View.GONE);
                cvHobby.setVisibility(View.GONE);
                cvRead.setVisibility(View.GONE);
            }


        }
    }
}
