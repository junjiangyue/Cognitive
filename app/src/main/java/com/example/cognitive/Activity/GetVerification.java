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
import com.example.cognitive.Utils.CheckSumBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GetVerification extends AppCompatActivity {
    private TextView confirm;
    private EditText verify_code;
    private String verificationCode;
    private SharedPreferences sp; // sharedPerferences实现记住密码和自动登录
    private HashMap<String, String> stringHashMap;
    private int userID;
    private String newPhone;
    String TAG = GetVerification.class.getCanonicalName();

    //发送验证码的请求路径URL
    private static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
            APP_KEY="e7390f32adba566071d99ef9248a2b97";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="ac6d644a8722";
    //随机数
    private static final String NONCE="123456";
    //短信模板ID
    private static final String TEMPLATEID="19497091";
    //手机号
    private String MOBILE="13888888888";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_verification);
        init();
        stringHashMap = new HashMap<>();
        //new Thread(postVeri).start();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 先判断验证码是否正确
                //if(!verify_code.getText().toString().equals("")&&verificationCode!=null&& verify_code.getText().toString().equals(verificationCode)){
                    // 验证码正确，将修改请求发到后端
                    stringHashMap.put("newPhone", newPhone);
                    stringHashMap.put("userID", String.valueOf(userID));
                    new Thread(postRun).start();
                //}
                //else {
                    //Toast.makeText(GetVerification.this,"验证码错误", Toast.LENGTH_LONG).show();
                //}

            }
        });
    }
    void init(){
        // 获取传递的消息
        Intent intent = this.getIntent();
        //verificationCode = intent.getStringExtra("verification");
        newPhone = intent.getStringExtra("newPhone");
        // 绑定组件
        confirm = findViewById(R.id.confirm);
        verify_code = findViewById(R.id.verification);
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userID = sp.getInt("USER_ID", 0);
    }
    Runnable postVeri = new Runnable() {

        @Override
        public void run() {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SERVER_URL);
            String curTime = String.valueOf((new Date()).getTime() / 1000L);
            /*
             * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
             */
            String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

            // 设置请求的header
            httpPost.addHeader("AppKey", APP_KEY);
            httpPost.addHeader("Nonce", NONCE);
            httpPost.addHeader("CurTime", curTime);
            httpPost.addHeader("CheckSum", checkSum);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // 设置请求的的参数，requestBody参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            /*
             * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
             * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
             * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
             */
            nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
            MOBILE = newPhone;
            nvps.add(new BasicNameValuePair("mobile", MOBILE));
            nvps.add(new BasicNameValuePair("codeLen", CODELEN));

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // 执行请求
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
             * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
             * 2.具体的code有问题的可以参考官网的Code状态表
             */
            try {
                //System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
                String res = EntityUtils.toString(response.getEntity(), "utf-8");
                JSONObject jsonObject = new JSONObject(res);
                System.out.println(jsonObject);
                verificationCode = jsonObject.optString("obj");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    };
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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/ChangePhoneServlet";
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
                            Toast.makeText(GetVerification.this,"该手机号已注册过账号", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        case 200: // 修改成功
                            Looper.prepare();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("USER_PHONE",  newPhone);
                            editor.commit();
                            Toast.makeText(GetVerification.this,"修改成功", Toast.LENGTH_LONG).show();
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