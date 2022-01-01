package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class SetPassword extends AppCompatActivity {
    private EditText newPassword;
    private EditText passwordAgain;
    private TextView confirm;
    private SharedPreferences sp;
    private HashMap<String, String> stringHashMap;
    private int userID;
    private String newPsw;
    String TAG = SetPassword.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        init();
        stringHashMap = new HashMap<>();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 两个密码框都不能为空
                if(!newPassword.getText().toString().equals("")&&!passwordAgain.getText().toString().equals("") ) {
                    //判断两次输入是否相同
                    if(newPassword.getText().toString().equals(passwordAgain.getText().toString())) {
                        //向后端发送修改密码的请求
                        newPsw = newPassword.getText().toString();
                        stringHashMap.put("newPassword", newPsw);
                        stringHashMap.put("userID", String.valueOf(userID));
                        new Thread(postRun).start();
                        // 跳转到账户安全
                        Intent intent = new Intent(SetPassword.this,AccountSecurity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SetPassword.this,"两次密码不一致，请填写新手机号", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(SetPassword.this,"请填写密码", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void init(){
        newPassword = findViewById(R.id.newPassword);
        passwordAgain  = findViewById(R.id.passwordAgain);
        confirm = findViewById(R.id.confirm);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getInt("USER_ID",0);
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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/ChangePasswordServlet";
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
                        case 500 : // 失败
                            Looper.prepare();
                            Toast.makeText(SetPassword.this,"该手机号已注册过账号", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 200: // 修改成功
                            Looper.prepare();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("PASSWORD", newPsw);
                            editor.commit();
                            Toast.makeText(SetPassword.this,"修改成功", Toast.LENGTH_LONG).show();
                            Intent intent2 = new Intent();
                            intent2.setClassName(this,"com.example.cognitive.Activity.AccountSecurity");
                            this.startActivity(intent2);
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
                Log.e(TAG, "Post方式请求失败");
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