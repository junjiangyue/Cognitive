package com.example.cognitive.Activity;
// 引导用户选择个人信息
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cognitive.Adapter.MyPagerAdapter;
import com.example.cognitive.Adapter.TabAdapter;
import com.example.cognitive.Fragment.FillAgeFragment;
import com.example.cognitive.Fragment.FillKnowledgeFragment;
import com.example.cognitive.Fragment.FillNameFragment;
import com.example.cognitive.Fragment.FillSexFragment;
import com.example.cognitive.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private List<Fragment> list_fragment; //定义要装frament的列表
    private List<String> list_title; //tab名称列表
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int fragmentIndex = 0;
    private SharedPreferences sp;//用来记住昵称

    public static GuideActivity guideActivity;//用于按下退出键的时候结束该fragment
    public long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideActivity=this;
        initView();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为tablayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(3)));
        TabAdapter adapter = new TabAdapter(this.getSupportFragmentManager(), list_fragment, list_title);
        //viewpager设置适配器
        viewPager.setAdapter(adapter);
        //tabLayout加载viewpager
        tabLayout.setupWithViewPager(viewPager);

        View layout_fillname = LayoutInflater.from(this).inflate(R.layout.layout_fillname, null);
//        if(LogoActivity.logoActivity!=null)
//            LogoActivity.logoActivity.finish();
//        if(LoginActivity.loginActivity!=null)
//            LoginActivity.loginActivity.finish();
    }
    private void initView(){
        //定义fragment
        FillNameFragment fillNameFragment = new FillNameFragment();
        FillAgeFragment fillAgeFragment = new FillAgeFragment();
        FillSexFragment fillSexFragment = new FillSexFragment();
        FillKnowledgeFragment fillKnowledgeFragment = new FillKnowledgeFragment();
        //将fragment装进列表
        list_fragment = new ArrayList<>();
        list_fragment.add(fillNameFragment);
        list_fragment.add(fillAgeFragment);
        list_fragment.add(fillSexFragment);
        list_fragment.add(fillKnowledgeFragment);
        //将名称加载tab列表
        list_title = new ArrayList<>();
        list_title.add("节气调理");
        list_title.add("健康食谱");
        list_title.add("3");
        list_title.add("4");
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager_fragment_pager);

    }
    public void onNextFragment() {
        fragmentIndex = (fragmentIndex + 1) % list_fragment.size();
        viewPager.setCurrentItem(fragmentIndex);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onBackPressed(){
        //super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                //退出系统，不保存之前页面
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
