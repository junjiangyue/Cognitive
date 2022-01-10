package com.example.cognitive.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cognitive.Activity.ArticleInfo;
import com.example.cognitive.Activity.DietSuggest;
import com.example.cognitive.Activity.Sleep;
import com.example.cognitive.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentActivity2 extends Fragment {
    private TextView mTvSearch;
    //private LinearLayout title_test;
    private Banner banner;
    private List<Integer> image=new ArrayList<>();
    private List<String> title=new ArrayList<>();
    private CardView suggest1;
    private CardView suggest2;
    //@Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment2,container,false);
        banner = view.findViewById(R.id.banner);
        suggest1 = view.findViewById(R.id.card_view1);
        suggest2 = view.findViewById(R.id.card_view2);
        initData();
        initView();
        suggest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleInfo.class);
                intent.putExtra("newsUrl","https://mp.weixin.qq.com/s/6xx808UhGhCT7WOJEkxysQ");
                startActivity(intent);
            }
        });
        suggest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArticleInfo.class);
                intent.putExtra("newsUrl","https://mp.weixin.qq.com/s/whyQsSXhwQQc0IV93t7Z3w");
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

    }

    private void initView() {


        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setImageLoader(new MyImageLoader());

        banner.setImages(image);

        banner.setBannerAnimation(Transformer.Default);

        banner.isAutoPlay(true);

        banner.setBannerTitles(title);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//可以根据自己想要的更换stylr

        banner.setDelayTime(3000);

        banner.setOnBannerListener(this::OnBannerClick);

        banner.start();
    }
    public void OnBannerClick(int position) {
        //Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
        switch (position){
            case 0 :
                Intent intent = new Intent(getActivity(), ArticleInfo.class);
                intent.putExtra("newsUrl","https://mp.weixin.qq.com/s/FpC5S4cuZIvPM3QSonH_tA");
                startActivity(intent);break;
            case 1 :
                Intent intent2 = new Intent(getActivity(), ArticleInfo.class);
                intent2.putExtra("newsUrl","https://mp.weixin.qq.com/s/YW8OGqBE_SrtteIWc5ZuIA");
                startActivity(intent2);
            case 2:
                Intent intent3 = new Intent(getActivity(), ArticleInfo.class);
                intent3.putExtra("newsUrl","https://mp.weixin.qq.com/s/oSGlBN69fbbjhrfqT6HPaQ");
                startActivity(intent3);
        }
    }


    private class MyImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }

    private void initData() {
        image.clear();
        title.clear();
        image.add(R.drawable.article_1);
        image.add(R.drawable.article_2);
        image.add(R.drawable.article_3);
        title.add("这6个健康准则，你做到了几条？");
        title.add("请收好这份冬春季健康防护提示");
        title.add("这3种竞技运动，不仅锻炼身体，还老少皆宜");

    }
    public void setSearchStr(String query) {
        if (!TextUtils.isEmpty(query))
            mTvSearch.setText("搜索的内容是" + query);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        ImageView Diet = (ImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.diet);
        ImageView Exercise = (ImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.exercise);
        ImageView SolarTerms = (ImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.solarTerms);
        ImageView Habits = (ImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.habits);
        Diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DietSuggest.class);
                startActivity(intent);
            }
        });
        Exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.cognitive.Activity.Exercise.class);
                startActivity(intent);
            }
        });
        SolarTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Sleep.class);
                startActivity(intent);
            }
        });
        Habits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.example.cognitive.Activity.Habit.class);
                startActivity(intent);
            }
        });
    }

}