package com.example.cognitive.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cognitive.Activity.GuideActivity;
import com.example.cognitive.R;


public class FillSexFragment extends Fragment {

    private GuideActivity mActivity;
    private ImageButton men;
    private ImageButton women;
    private String sex = "未设置";
    private SharedPreferences sp;//用来记住昵称
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fill_sex, container, false);
        ImageButton vp_Btn = (ImageButton) view.findViewById(R.id.next);
        mActivity = (GuideActivity) getActivity();
        vp_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.onNextFragment();
                sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("USER_SEX", sex);
                editor.commit();
            }
        });

        ImageButton men = (ImageButton) view.findViewById(R.id.men);
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                men.setImageResource(R.drawable.men_unchosen);
                sex="男";
            }
        });
        ImageButton women = (ImageButton) view.findViewById(R.id.women);
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                women.setImageResource(R.drawable.women_unchosen);
                sex="女";
            }
        });
        return view;
    }
}