package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cognitive.Adapter.ContactsAdapter;
import com.example.cognitive.Bean.Articles;
import com.example.cognitive.Bean.Contacts;
import com.example.cognitive.R;
import com.lucasurbas.listitemview.ListItemView;

import org.json.JSONArray;
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

public class ContactsActivity extends AppCompatActivity {
    private ListItemView addContact;
    private ListView contactList;
    private ListView newApply;

    private List<Contacts> apply;
    private ContactsAdapter applyadapter;

    private List<Contacts> data;
    private ContactsAdapter adapter;

    String TAG = SearchUserActivity.class.getCanonicalName();
    private HashMap<String, String> stringHashMap;
    private HashMap<String, String> confirmHashMap;
    private SharedPreferences sp;
    private int user_id;

    public Handler mhandler;
    public Handler mhandler2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //添加联系人按钮
        addContact = findViewById(R.id.add_contact);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, SearchUserActivity.class);
                startActivity(intent);
            }
        });
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        stringHashMap = new HashMap<>();
        confirmHashMap = new HashMap<>();
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_id = sp.getInt("USER_ID",0);
        stringHashMap.put("userID", String.valueOf(user_id));

        /* 展示联系人列表*/
        contactList = findViewById(R.id.contacts);
        data = new ArrayList<Contacts>();
        mhandler = new mHandler();
        // 从后端获取数据
        new Thread(postRun).start();
        // 展示获取的数据
        //Log.e(TAG,data.toString());
        adapter = new ContactsAdapter(ContactsActivity.this, data);
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击进入他人主页
                Intent intent = new Intent(ContactsActivity.this,AnotherPersonpage.class);
                intent.putExtra("userID",String.valueOf(data.get(position).getUserId()));
                intent.putExtra("user_name",data.get(position).getUserName());
                intent.putExtra("user_sex",data.get(position).getUserSex());
                intent.putExtra("user_birth",data.get(position).getUserBirth());
                startActivity(intent);
            }
        });

        /* 展示新联系人申请*/
        newApply = findViewById(R.id.newApply);
        apply =  new ArrayList<Contacts>();
        mhandler2 = new mHandler();
        // 从后端获取数据
        new Thread(requestPost).start();
        applyadapter= new ContactsAdapter(ContactsActivity.this, apply);
        newApply.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                AddContactDialog addContactDialog = new AddContactDialog(ContactsActivity.this,new AddContactDialog.DataBackListener() {
                    @Override
                    public void getData(int data) {
                        // 像后端发送请求
                        if(data == 1){
                            confirmHashMap.put("contactID",String.valueOf(apply.get(position).getUserId()));
                            confirmHashMap.put("userID", String.valueOf(user_id));
                            confirmHashMap.put("confirmState","accept");
                            new Thread(postApply).start();
                        }else if(data == 0){
                            confirmHashMap.put("contactID",String.valueOf(apply.get(position).getUserId()));
                            confirmHashMap.put("userID", String.valueOf(user_id));
                            confirmHashMap.put("confirmState","refuse");
                            new Thread(postApply).start();
                        }

                    }
                });
                addContactDialog.show();
            }
        });

    }

    class mHandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            contactList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            newApply.setAdapter(applyadapter);
            applyadapter.notifyDataSetChanged();
        }
    }
    Runnable postApply = new Runnable() {
        @Override
        public void run() {
            int code = 20;
            try {
                String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/ResolveApplyServlet";
                //合成参数
                StringBuilder tempParams = new StringBuilder();
                int pos = 0;
                for (String key : confirmHashMap.keySet()) {
                    if (pos >0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(confirmHashMap.get(key), "utf-8")));
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
                                Intent intent = new Intent(ContactsActivity.this,ContactsActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                Looper.prepare();
                                Toast.makeText(ContactsActivity.this,"出错啦", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ContactsActivity.this,"出错啦", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                // 关闭连接
                urlConn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

        }
    };
    Runnable requestPost = new Runnable() {
        @Override
        public void run() {
            int code = 20;
            try {
                String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/CheckNewContactsServlet";
                //合成参数
                StringBuilder tempParams = new StringBuilder();
                int pos = 0;
                for (String key : stringHashMap.keySet()) {
                    if (pos >0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(stringHashMap.get(key), "utf-8")));
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
                                String jsondata = jsonObject.optString("data");
                                JSONArray jsonObject2 = new JSONArray(jsondata);
                                List<String> list = new ArrayList<String>();
                                for (int i=0; i<jsonObject2.length(); i++) {
                                    list.add( jsonObject2.getString(i) );
                                }
                                Log.e(TAG,list.toString());
                                for (int i = 0; i <jsonObject2.length() ; i++) {
                                    JSONObject item = jsonObject2.getJSONObject(i);
                                    Contacts datas = new Contacts();
                                    datas.setUserPhone(item.getString("userPhone"));
                                    datas.setUserName(item.getString("userName"));
                                    datas.setUserBirth(item.getString("userBirth"));
                                    datas.setUserId(item.getInt("userId"));
                                    datas.setUserSex(item.getString("userSex"));
                                    apply.add(datas);
                                }
                                Message msg = Message.obtain();
                                msg.what = 1;
                                Bundle bundle = new Bundle();
                                msg.setData(bundle);//mes利用Bundle传递数据
                                mhandler2.sendMessage(msg);
                                break;
                            default:
                                Looper.prepare();
                                Toast.makeText(ContactsActivity.this,"搜索失败", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ContactsActivity.this,"手机号或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                // 关闭连接
                urlConn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    };
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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetContactsListServlet";
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
                            String jsondata = jsonObject.optString("data");
                            JSONArray jsonObject2 = new JSONArray(jsondata);
                            List<String> list = new ArrayList<String>();
                            for (int i=0; i<jsonObject2.length(); i++) {
                                list.add( jsonObject2.getString(i) );
                            }
                            Log.e(TAG,list.toString());
                            for (int i = 0; i <jsonObject2.length() ; i++) {
                                JSONObject item = jsonObject2.getJSONObject(i);
                                Contacts datas = new Contacts();
                                datas.setUserPhone(item.getString("userPhone"));
                                datas.setUserName(item.getString("userName"));
                                datas.setUserBirth(item.getString("userBirth"));
                                datas.setUserId(item.getInt("userId"));
                                datas.setUserSex(item.getString("userSex"));
                                data.add(datas);
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);
                            break;
                        default:
                            Looper.prepare();
                            Toast.makeText(ContactsActivity.this,"搜索失败", Toast.LENGTH_LONG).show();
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
                Toast.makeText(ContactsActivity.this,"手机号或密码错误，请重新登录", Toast.LENGTH_LONG).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}