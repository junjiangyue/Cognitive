package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cognitive.R;
import com.lucasurbas.listitemview.ListItemView;

public class AnotherPersonpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_personpage);
       // 初始化页面
        Intent intent = this.getIntent();
        int userID = Integer.parseInt(intent.getStringExtra("userID"));
        String userName = intent.getStringExtra("userName");
        String userBirth = intent.getStringExtra("userBirth");
        String userSex = intent.getStringExtra("userSex");
        TextView user_name = findViewById(R.id.user_name);
        user_name.setText(userName);
        TextView user_birth = findViewById(R.id.user_age);
        user_birth.setText(userBirth);
        TextView user_sex = findViewById(R.id.user_sex);
        user_sex.setText(userSex);
        ListItemView history_test = findViewById(R.id.history_test);
        ListItemView longtime_report = findViewById(R.id.longtime_report);
        history_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnotherPersonpage.this, TestHistory.class);
                intent.putExtra("USER_ID", String.valueOf(userID));
                startActivity(intent);
            }
        });
        longtime_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnotherPersonpage.this, WeeklyReportHistory.class);
                intent.putExtra("USER_ID", String.valueOf(userID));
                startActivity(intent);
            }
        });

    }

}