package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.cognitive.R;
import com.example.cognitive.Fragment.FragmentActivity1;
import com.example.cognitive.Fragment.FragmentActivity2;
import com.example.cognitive.Fragment.FragmentActivity3;
import com.example.cognitive.Fragment.FragmentActivity4;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout main_body;
    private TextView bottom_bar_text_1;
    private ImageView bottom_bar_image_1;
    private TextView bottom_bar_text_2;
    private ImageView bottom_bar_image_2;
    private TextView bottom_bar_text_3;
    private ImageView bottom_bar_image_3;
    private TextView bottom_bar_text_4;
    private ImageView bottom_bar_image_4;

    private LinearLayout main_body_bar;
    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private RelativeLayout bottom_bar_3_btn;
    private RelativeLayout bottom_bar_4_btn;

    private FragmentActivity1 fragment1;
    private FragmentActivity2 fragment2;
    private FragmentActivity3 fragment3;
    private FragmentActivity4 fragment4;


    private int TAG=1;

    //SharedPreferences sp = this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,new FragmentActivity1()).commit();
        initView();

        fragment1=new FragmentActivity1();
        fragment2=new FragmentActivity2();
        fragment3=new FragmentActivity3();
        fragment4=new FragmentActivity4();

    }


    private View.OnClickListener MyListener=new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(TAG==0)
            {
                TAG=1;
                switch (v.getId())
                {
                    case R.id.bottom_bar_1_btn:
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment1).commitAllowingStateLoss();
                        setSelectStatus(0);
                        break;

                    case R.id.bottom_bar_2_btn:
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment3).commitAllowingStateLoss();
                        setSelectStatus(1);
                        break;

                    case R.id.bottom_bar_3_btn:
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment2).commitAllowingStateLoss();
                        setSelectStatus(2);
                        break;

                    case R.id.bottom_bar_4_btn:
                        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment4).commitAllowingStateLoss();
                        setSelectStatus(3);
                        break;
                }
            }
            else if(TAG==1)
            {
                switch (v.getId())
                {
                    case R.id.bottom_bar_1_btn:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment1).commitAllowingStateLoss();
                        setSelectStatus(0);
                        break;

                    case R.id.bottom_bar_2_btn:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment3).commitAllowingStateLoss();
                        setSelectStatus(1);
                        break;

                    case R.id.bottom_bar_3_btn:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment2).commitAllowingStateLoss();
                        setSelectStatus(2);
                        break;

                    case R.id.bottom_bar_4_btn:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment4).commitAllowingStateLoss();
                        setSelectStatus(3);
                        break;
                }
            }
        }
    };

    private void initView()
    {
        main_body=findViewById(R.id.main_body);
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottom_bar_text_3=findViewById(R.id.bottom_bar_text_3);
        bottom_bar_text_4=findViewById(R.id.bottom_bar_text_4);
        bottom_bar_image_1=findViewById(R.id.bottom_bar_image_1);
        bottom_bar_image_2=findViewById(R.id.bottom_bar_image_2);
        bottom_bar_image_3=findViewById(R.id.bottom_bar_image_3);
        bottom_bar_image_4=findViewById(R.id.bottom_bar_image_4);

        main_body_bar=findViewById(R.id.main_body_bar);
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);
        bottom_bar_4_btn=findViewById(R.id.bottom_bar_4_btn);

        bottom_bar_1_btn.setOnClickListener(MyListener);
        bottom_bar_2_btn.setOnClickListener(MyListener);
        bottom_bar_3_btn.setOnClickListener(MyListener);
        bottom_bar_4_btn.setOnClickListener(MyListener);

        bottom_bar_image_1.setImageResource(R.drawable.test);
        bottom_bar_text_1.setTextColor(Color.parseColor("#0097f7"));

        setSelectStatus(0);
    }

    public void setSelectStatus(int index)
    {
        switch (index)
        {
            case 0:
                bottom_bar_image_1.setImageResource(R.drawable.test);
                bottom_bar_text_1.setTextColor(Color.parseColor("#0097f7"));

                bottom_bar_image_2.setImageResource(R.drawable.detect);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.knowledge);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.my);
                bottom_bar_text_4.setTextColor(Color.parseColor("#000000"));

                break;

            case 1:
                bottom_bar_image_1.setImageResource(R.drawable.test);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.detect);
                bottom_bar_text_2.setTextColor(Color.parseColor("#0097f7"));

                bottom_bar_image_3.setImageResource(R.drawable.knowledge);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.my);
                bottom_bar_text_4.setTextColor(Color.parseColor("#000000"));
                break;

            case 2:
                bottom_bar_image_1.setImageResource(R.drawable.test);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.detect);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.knowledge);
                bottom_bar_text_3.setTextColor(Color.parseColor("#0097f7"));

                bottom_bar_image_4.setImageResource(R.drawable.my);
                bottom_bar_text_4.setTextColor(Color.parseColor("#000000"));
                break;

            case 3:
                bottom_bar_image_1.setImageResource(R.drawable.test);
                bottom_bar_text_1.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_2.setImageResource(R.drawable.detect);
                bottom_bar_text_2.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_3.setImageResource(R.drawable.knowledge);
                bottom_bar_text_3.setTextColor(Color.parseColor("#000000"));

                bottom_bar_image_4.setImageResource(R.drawable.my);
                bottom_bar_text_4.setTextColor(Color.parseColor("#0097f7"));
                break;
        }
    }
}