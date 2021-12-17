package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cognitive.R;
public class ChangeSexDialog extends Dialog {

    private Button btnOK;
    private Button btnCancel;
    private ImageButton men;
    private ImageButton women;
    private String sex = "未设置";

    // 定义回调接口
    public interface DataBackListener{
        public void getData(String data);
    }
    DataBackListener listener;

    public ChangeSexDialog(Context context, final DataBackListener listener){
        super(context);
        //用传递过来的监听器来初始化
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //去除标题
        setContentView(R.layout.activity_change_sex_dialog);
        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        ImageButton men = (ImageButton) findViewById(R.id.men);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.setImageResource(R.drawable.men_unchosen);
                sex="男";
            }
        });
        ImageButton women = (ImageButton) findViewById(R.id.women);
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                women.setImageResource(R.drawable.women_unchosen);
                sex="女";
            }
        });
        //为“确定”按钮设置点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处写需要处理的逻辑
                listener.getData(sex);
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