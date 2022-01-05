package com.example.cognitive.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cognitive.R;

public class AddContactDialog extends Dialog {
    private Button btnOK;
    private Button btnCancel;
    private int confirm;
    public interface DataBackListener{
        public void getData(int data);
    }

    AddContactDialog.DataBackListener listener;
    public AddContactDialog(Context context, final AddContactDialog.DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_contacts);

        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);


        //为“确定”按钮设置点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm = 1;
                listener.getData(confirm);

                dismiss();
            }
        });

        //为“取消”按钮设置点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm = 0;
                listener.getData(confirm);
                dismiss();  //关闭当前对话框
            }
        });
    }
}
