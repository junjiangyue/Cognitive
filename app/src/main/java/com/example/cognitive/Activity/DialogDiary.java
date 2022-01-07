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

/*public class DialogDiary extends AppCompatActivity {
    private SharedPreferences spFinishTask;
    private FancyButton btnDiaryCancel;
    private FancyButton btnDiaryFinish;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_diary);
        spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        btnDiaryCancel=findViewById(R.id.btn_diary_cancel);
        btnDiaryCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogDiary.this.finish();
            }
        });
        //完成一项打卡
        btnDiaryFinish=findViewById(R.id.btn_diary_finish);
        btnDiaryFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("diaryFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Intent intent = new Intent(DialogDiary.this, FinishHealthyTask.class);
                AppManager.getAppManager().finishActivity(FinishHealthyTask.class);
                startActivity(intent);
                finish();

                //DialogDiary.this.finish();
            }
        });

    }
}*/

public class DialogDiary extends Dialog {
    private SharedPreferences spFinishTask;
    private FancyButton btnDiaryCancel;
    private FancyButton btnDiaryFinish;
    public interface DataBackListener{
        public void getData(int data);
    }
    DialogDiary.DataBackListener listener;
    public DialogDiary(Context context, final DialogDiary.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_diary);
        //spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        btnDiaryCancel=findViewById(R.id.btn_diary_cancel);
        btnDiaryCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //完成一项打卡
        btnDiaryFinish=findViewById(R.id.btn_diary_finish);
        btnDiaryFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.getData(1);
                dismiss();

                //DialogDiary.this.finish();
            }
        });

    }
}