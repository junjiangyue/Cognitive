package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.cognitive.Activity.FRAIL_intropage;
import com.example.cognitive.R;

public class FragmentActivity1 extends Fragment {
    private LinearLayout title_test;
    private Button button_test1;
    private Button button_test2;
    private Button button_test3;
    private Button button_test4;
    private Button button_test5;
    private Button button_test6;

    private Button button_history1;
    private Button button_history2;
    private Button button_history3;
    private Button button_history4;
    private Button button_history5;
    private Button button_history6;
    private Button button_history7;
    private Button button_history8;
    private Button button_history9;
    private Button button_history10;
    private Button button_history11;
    private Button button_history12;




    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_fragment1,container,false);
        return view;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        title_test=view.findViewById(R.id.title_test);
        button_test1=view.findViewById(R.id.test1);
        button_test2=view.findViewById(R.id.test2);
        button_test3=view.findViewById(R.id.test3);
        button_test4=view.findViewById(R.id.test4);
        button_test5=view.findViewById(R.id.test5);
        button_test6=view.findViewById(R.id.test6);

        button_history1=view.findViewById(R.id.history1);
        button_history2=view.findViewById(R.id.history2);
        button_history3=view.findViewById(R.id.history3);
        button_history4=view.findViewById(R.id.history4);
        button_history5=view.findViewById(R.id.history5);
        button_history6=view.findViewById(R.id.history6);
        button_history7=view.findViewById(R.id.history7);
        button_history8=view.findViewById(R.id.history8);
        button_history9=view.findViewById(R.id.history9);
        button_history10=view.findViewById(R.id.history10);
        button_history11=view.findViewById(R.id.history11);
        button_history12=view.findViewById(R.id.history12);

        Button [] buttons={button_test1,button_test2,button_test3,button_test4,button_test5,button_test6,button_history1,button_history2,button_history3,button_history4,
                button_history5,button_history6,button_history7,button_history8,button_history9,button_history10,button_history11,button_history12
        };
        int[] colors={R.color.indigo,R.color.orange,R.color.light_pink,
                R.color.lighter_green,R.color.green_alt,R.color.blue_alt,
                R.color.pink,R.color.light_orange,R.color.yellow,
                R.color.light_green,R.color.green,R.color.blue_green,
                R.color.light_blue,R.color.blue,R.color.purple,
                R.color.light_purple,R.color.pink_alt,R.color.pink};

        for(int i=0;i<18;i++)
        {
            GradientDrawable shape=new GradientDrawable();
            shape.setCornerRadius(50);
            shape.setColor(getResources().getColor(colors[i]));
            buttons[i].setBackground(shape);
        }
        button_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FRAIL_intropage.class);
                startActivity(intent);
            }
        });
    }

}

