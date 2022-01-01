package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForgetPassword extends AppCompatActivity {
    private TextView next;
    private EditText user_phone;
    private EditText verify_code;
    private Button get_veri;
    private String verificationCode;

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

    private String userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
        userPhone = user_phone.getText().toString();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 验证码和手机号不能为空
                if(!user_phone.getText().toString().equals("")&&!get_veri.getText().toString().equals("")){
                    //if(get_veri.getText().toString().equals(verificationCode)) {
                        //new Thread(postVeri).start();
                        //Toast.makeText(ForgetPassword.this, "验证码已发送", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgetPassword.this,SetPassword.class);
                        startActivity(intent);
                    //}else {
                        //Toast.makeText(ForgetPassword.this, "验证码错误！", Toast.LENGTH_LONG).show();
                   //// }
                }else{
                    Toast.makeText(ForgetPassword.this,"请填写手机号", Toast.LENGTH_LONG).show();
                }
            }
        });
        get_veri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 手机号不能为空
                if(!user_phone.getText().toString().equals("")){
                    //new Thread(postVeri).start();
                    Toast.makeText(ForgetPassword.this,"验证码已发送", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ForgetPassword.this,"请填写手机号", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void init(){
        next = findViewById(R.id.next);
        user_phone = findViewById(R.id.user_phone);
        verify_code = findViewById(R.id.verification);
        get_veri = findViewById(R.id.get_veri);
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
            MOBILE = userPhone;
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
}