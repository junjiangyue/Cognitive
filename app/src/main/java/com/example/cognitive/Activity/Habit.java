package com.example.cognitive.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cognitive.Adapter.ArticleAdapter;
import com.example.cognitive.Bean.Articles;
import com.example.cognitive.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Habit extends AppCompatActivity {

    /**
     * 新闻列表请求接口
     */
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=top&key=a1a755458cc22f129942b34904feb820";

    /**
     * ListView对象
     */
    private ListView listView;

    /**
     * 新闻集合对象
     */
    private List<Articles> data;

    /**
     * 自定义的Adapter对象
     */
    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        listView = (ListView) this.findViewById(R.id.listview);

        data = new ArrayList<Articles>();

        getData(URL);

        /**
         * 实例化Adapter对象(注意:必须要写在在getData() 方法后面,不然data中没有数据)
         */
        adapter = new ArticleAdapter(Habit.this, data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                /**
                 * 创建一个意图
                 */
                Intent intent = new Intent(Habit.this,ArticleInfo.class);

                /**
                 * 在datas中通过点击的位置position通过get()方法获得具体某个新闻
                 * 的数据然后通过Intent的putExtra()传递到NewsInfoActivity中
                 */
                intent.putExtra("newsTitle", data.get(position).getNewsTitle());
                intent.putExtra("newsDate", data.get(position).getNewsDate());
                intent.putExtra("newsImgUrl", data.get(position).getNewsImgUrl());
                intent.putExtra("newsUrl", data.get(position).getNewsUrl());

                Habit.this.startActivity(intent);//启动Activity

            }
        });

    }
    /**
     * 通过接口获取新闻列表的方法
     * @param url
     */
    public void getData(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(Habit.this);
        Response.Listener myListener = (Response.Listener<JSONObject>) jsonObject -> {

            try {
                /**
                 * 对返回的json数据进行解析,然后装入data集合中
                 */
                JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                JSONArray jsonArray = jsonObject2.getJSONArray("data");

                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    Articles datas = new Articles();
                    datas.setNewsTitle(item.getString("title"));
                    datas.setNewsDate(item.getString("date"));
                    datas.setNewsImgUrl(item.getString("thumbnail_pic_s"));
                    datas.setNewsUrl(item.getString("url"));
                    data.add(datas);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**
             * 请求成功后为ListView设置Adapter
             */
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        };
        @Nullable
        Response.ErrorListener myErrorListener;
        myErrorListener = volleyError -> {

        };
        JsonObjectRequest stringRequest = new JsonObjectRequest(url,myListener, myErrorListener);
        mQueue.add(stringRequest);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}