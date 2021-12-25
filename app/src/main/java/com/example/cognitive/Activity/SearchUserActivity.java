
package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.cognitive.R;

import java.lang.reflect.Field;
import java.util.HashMap;

public class SearchUserActivity extends AppCompatActivity {
    private TextView mTvSearch;
    private SharedPreferences sp;
    private HashMap<String, String> stringHashMap;
    private int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        SearchView mSearchView = (SearchView) findViewById(R.id.sv);
        setUnderLineTransparent(mSearchView);
        mTvSearch = findViewById(R.id.tv_title);
        stringHashMap = new HashMap<>();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_id = sp.getInt("userID",0);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //在文字改变的时候回调，query是改变之后的文字
                setSearchStr(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //文字提交的时候回调，newText是最后提交搜索的文字
                return false;
            }
        });
    }
        private void setUnderLineTransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void setSearchStr(String query) {
        if (!TextUtils.isEmpty(query))
            mTvSearch.setText("搜索的内容是" + query);
    }
}