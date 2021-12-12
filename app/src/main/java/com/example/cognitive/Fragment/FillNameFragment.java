package com.example.cognitive.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cognitive.Activity.GuideActivity;
import com.example.cognitive.R;

public class FillNameFragment  extends Fragment {
    // 注册按钮
    private ImageButton next;
    private View view;
    private EditText user_name;
    private SharedPreferences sp;//用来记住昵称
    private GuideActivity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fillname, null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        ImageButton vp_Btn = (ImageButton) view.findViewById(R.id.next);
        EditText user_name = (EditText) view.findViewById(R.id.et_data_uname);
        mActivity = (GuideActivity) getActivity();
        vp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("USER_NAME", user_name.getText().toString());
                editor.commit();
                mActivity.onNextFragment();
            }
        });
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}