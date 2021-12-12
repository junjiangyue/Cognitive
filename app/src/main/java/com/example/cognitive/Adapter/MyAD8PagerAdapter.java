package com.example.cognitive.Adapter;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.viewpager.widget.PagerAdapter;

import com.example.cognitive.R;

import java.util.ArrayList;

public class MyAD8PagerAdapter extends PagerAdapter {

    private ArrayList<View> viewLists;

    //存储结果的数组
    public int [] result=new int[8];

    public MyAD8PagerAdapter() {
    }

    public MyAD8PagerAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        int[] yes_buttons= new int[]{R.id.ad8_yes_1,R.id.ad8_yes_2,R.id.ad8_yes_3,R.id.ad8_yes_4,R.id.ad8_yes_5,R.id.ad8_yes_6
                ,R.id.ad8_yes_7,R.id.ad8_yes_8};
        int[] no_buttons= new int[]{R.id.ad8_no_1,R.id.ad8_no_2,R.id.ad8_no_3,R.id.ad8_no_4,R.id.ad8_no_5,R.id.ad8_no_6
                ,R.id.ad8_no_7,R.id.ad8_no_8};
        RadioButton radioButton_yes=(RadioButton) viewLists.get(position).findViewById(yes_buttons[position]);
        radioButton_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result[position]=1;
                Log.i("click", "onClick: yes"+position);
                Log.i("result", "onClick: result:"+result[position]);
            }
        });
        RadioButton radioButton_no=(RadioButton) viewLists.get(position).findViewById(no_buttons[position]);
        radioButton_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result[position]=0;
            }
        });
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}
