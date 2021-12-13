package com.example.cognitive.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.cognitive.Activity.GuideActivity;
import com.example.cognitive.R;
import com.loper7.date_time_picker.DateTimeConfig;
import com.loper7.date_time_picker.DateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FillAgeFragment extends Fragment {
    private GuideActivity mActivity;
    private DatePicker datePicker;
    private SharedPreferences sp;//用来记住年龄

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.layout_fillage, container, false);

        ImageButton vp_Btn = (ImageButton) view.findViewById(R.id.next);

        // 日期选择器初始化
        datePicker = view.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        int year         = calendar.get(Calendar.YEAR);
        int monthOfYear  = calendar.get(Calendar.MONTH);
        int dayOfMonth   = calendar.get(Calendar.DAY_OF_MONTH);
//        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                // 获取一个日历对象，并初始化为当前选择时间
//                calendar.set(year,monthOfYear,dayOfMonth);
//                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月DD日 HH:mm");
//                Toast.makeText(getActivity(),format.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
//
//            }
//        });
        mActivity = (GuideActivity) getActivity();
        vp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int y=datePicker.getYear();
                int m=datePicker.getMonth();
                int d=datePicker.getDayOfMonth();
                String birth = String.valueOf(y) +"年"+ String.valueOf(m) +"月"+String.valueOf(d)+"日";
                calendar.set(y,m,d);
                mActivity.onNextFragment();
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月DD日 HH:mm");
                Toast.makeText(getActivity(),format.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
                sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("USER_BIRTH", birth);
                editor.commit();
            }
        });
        return view;
    }
    public static String getAge(String str) {

        // 使用默认时区和语言环境获得一个日历
        Calendar cal = Calendar.getInstance();
        int a = Integer.parseInt((str.subSequence(0, 4).toString())); // 输入的年
        int a1 = Integer.parseInt((str.subSequence(4, 6).toString())); // 输入的月
        int a2 = Integer.parseInt((str.subSequence(6, 8).toString())); // 输入的日
        int b = cal.get(Calendar.YEAR); // 当前的年
        int b1 = cal.get(Calendar.MONDAY) + 1; // 当前的月
        int b2 = cal.get(Calendar.DAY_OF_MONTH); // 当前的日

        // 粗略判断
        String err = "身份证号中的出生年月日有误";
        if (a > b || a < 1000 || a1 > 12 || a1 <= 0 || a2 > 31 || a2 <= 0) {
            return err;
        }

        // 进一步判断
        if ((a % 4 == 0 && a1 == 2 && a2 > 29) || (a % 4 != 0 && a1 == 2 && a2 > 28)
                || ((a1 == 4 || a1 == 6 || a1 == 9 || a1 == 11) && a2 > 30)
                || (a == b && (a1 < b1 || (a1 == b1 && a2 > b2)))) {
            return err;
        }

        // 计算年龄
        int age = b - a;
        if (b1 < a1 || (b1 == a1 && b2 <= a2)) {
            age--;
        }
        return "年龄：" + age + "岁";

    }

}