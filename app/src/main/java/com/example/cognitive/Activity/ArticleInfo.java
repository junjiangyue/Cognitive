package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.cognitive.R;

public class ArticleInfo extends AppCompatActivity {
    //private ImageView ivImg;
    //private TextView tvTitle;
    //private TextView tvDate;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_info);
        initViews();
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }
    /**
     * 初始化数据
     */
    public void initViews(){
        //ivImg = (ImageView) this.findViewById(R.id.iv_img);
        //tvTitle = (TextView) this.findViewById(R.id.tv_title);
        //tvDate = (TextView) this.findViewById(R.id.tv_date);
        webView = (WebView) this.findViewById(R.id.wv_content);

        /**
         * 获得传递过来的数据
         */
        Intent intent = this.getIntent();
        String newsTitle = intent.getStringExtra("newsTitle");
        String newsDate = intent.getStringExtra("newsDate");
        String newsImgUrl = intent.getStringExtra("newsImgUrl");
        String newsUrl = intent.getStringExtra("newsUrl");


        //getImage(this, newsImgUrl, ivImg);

        /**
         * 显示新闻信息
         */
        //tvTitle.setText(newsTitle);
        //tvDate.setText(newsDate);

        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 设置缓存
        webSettings.setJavaScriptEnabled(true);//设置能够解析Javascript
        webSettings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置

        //webView  加载视频，下面五行必须
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        webView.loadUrl(newsUrl);
    }

    /**
     * 加载网络图片
     */
    public void getImage(Context context, String imgUrl,
                         final ImageView imageView) {

        RequestQueue mQueue = Volley.newRequestQueue(context);

        Response.Listener<Bitmap> myResponseListener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        };
        Response.ErrorListener myErrorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
        ImageRequest imageRequest = new ImageRequest(imgUrl,myResponseListener,0,0, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565,myErrorListener);
        mQueue.add(imageRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}