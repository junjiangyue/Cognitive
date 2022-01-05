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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cognitive.Activity.ADIntro;
import com.example.cognitive.Activity.FrailIntro;
import com.example.cognitive.Activity.TestHistory;
import com.example.cognitive.R;

public class FragmentActivity1 extends Fragment {
    private LinearLayout title_test;
    private ImageView button_test1;
    private ImageView button_test2;

    private ImageView history;

    //private Button go_test;



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

        history=view.findViewById(R.id.history);

        //go_test=view.findViewById(R.id.go_test_button);

        button_test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ADIntro.class);
                startActivity(intent);
            }
        });

        button_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FrailIntro.class);
                startActivity(intent);
            }
        });

//        go_test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), FrailIntro.class);
//                startActivity(intent);
//            }
//        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TestHistory.class);
                startActivity(intent);
            }
        });
    }
}

