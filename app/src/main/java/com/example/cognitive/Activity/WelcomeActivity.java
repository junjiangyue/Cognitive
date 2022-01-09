package com.example.cognitive.Activity;
// 欢迎进入
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cognitive.R;
import com.example.cognitive.Utils.DestroyActivityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
        String TAG = WelcomeActivity.class.getCanonicalName();
        private SharedPreferences sp;
        private ViewPager viewPager;
        private String user_name;
        private String user_birth;
        private String user_sex;
        private String user_phone;
        private HashMap<String, String> stringHashMap;
        //引导图片的资源id
        private int[] mImageIds = new int[]{
                R.drawable.welcom1
        };

        private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();

        private Button btn_start;
        @Override
        public void onCreate(Bundle savedInstanceState){
            DestroyActivityUtil.addActivity(WelcomeActivity.this);
            super.onCreate(savedInstanceState);
            //设置标题栏隐藏
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.activity_welcome);

            initViews();
            //找到ViewPager
            viewPager = (ViewPager) findViewById(R.id.vp_guide);
            //为viewPager设置适配器
            viewPager.setAdapter(new GuideAdapter());
            stringHashMap = new HashMap<>();
            sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
            user_name = sp.getString("USER_NAME", "");
            user_birth = sp.getString("USER_BIRTH", "");
            user_sex = sp.getString("USER_SEX", "");
            user_phone = sp.getString("USER_PHONE", "");
            stringHashMap.put("user_name", user_name);
            stringHashMap.put("user_birth", user_birth);
            stringHashMap.put("user_sex", user_sex);
            stringHashMap.put("user_phone", user_phone);
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
                    // 将用户数据上传到后端
                    new Thread(postRun).start();
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();

                }
            });



        }

    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(stringHashMap);
        }
    };
    /**
     * post提交数据
     *
     * @param paramsMap
     */
    private void requestPost(HashMap<String, String> paramsMap) {
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/UpdateInfoServlet";
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos >0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            Log.e(TAG,"params--post-->>"+params);
            // 请求的参数转换为byte数组
//            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            //配置请求Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post请求不能设置这个
            // 开始连接
            urlConn.connect();

            // 发送请求参数
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post方式请求成功，result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                    }
                    switch (code){
                        case 200 :
                            //插入成功
                            Looper.prepare();
                            Toast.makeText(WelcomeActivity.this,"数据上传成功", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        default:
                            // 插入失败
                            Looper.prepare();
                            Toast.makeText(WelcomeActivity.this,"数据上传失败", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post方式请求失败");
                Toast.makeText(WelcomeActivity.this,"Post方式请求失败，没接上", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
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