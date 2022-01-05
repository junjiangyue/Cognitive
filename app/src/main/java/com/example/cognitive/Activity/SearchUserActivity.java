
package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cognitive.Adapter.ArticleAdapter;
import com.example.cognitive.Adapter.ContactsAdapter;
import com.example.cognitive.Bean.Articles;
import com.example.cognitive.Bean.Contacts;
import com.example.cognitive.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchUserActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private HashMap<String, String> stringHashMap;
    private HashMap<String, String> contactHashMap;
    /**
     * ListView对象
     */
    private ListView listView;
    /**
     * 联系人集合对象
     */
    private List<Contacts> data;
    /**
     * 自定义的Adapter对象
     */
    private ContactsAdapter adapter;

    public Handler mhandler;
    private int contactId;
    private int user_id;

    String TAG = SearchUserActivity.class.getCanonicalName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        SearchView mSearchView = (SearchView) findViewById(R.id.sv);
        setUnderLineTransparent(mSearchView);
        mhandler = new mHandler();
        listView = (ListView) this.findViewById(R.id.listview);
        data = new ArrayList<Contacts>();
        adapter = new ContactsAdapter(SearchUserActivity.this, data);
        stringHashMap = new HashMap<>();
        contactHashMap = new HashMap<>();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_id = sp.getInt("USER_ID",0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                AddContactDialog addContactDialog = new AddContactDialog(SearchUserActivity.this,new AddContactDialog.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        // 像后端发送请求
                        contactHashMap.put("contactID", String.valueOf(contactId));
                        contactHashMap.put("userID", String.valueOf(user_id));
                        new Thread(contactRequest).start();
                    }
                });
                addContactDialog.show();
            }
        });





        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //在文字提交的时候回调，query是提交的文字
                //setSearchStr(query);
                //Toast.makeText(SearchUserActivity.this,query, Toast.LENGTH_LONG).show();
                // 向后端发起搜索请求
                stringHashMap.put("userPhone", query);
                new Thread(postRun).start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //文字改变的时候回调

                return false;
            }
        });
    }
    // 取消下划线
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

    class mHandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            String username = msg.getData().getString("username");//接受msg传递过来的参数
            String usersex = msg.getData().getString("usersex");//接受msg传递过来的参数
            String userbirth = msg.getData().getString("userbirth");//接受msg传递过来的参数
            String userphone = msg.getData().getString("userphone");
            int userid = msg.getData().getInt("userid");
            Contacts datas = new Contacts();
            contactId = userid;
            datas.setUserId(userid);
            datas.setUserBirth(userbirth);
            datas.setUserSex(usersex);
            datas.setUserName(username);
            datas.setUserPhone(userphone);
            data.add(datas);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/SearchUserServlet";
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
                            String data = jsonObject.optString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            int user_id = jsonObject2.optInt("id");
                            String user_name = jsonObject2.optString("username");
                            String user_sex = jsonObject2.optString("usersex");
                            String user_birth = jsonObject2.optString("userbirth");
                            String user_phone = jsonObject2.getString("userphone");
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("username",user_name);  //往Bundle中存放数据
                            bundle.putString("usersex",user_sex);  //往Bundle中put数据
                            bundle.putString("userbirth",user_birth);  //往Bundle中put数据
                            bundle.putString("userphone",user_phone);  //往Bundle中put数据
                            bundle.putInt("userid",user_id);  //往Bundle中put数据
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                            break;
                        default:
                            Looper.prepare();
                            Toast.makeText(SearchUserActivity.this,"搜索失败", Toast.LENGTH_LONG).show();
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
                Toast.makeText(SearchUserActivity.this,"手机号或密码错误，请重新登录", Toast.LENGTH_LONG).show();
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

    Runnable contactRequest = new Runnable() {
        @Override
        public void run() {
            int code = 20;
            try {
                String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/HandleContactTempServlet";
                //合成参数
                StringBuilder tempParams = new StringBuilder();
                int pos = 0;
                for (String key : contactHashMap.keySet()) {
                    if (pos >0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(contactHashMap.get(key), "utf-8")));
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
                                Looper.prepare();
                                Toast.makeText(SearchUserActivity.this,"申请发送成功", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                Intent intent = new Intent(SearchUserActivity.this,ContactsActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                Looper.prepare();
                                Toast.makeText(SearchUserActivity.this,"添加失败", Toast.LENGTH_LONG).show();
                                Intent intent2 = new Intent(SearchUserActivity.this,SearchUserActivity.class);
                                startActivity(intent2);
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
                    Toast.makeText(SearchUserActivity.this,"Post方式请求失败", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                // 关闭连接
                urlConn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    };
}