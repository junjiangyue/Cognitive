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

public class DialogWater extends Dialog {
    private SharedPreferences spFinishTask;
    private FancyButton btnWaterCancel;
    private FancyButton btnWaterFinish;
    public interface DataBackListener{
        public void getData(int data);
    }
    DialogWater.DataBackListener listener;
    public DialogWater(Context context, final DialogWater.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_water);
        btnWaterCancel=findViewById(R.id.btn_water_cancel);
        btnWaterCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //完成一项打卡
        btnWaterFinish=findViewById(R.id.btn_water_finish);
        btnWaterFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.getData(1);
                dismiss();
                //DialogDiary.this.finish();
            }
        });
    }
}
