package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cognitive.Activity.LoginActivity;
import com.example.cognitive.R;
import com.example.cognitive.Utils.DestroyActivityUtil;

public class FragmentActivity4 extends Fragment {

    public Button logout;
//    SharedPreferences preferences = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment4,container,false);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
//                editor.commit();
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