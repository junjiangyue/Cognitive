package com.example.cognitive.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> Lists;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> viewLists) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.Lists = viewLists;
    }
    public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
    @Override
    public int getCount() {
        return Lists.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return Lists.get(position);
    }
}
