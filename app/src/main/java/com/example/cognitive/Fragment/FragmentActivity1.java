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

import com.example.cognitive.Activity.ADIntro;
import com.example.cognitive.Activity.FrailIntro;
import com.example.cognitive.Activity.TestHistory;
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

    private Button go_test;



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

        button_history1=view.findViewById(R.id.history1);

        go_test=view.findViewById(R.id.go_test_button);
        Button [] buttons={button_test1,button_test2,button_history1,
        };
        int[] colors={R.color.indigo,R.color.orange,R.color.light_pink,};

        for(int i=0;i<3;i++)
        {
            GradientDrawable shape=new GradientDrawable();
            shape.setCornerRadius(50);
            shape.setColor(getResources().getColor(colors[i]));
            buttons[i].setBackground(shape);
        }
        button_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FrailIntro.class);
                startActivity(intent);
            }
        });

        button_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ADIntro.class);
                startActivity(intent);
            }
        });

        go_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FrailIntro.class);
                startActivity(intent);
            }
        });

        button_history1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TestHistory.class);
                startActivity(intent);
            }
        });
    }
}

