package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.cognitive.R;

public class ChangeBirthDialog extends Dialog {
    private DatePicker datePicker;
    private String birth;
    private Button btnOK;
    private Button btnCancel;
    public interface DataBackListener{
        public void getData(String data);
    }
    ChangeBirthDialog.DataBackListener listener;
    public ChangeBirthDialog(Context context, final DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_birth_dialog);
        datePicker = findViewById(R.id.datePicker);
        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);


        //为“确定”按钮设置点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处写需要处理的逻辑
                int y=datePicker.getYear();
                int m=datePicker.getMonth()+1;
                int d=datePicker.getDayOfMonth();
                birth = String.valueOf(y) +"年"+ String.valueOf(m) +"月"+String.valueOf(d)+"日";
                listener.getData(birth);
                dismiss();
            }
        });

        //为“取消”按钮设置点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();  //关闭当前对话框
            }
        });
    }
}