package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.cognitive.R;
import com.lucasurbas.listitemview.ListItemView;

public class AccountSecurity extends AppCompatActivity {

    private ListItemView changePhone;
    private ListItemView changePassword;
    private SharedPreferences sp; // sharedPerferences实现记住密码和自动登录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        init();
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSecurity.this,ChangePhone.class);
                startActivity(intent);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSecurity.this,ChangePassword.class);
                startActivity(intent);
            }
        });
    }
    void init(){
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        changePhone = findViewById(R.id.user_phone);
        changePassword = findViewById(R.id.change_psw);
        changePhone.setSubtitle(sp.getString("USER_PHONE", ""));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}