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

/*public class DialogSmile extends AppCompatActivity {
    private SharedPreferences spFinishTask;
    private FancyButton btnSmileCancel;
    private FancyButton btnSmileFinish;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_smile);
        spFinishTask=this.getSharedPreferences("userFinishTask", Context.MODE_PRIVATE);
        btnSmileCancel=findViewById(R.id.btn_smile_cancel);
        btnSmileCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogSmile.this.finish();
            }
        });
        //完成一项打卡
        btnSmileFinish=findViewById(R.id.btn_smile_finish);
        btnSmileFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = spFinishTask.edit();
                editor.putInt("smileFinish", 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //获取当前日期
                Date date = new Date(System.currentTimeMillis());
                editor.putString("finishDate",simpleDateFormat.format(date));
                editor.commit();
                Intent intent = new Intent(DialogSmile.this, FinishHealthyTask.class);
                AppManager.getAppManager().finishActivity(FinishHealthyTask.class);
                startActivity(intent);
                finish();

                //DialogDiary.this.finish();
            }
        });
    }
}*/
public class DialogSmile extends Dialog {
    private FancyButton btnSmileCancel;
    private FancyButton btnSmileFinish;
    public interface DataBackListener{
        public void getData(int data);
    }
    DialogSmile.DataBackListener listener;
    public DialogSmile(Context context, final DialogSmile.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_smile);
        btnSmileCancel=findViewById(R.id.btn_smile_cancel);
        btnSmileCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //完成一项打卡
        btnSmileFinish=findViewById(R.id.btn_smile_finish);
        btnSmileFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.getData(1);
                dismiss();
                //DialogDiary.this.finish();
            }
        });
    }
}
