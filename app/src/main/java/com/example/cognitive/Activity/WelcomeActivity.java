package com.example.cognitive.Activity;
// 欢迎进入
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import com.example.cognitive.R;
import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

        private ViewPager viewPager;
        //引导图片的资源id
        private int[] mImageIds = new int[]{
                R.drawable.welcome1
        };

        private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();

        private Button btn_start;
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            //设置标题栏隐藏
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.activity_welcome);

            initViews();
            //找到ViewPager
            viewPager = (ViewPager) findViewById(R.id.vp_guide);
            //为viewPager设置适配器
            viewPager.setAdapter(new GuideAdapter());

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int i) {
                        btn_start.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

            btn_start = (Button) findViewById(R.id.btn_start);
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //引导页面的开始体验按钮被点击后设置配置文件的用户引导显示状态
                    SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
                    sp.edit().putBoolean("is_user_guide_show_state",true).commit();
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }
            });



        }

        /**
         * 初始化视图
         */
        public void initViews(){

            for(int i =0 ; i< mImageIds.length; i++){
                //声明一个ImageView
                ImageView image = new ImageView(this);
                //设置背景资源为对应的图片
                image.setBackgroundResource(mImageIds[i]);
                //把ImageView添加到图片列表中
                mImageViewList.add(image);

            }
        }
        //自定义适配器
        private class GuideAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return mImageIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }
            //实例化项目
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageViewList.get(position));
                return mImageViewList.get(position);
            }
            //销毁项目
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
}