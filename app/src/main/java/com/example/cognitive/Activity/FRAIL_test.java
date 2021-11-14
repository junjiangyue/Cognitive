package com.example.cognitive.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.Adapter.MyExpandableListViewAdapter;
import com.example.cognitive.R;

import java.util.ArrayList;
import java.util.List;


public class FRAIL_test extends AppCompatActivity {
    private ExpandableListView mExpandableListView = null;
    // 列表数据
    private List<String> mGroupNameList = null;
    private List<List<String>> mItemNameList = null;
    // 适配器
    private MyExpandableListViewAdapter mAdapter = null;
    public static FRAIL_test Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frail_test);
        Test=this;

        // 获取组件
        mExpandableListView = (ExpandableListView) findViewById(R.id.expand_list);
        mExpandableListView.setGroupIndicator(null);

        // 初始化数据
        initData();
        // 为ExpandableListView设置Adapter
        mAdapter = new MyExpandableListViewAdapter(this, mGroupNameList, mItemNameList);
        mExpandableListView.setAdapter(mAdapter);


        // 监听组点击
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if (mGroupNameList.get(groupPosition).isEmpty()) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
//        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
//                                        int childPosition, long id) {
//                Toast.makeText(FRAIL_test.this, "hello", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                MyExpandableListViewAdapter adapter=(MyExpandableListViewAdapter) mExpandableListView.getExpandableListAdapter();
                if (adapter == null) {return;}
                for (int i = 0; i < adapter.getGroupCount(); i++)
                {
                    Log.i("gwr", "onGroupExpand: "+Integer.toString(mAdapter.status[i]));
                    if (i != groupPosition)
                    {
                        mExpandableListView.collapseGroup(i);
                    }
                }
            }
        });


//        Button yesButton=new Button(this);
//        yesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyExpandableListViewAdapter adapter=(MyExpandableListViewAdapter) mExpandableListView.getExpandableListAdapter();
//                Log.i("ggwwrr", "onClick: ");
//                mExpandableListView.collapseGroup(0);
//
//            }
//        });
    }


    public void chooseYes(View view)
    {
        MyExpandableListViewAdapter adapter=(MyExpandableListViewAdapter) mExpandableListView.getExpandableListAdapter();
        Log.i("gwr", "onClick:Yes ");
        for (int i = 0; i < adapter.getGroupCount(); i++)
        {
            mExpandableListView.collapseGroup(i);
        }
    }

    public void chooseNo(View view)
    {
        MyExpandableListViewAdapter adapter=(MyExpandableListViewAdapter) mExpandableListView.getExpandableListAdapter();
        Log.i("gwr", "onClick:No ");
        for (int i = 0; i < adapter.getGroupCount(); i++)
        {
            mExpandableListView.collapseGroup(i);
        }
    }

    // 初始化数据
    private void initData(){
        // 组名
        mGroupNameList = new ArrayList<String>();
        mGroupNameList.add("第一题");
        mGroupNameList.add("第二题");
        mGroupNameList.add("第三题");
        mGroupNameList.add("第四题");
        mGroupNameList.add("第五题");

        mItemNameList = new  ArrayList<List<String>>();


        // 第一题
        List<String> itemList = new ArrayList<String>();
        itemList.add(getResources().getString(R.string.FRAIL_1));
        mItemNameList.add(itemList);

        // 第二题
        itemList = new ArrayList<String>();
        itemList.add(getResources().getString(R.string.FRAIL_2));
        mItemNameList.add(itemList);

        // 第三题
        itemList = new ArrayList<String>();
        itemList.add(getResources().getString(R.string.FRAIL_3));
        mItemNameList.add(itemList);

        // 第四题
        itemList = new ArrayList<String>();
        itemList.add(getResources().getString(R.string.FRAIL_4));
        mItemNameList.add(itemList);

        // 第五题
        itemList = new ArrayList<String>();
        itemList.add(getResources().getString(R.string.FRAIL_5));
        mItemNameList.add(itemList);

    }

    //点击按钮，传递分数
    public void calculateScore(View view)
    {
        int score=0;
        for(int i=0;i<5;i++)
        {
            score+=mAdapter.score[i];
        }
        Log.i("score result",String.valueOf(score));
        Intent intent=new Intent(FRAIL_test.this,FrailResultPage.class);
        intent.putExtra("score",String.valueOf(score));
        //finish();
        startActivity(intent);
    }
}