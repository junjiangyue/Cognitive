package com.example.cognitive.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cognitive.R;
import com.lucasurbas.listitemview.ListItemView;

import java.util.HashMap;

public class PersonalSetting extends AppCompatActivity {

    private ListItemView avatar;
    private ListItemView user_name;
    private ListItemView user_sex;
    private ListItemView user_birth;
    private SharedPreferences sp;
    private HashMap<String, String> stringHashMap;
    private String userphone;
    private String username;
    private String usersex;
    private String userbirth;
    private Button save_change;
    String TAG = PersonalSetting.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        initView();

        user_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeSexDialog changeSexDialog = new ChangeSexDialog(PersonalSetting.this,new ChangeSexDialog.DataBackListener() {
                    @Override
                    public void getData(String data) {
                        String result = data;
                        user_sex.setSubtitle(result);
                    }
                });
                changeSexDialog.show();
            }
        });
        user_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBirthDialog changeBirthDialog = new ChangeBirthDialog(PersonalSetting.this,new ChangeBirthDialog.DataBackListener() {
                    @Override
                    public void getData(String data) {
                        String result = data;
                        user_birth.setSubtitle(result);
                    }
                });
                changeBirthDialog.show();
            }
        });
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNameDialog changeNameDialog = new ChangeNameDialog(PersonalSetting.this,new ChangeNameDialog.DataBackListener() {
                    @Override
                    public void getData(String data) {
                        String result = data;
                        user_name.setSubtitle(result);
                    }
                });
                changeNameDialog.show();
            }
        });

    }

    private void initView() {
        // 注册组件
        avatar=findViewById(R.id.avatar);
        user_name = findViewById(R.id.user_name);
        user_sex = findViewById(R.id.user_sex);
        user_birth = findViewById(R.id.user_birth);
        save_change = findViewById(R.id.save_change);
        sp = PersonalSetting.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userphone = sp.getString("USER_PHONE", "");
        username = sp.getString("USER_NAME", "");
        usersex = sp.getString("USER_SEX", "");
        userbirth = sp.getString("USER_BIRTH", "");
        if(username!=""){
            user_name.setSubtitle(username);
            user_birth.setSubtitle(userbirth);
            user_sex.setSubtitle(usersex);
        } // 求求你不要退出登录，我真的不想写if else了

    }

}