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

public class ChangePassword extends AppCompatActivity {
    private EditText preCode;
    private EditText newCode;
    private EditText confirmCode;
    private TextView confirm;
    private SharedPreferences sp; // sharedPerferences实现记住密码和自动登录
    private HashMap<String, String> stringHashMap;
    private int userID;
    private String userPassword;
    private String newPassword;
    private TextView forgetPass;
    String TAG = GetVerification.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        stringHashMap = new HashMap<>();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先判断旧密码是否正确
                if (preCode.getText().toString().equals(userPassword))
                {
                    // 再判断两次密码是否一致
                    if(newCode.getText().toString().equals(confirmCode.getText().toString())){
                        // 向后端发送请求
                        newPassword = newCode.getText().toString();
                        stringHashMap.put("newPassword", newCode.getText().toString());
                        stringHashMap.put("userID", String.valueOf(userID));
                        new Thread(postRun).start();
                    }
                }

            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassword.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    void init(){
        preCode = findViewById(R.id.prePassword);
        newCode = findViewById(R.id.newPassword);
        confirmCode = findViewById(R.id.passwordAgain);
        confirm = findViewById(R.id.confirm);
        forgetPass = findViewById(R.id.forgetPass);

        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getInt("USER_ID", 0);
        userPassword = sp.getString("PASSWORD", "");
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
                            Toast.makeText(ChangePassword.this,"修改失败", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 200: // 修改成功
                            Looper.prepare();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("PASSWORD",  newPassword);
                            editor.commit();
                            Toast.makeText(ChangePassword.this,"密码修改成功", Toast.LENGTH_LONG).show();
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