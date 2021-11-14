package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.cognitive.Adapter.TabAdapter;
import com.example.cognitive.Fragment.HealthRecipeFragment;
import com.example.cognitive.Fragment.SolarTermFragment;
import com.example.cognitive.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DietSuggest extends AppCompatActivity {
    private List<Fragment> list_fragment; //定义要装frament的列表
    private List<String> list_title; //tab名称列表
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_suggest);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        initView();
        //设置Tablayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为tablayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        TabAdapter adapter = new TabAdapter(this.getSupportFragmentManager(), list_fragment, list_title);
        //viewpager设置适配器
        viewPager.setAdapter(adapter);
        //tabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initView(){
        //定义fragment
        SolarTermFragment solarTermFragment = new SolarTermFragment();
        HealthRecipeFragment healthRecipeFragment = new HealthRecipeFragment();
        //将fragment装进列表
        list_fragment = new ArrayList<>();
        list_fragment.add(solarTermFragment);
        list_fragment.add(healthRecipeFragment);
        //将名称加载tab列表
        list_title = new ArrayList<>();
        list_title.add("节气调理");
        list_title.add("健康食谱");
        //控件初始化
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}