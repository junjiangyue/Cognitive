package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChangePhone extends AppCompatActivity {
    private TextView next;
    private TextView phone;
    private SharedPreferences sp; // sharedPerferences实现记住密码和自动登录
    private EditText newphone;
    private String verficationCode;
    private String userphone;

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

    private HashMap<String, String> stringHashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        init();
        stringHashMap = new HashMap<>();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断输入是否为空
                // 判断输入是否为手机号
                // 发送验证码
                if(!newphone.getText().toString().equals("") ) {
                    //判断输入手机号是否就是原本手机号
                    if(!newphone.getText().toString().equals(userphone)) {
                        //new Thread(postVeri).start();
                        // 跳转到输入验证码页面
                        Intent intent = new Intent(ChangePhone.this,GetVerification.class);
                        //intent.putExtra("verification",verficationCode);
                        intent.putExtra("newPhone",newphone.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ChangePhone.this,"这是当前绑定的手机号，请填写新手机号", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(ChangePhone.this,"需要填写手机号", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    void init(){
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        next = findViewById(R.id.next);
        phone = findViewById(R.id.user_phone);
        phone.setText("当前绑定的手机号为：" + sp.getString("USER_PHONE", ""));
        newphone = findViewById(R.id.newPhone);
        userphone = sp.getString("USER_PHONE", "");
    }


}