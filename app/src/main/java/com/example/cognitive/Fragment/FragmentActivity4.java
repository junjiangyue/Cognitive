package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cognitive.Activity.ContactsActivity;
import com.example.cognitive.Activity.Exercise;
import com.example.cognitive.Activity.FrailIntro;
import com.example.cognitive.Activity.LoginActivity;
import com.example.cognitive.Activity.PersonalSetting;
import com.example.cognitive.Activity.Sleep;
import com.example.cognitive.R;
import com.example.cognitive.Utils.DestroyActivityUtil;
import com.lucasurbas.listitemview.ListItemView;

public class FragmentActivity4 extends Fragment {

    public ListItemView logout;
    public ListItemView family;
    public ListItemView personal;
    public ListItemView history_test;
    public ListItemView longtime_report;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment4,container,false);
        logout = view.findViewById(R.id.logout);
        family = view.findViewById(R.id.family);
        personal = view.findViewById(R.id.personal);
        history_test = view.findViewById(R.id.history_test);
        longtime_report = view.findViewById(R.id.longtime_report);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactsActivity.class);
                startActivity(intent);
            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalSetting.class);
                startActivity(intent);
            }
        });
        history_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FrailIntro.class);
//                intent.putExtra("data", "mainActivity");
                startActivity(intent);
            }
        });
        longtime_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Exercise.class);
                //intent.putExtra("data", "mainActivity");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		//将DengLuActivity至于栈顶
                startActivity(intent);
                DestroyActivityUtil destroyActivityUtil = new DestroyActivityUtil();
                destroyActivityUtil.exit();

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
    }
}