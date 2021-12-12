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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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
    //@Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_fragment2,container,false);
        banner = view.findViewById(R.id.banner);
        initData();
        initView();
//        SearchView mSearchView = (SearchView) view.findViewById(R.id.sv);
//        setUnderLineTransparent(mSearchView);
//        mTvSearch = view.findViewById(R.id.tv_title);
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //在文字改变的时候回调，query是改变之后的文字
//                setSearchStr(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //文字提交的时候回调，newText是最后提交搜索的文字
//                return false;
//            }
//        });
        return view;
    }
//    private void setUnderLineTransparent(SearchView searchView){
//        try {
//            Class<?> argClass = searchView.getClass();
//            // mSearchPlate是SearchView父布局的名字
//            Field ownField = argClass.getDeclaredField("mSearchPlate");
//            ownField.setAccessible(true);
//            View mView = (View) ownField.get(searchView);
//            mView.setBackgroundColor(Color.TRANSPARENT);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        //title_test=view.findViewById(R.id.title_test);
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
        Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
        switch (position){
            case 0 :
                Intent intent = new Intent(getActivity(),DietSuggest.class);
                startActivity(intent);break;
            case 1 :
                Intent intent2 = new Intent(getActivity(), com.example.cognitive.Activity.Exercise.class);
                startActivity(intent2);
            case 2:
                Intent intent3 = new Intent(getActivity(), com.example.cognitive.Activity.Habit.class);
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
        image.add(R.drawable.lidong);
        image.add(R.drawable.xiaoxue);
        image.add(R.drawable.three_photo);
        title.add("细雨生寒未有霜，庭前木叶半青黄。小春此去无多日，何处梅花一绽香。");
        title.add("冬腊风腌，蓄以御冬");
        title.add("冬至已到，锻炼时须加倍防范运动损伤");

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