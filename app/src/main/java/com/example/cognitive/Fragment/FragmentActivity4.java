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
import com.example.cognitive.Activity.WeeklyReportHistory;
import com.example.cognitive.NewbieGuide;
import com.example.cognitive.R;
import com.example.cognitive.Utils.DestroyActivityUtil;
import com.example.cognitive.model.GuidePage;
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

        NewbieGuide.with(getActivity())
                .setLabel("guide1")
                //.alwaysShow(true)//????????????????????????????????????
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(family)
                        //.addHighLight(new RectF(0, 800, 200, 1200))
                        .setLayoutRes(R.layout.layout_hint2))
                .show();

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
                sp=getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                int userID = sp.getInt("USER_ID", 0);
                intent.putExtra("USER_ID",String.valueOf(userID));
                startActivity(intent);
            }
        });
        longtime_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeeklyReportHistory.class);
                //intent.putExtra("data", "mainActivity");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		//???DengLuActivity????????????
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

        // ????????????handlerMessage() ??????????????????UI?????????
        @Override
        public void handleMessage(Message msg) {
            String username = msg.getData().getString("username");//??????msg?????????????????????
            String usersex = msg.getData().getString("usersex");//??????msg?????????????????????
            String userbirth = msg.getData().getString("userbirth");//??????msg?????????????????????
            name.setText(username);
            sex.setText(usersex);
            age.setText(userbirth);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("USER_NAME", username);
            editor.putString("USER_SEX", usersex);
            editor.putString("USER_BIRTH", userbirth);
            editor.commit();
            //Log.i("lgq","..ab ==7....11......"+username);
            // ??????UI??????

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
     * post????????????
     *
     * @param paramsMap
     */
    private void requestPost(HashMap<String, String> paramsMap) {
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetInfoServlet";
            //????????????
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
            // ????????????????????????byte??????
//            byte[] postData = params.getBytes();
            // ????????????URL??????
            URL url = new URL(baseUrl);
            // ????????????HttpURLConnection??????
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // ????????????????????????
            urlConn.setConnectTimeout(5 * 1000);
            //?????????????????????????????????
            urlConn.setReadTimeout(5 * 1000);
            // Post?????????????????????????????? ??????false
            urlConn.setDoOutput(true);
            //???????????????????????? ?????????true
            urlConn.setDoInput(true);
            // Post????????????????????????
            urlConn.setUseCaches(false);
            // ?????????Post??????
            urlConn.setRequestMethod("POST");
            //?????????????????????????????????????????????
            urlConn.setInstanceFollowRedirects(true);
            //????????????Content-Type
//            urlConn.setRequestProperty("Content-Type", "application/json");//post????????????????????????
            // ????????????
            urlConn.connect();

            // ??????????????????
            PrintWriter dos = new PrintWriter(urlConn.getOutputStream());
            dos.write(params);
            dos.flush();
            dos.close();
            // ????????????????????????
            if (urlConn.getResponseCode() == 200) {
                // ?????????????????????
                String result = streamToString(urlConn.getInputStream());
                Log.e(TAG, "Post?????????????????????result--->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null) {
                        code = jsonObject.optInt("code");
                    }
                    switch (code){
                        case 200 :
                            //??????????????????
                            String data = jsonObject.optString("data");
                            JSONObject jsonObject2 = new JSONObject(data);
                            user_name = jsonObject2.optString("username");
                            user_sex = jsonObject2.optString("usersex");
                            user_birth = jsonObject2.optString("userbirth");
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putString("username",user_name);  //???Bundle???????????????
                            bundle.putString("usersex",user_sex);  //???Bundle???put??????
                            bundle.putString("userbirth",user_birth);  //???Bundle???put??????
                            msg.setData(bundle);//mes??????Bundle????????????
                            mhandler.sendMessage(msg);//???activity??????handler????????????
                            break;
                        default:
                            Looper.prepare();
                            Toast.makeText(getActivity(),"???????????????", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            } else {
                Looper.prepare();
                Log.e(TAG, "Post??????????????????");
                Toast.makeText(getActivity(),"Post??????????????????", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            // ????????????
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    /**
     * ??????????????????????????????
     *
     * @param is ???????????????????????????
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