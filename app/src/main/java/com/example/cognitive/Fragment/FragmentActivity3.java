package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cognitive.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentActivity3 extends Fragment {
    private LinearLayout title_test;
    private TextView get_date;
    private TextView get_weekday;
    private LinearLayout expandable_part;
    private TextView unfold_picture;
    private LinearLayout manage_disease;
    private LinearLayout disease;
    private TextView history_step;
    private boolean isVisible = true;



    //@Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment3,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        title_test=view.findViewById(R.id.title_test);

        //获取日期
        get_date=view.findViewById(R.id.get_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        get_date.setText(simpleDateFormat.format(date));
        //获取星期
        get_weekday=view.findViewById(R.id.get_weekday);
        String weekDay=getWeekDay();
        get_weekday.setText("星期"+weekDay);



        //我的步数图片的展开与收起
        expandable_part=view.findViewById(R.id.expandable_part);
        expandable_part.setVisibility(View.GONE);
        unfold_picture=view.findViewById(R.id.unfold_picture);
        unfold_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    expandable_part.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
                    unfold_picture.setText("收起图片");
                } else {
                    expandable_part.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
                    unfold_picture.setText("展开图片");
                    isVisible = true;
                }
            }
        });

        //慢性病管理的展开与收起
        manage_disease=view.findViewById(R.id.manage_disease);
        manage_disease.setVisibility(View.GONE);
        disease=view.findViewById(R.id.disease);
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    manage_disease.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
                } else {
                    manage_disease.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
                    isVisible = true;
                }
            }
        });

        //跳转历史步数
        history_step=view.findViewById(R.id.history_step);
        history_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, new EverydayStepNum(), null)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    //获取星期
    public String getWeekDay(){
        Calendar instance = Calendar.getInstance();
        int weekDay = instance.get(Calendar.DAY_OF_WEEK);
        if(weekDay==1){
            return "天";
        } else if(weekDay==2) {
            return "一";
        } else if(weekDay==3) {
            return "二";
        } else if(weekDay==4) {
            return "三";
        } else if(weekDay==5) {
            return "四";
        } else if(weekDay==6) {
            return "五";
        } else if(weekDay==7) {
            return "六";
        } else {
            return null;
        }
    }
}