package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cognitive.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeNameDialog extends Dialog {
    private Button btnOK;
    private Button btnCancel;
    private String name;
    private EditText user_name;
    public interface DataBackListener{
        public void getData(String data);
    }
    ChangeNameDialog.DataBackListener listener;
    public ChangeNameDialog(Context context, final ChangeNameDialog.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_dialog);

        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        user_name = (EditText)findViewById(R.id.user_name);
        //为“确定”按钮设置点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处写需要处理的逻辑
                name = user_name.getText().toString();
                listener.getData(name);
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