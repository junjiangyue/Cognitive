package com.example.cognitive.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cognitive.Activity.AccountSecurity;
import com.example.cognitive.Activity.ContactsActivity;
import com.example.cognitive.Activity.Exercise;
import com.example.cognitive.Activity.FrailIntro;
import com.example.cognitive.Activity.LoginActivity;
import com.example.cognitive.Activity.MainActivity;
import com.example.cognitive.Activity.PersonalSetting;
import com.example.cognitive.Activity.Sleep;
import com.example.cognitive.Activity.TestHistory;
import com.example.cognitive.R;
import com.example.cognitive.Utils.DestroyActivityUtil;
import com.lucasurbas.listitemview.ListItemView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class FragmentActivity4 extends Fragment {

    public ListItemView logout;
    public ListItemView family;
    public ListItemView personal;
    public ListItemView history_test;
    public ListItemView longtime_report;
    public ListItemView safe;
    private SharedPreferences sp;
    public TextView name;
    public TextView age;
    public TextView sex;
    private HashMap<String, String> stringHashMap;
    private String user_phone;
    private String user_name;
    private String user_sex;
    private String user_birth;
    public Handler mhandler;
    String TAG = MainActivity.class.getCanonicalName();
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_fragment4,container,false);
        logout = view.findViewById(R.id.logout);
        family = view.findViewById(R.id.family);
        personal = view.findViewById(R.id.personal);
        history_test = view.findViewById(R.id.history_test);
        longtime_report = view.findViewById(R.id.longtime_report);
        safe = view.findViewById(R.id.safe);
        name = (TextView)view.findViewById(R.id.user_name);
        sex = (TextView)view.findViewById(R.id.user_sex);
        age = (TextView)view.findViewById(R.id.user_age);

        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user_phone = sp.getString("USER_PHONE", "");
        user_name = sp.getString("USER_NAME", "");
        user_sex = sp.getString("USER_SEX", "");
        user_birth = sp.getString("USER_BIRTH", "");
        stringHashMap = new HashMap<>();
        stringHashMap.put("userphone", user_phone);

        mhandler = new mHandler();

        if(user_name!=""){
            name.setText(user_name);
            sex.setText(user_sex);
            age.setText(user_birth);
        } else new Thread(postRun).start();
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactsActivity.class);
                startActivity(intent);
            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PersonalSetting.class);
                startActivity(intent);
            }
        });
        history_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TestHistory.class);
//                intent.putExtra("data", "mainActivity");
                startActivity(intent);
            }
        });
        longtime_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Exercise.class);
                //intent.putExtra("data", "mainActivity");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		//将DengLuActivity至于栈顶
                startActivity(intent);
                DestroyActivityUtil destroyActivityUtil = new DestroyActivityUtil();
                destroyActivityUtil.exit();

            }
        });
        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountSecurity.class);
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

    class mHandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            String username = msg.getData().getString("username");//接受msg传递过来的参数
            String usersex = msg.getData().getString("usersex");//接受msg传递过来的参数
            String userbirth = msg.getData().getString("userbirth");//接受msg传递过来的参数
            name.setText(username);
            sex.setText(usersex);
            age.setText(userbirth);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("USER_NAME", username);
            editor.putString("USER_SEX", usersex);
            editor.putString("USER_BIRTH", userbirth);
            editor.commit();
            //Log.i("lgq","..ab ==7....11......"+username);
            // 执行UI操作

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
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetInfoServlet";
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
                            //获取用户信息
                            String data = jsonObject.optString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            user_name = jsonObject2.optString("username");
                            user_sex = jsonObject2.optString("usersex");
                            user_birth = jsonObject2.optString("userbirth");
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("username",user_name);  //往Bundle中存放数据
                            bundle.putString("usersex",user_sex);  //往Bundle中put数据
                            bundle.putString("userbirth",user_birth);  //往Bundle中put数据
                            msg.setData(bundle);//mes利用Bundle传递数据
                            mhandler.sendMessage(msg);//用activity中的handler发送消息
                            break;
                        default:
                            Looper.prepare();
                            Toast.makeText(getActivity(),"数据库错误", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getActivity(),"Post方式请求失败", Toast.LENGTH_LONG).show();
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
}