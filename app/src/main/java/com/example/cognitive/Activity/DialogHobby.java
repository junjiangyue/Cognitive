package com.example.cognitive.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.R;
import com.example.cognitive.Utils.AppManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;

/*public class DialogHobby extends AppCompatActivity {
    private SharedPreferences spFinishTask;
    private FancyButton btnHobbyFinish;
    private FancyButton btnHobbyCancel;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hobby);
        spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        btnHobbyCancel=findViewById(R.id.btn_hobby_cancel);
        btnHobbyCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogHobby.this.finish();
            }
        });
        //完成一项打卡
        btnHobbyFinish=findViewById(R.id.btn_hobby_finish);
        btnHobbyFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("hobbyFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Intent intent = new Intent(DialogHobby.this, FinishHealthyTask.class);
                AppManager.getAppManager().finishActivity(FinishHealthyTask.class);
                startActivity(intent);
                finish();

                //DialogDiary.this.finish();
            }
        });
    }
}*/
public class DialogHobby extends Dialog {
    private SharedPreferences spFinishTask;
    private FancyButton btnHobbyFinish;
    private FancyButton btnHobbyCancel;
    public interface DataBackListener{
        public void getData(int data);
    }
    DialogHobby.DataBackListener listener;
    public DialogHobby(Context context, final DialogHobby.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hobby);
        btnHobbyCancel=findViewById(R.id.btn_hobby_cancel);
        btnHobbyCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //完成一项打卡
        btnHobbyFinish=findViewById(R.id.btn_hobby_finish);
        btnHobbyFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.getData(1);
                dismiss();

                //DialogDiary.this.finish();
            }
        });
    }
}