package com.example.cognitive.Adapter;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cognitive.R;

import java.util.ArrayList;

public class MyFrailPagerAdapter extends PagerAdapter {

    private ArrayList<View> viewLists;
    private final CheckBox [] checkBoxes=new CheckBox[11];
    public int [] result=new int[5];
    public int [] checked_status=new int[11];
    public int [] state = new int[5];
    public int weight_pre;
    public int weight_cur;


    public MyFrailPagerAdapter() {
    }

    public MyFrailPagerAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        int [] yes_buttons=new int[]{R.id.frail_yes_1,R.id.frail_yes_2,R.id.frail_yes_3};
        int [] no_buttons=new int[]{R.id.frail_no_1,R.id.frail_no_2,R.id.frail_no_3};
        switch (position)
        {
            case 0:
            case 1:
            case 2:
                RadioButton radioButton_yes = (RadioButton) viewLists.get(position).findViewById(yes_buttons[position]);
                radioButton_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result[position]=1;
                        state[position] = 1;
                        Log.i("click", "onClick: yes"+position);
                        Log.i("result", "onClick: result:"+result[position]);
                    }
                });

                RadioButton radioButton_no = (RadioButton) viewLists.get(position).findViewById(no_buttons[position]);
                radioButton_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        result[position]=0;
                        state[position] = 1;
                        Log.i("click", "onClick: no"+position);
                        Log.i("result", "onClick: result:"+result[position]);
                    }
                });
            case 3:
                int [] temp_checkbox= {R.id.disease_1, R.id.disease_2, R.id.disease_3, R.id.disease_4, R.id.disease_5, R.id.disease_6,
                                        R.id.disease_7, R.id.disease_8, R.id.disease_9, R.id.disease_10, R.id.disease_11};
                for(int i=0;i<11;i++)
                {
                    checkBoxes[i]=(CheckBox) viewLists.get(3).findViewById(temp_checkbox[i]);
                    int finalI = i;
                    checkBoxes[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checked_status[finalI]==0)
                            {
                                checked_status[finalI]=1;
                                state[position] = 1;
                                result[3]++;
                            }
                            else
                            {
                                checked_status[finalI]=0;
                                state[position] = 0;
                                result[3]--;
                            }
                            Log.i("click", "onClick: checkbox"+finalI);
                            Log.i("result", "onClick: checkbox result:"+result[3]);
                        }
                    });
                }
                break;
            case 4:
                EditText editText_pre = (EditText) viewLists.get(4).findViewById(R.id.previous_weight);
                EditText editText_cur = (EditText) viewLists.get(4).findViewById(R.id.current_weight);
                editText_pre.addTextChangedListener(textWatcher_pre);
                editText_cur.addTextChangedListener(textWatcher_cur);

                //result[4]=((float)(weight_pre-weight_cur)/(float)weight_cur>0.05)? 1:0;
                //Log.i("weight", "afterTextChanged: "+result[4]);
                //Log.i("weight", "afterTextChanged: "+(float)(weight_pre-weight_cur)/(float)weight_cur);
                break;
            default:
                break;
        }
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewLists.get(position));
    }

    TextWatcher textWatcher_pre=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>0&&s.length()<5) {
                weight_pre = Integer.parseInt(s.toString());
                state[4] =1;
            }
            else
            {
                weight_pre=0;
                state[4] = 0;
            }
            //Log.i("weight", "afterTextChanged: "+weight_pre);
        }
    };

    TextWatcher textWatcher_cur=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>0&&s.length()<5) {
                weight_cur = Integer.parseInt(s.toString());
                state[4] = 1;
            }
            else
            {
                weight_cur=0;
                state[4] = 0;
            }
            //Log.i("weight", "afterTextChanged: "+weight_cur);
        }
    };
}
