package com.example.cognitive.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cognitive.Adapter.ContactsAdapter;
import com.example.cognitive.Adapter.HistoryAdapter;
import com.example.cognitive.Bean.Contacts;
import com.example.cognitive.Bean.History;
import com.example.cognitive.R;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lucasurbas.listitemview.ListItemView;

public class TestHistory extends AppCompatActivity {
    String TAG = TestHistory.class.getCanonicalName();
    private ListView listView;
    private SharedPreferences sp;
    private HashMap<String,String> stringHashMap;

    public Handler mhandler;

    private List<History> data;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        //sp=getSharedPreferences("userInfo",MODE_PRIVATE);
        //int userID = sp.getInt("USER_ID", 0);

        Intent getIntent=getIntent();
        String userID=getIntent.getStringExtra("USER_ID");
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//???????????????????????????
        getSupportActionBar().setHomeButtonEnabled(true); //?????????????????????
        stringHashMap=new HashMap<>();
        stringHashMap.put("userid",userID);

        /**
         * ??????????????????
         */
        listView = findViewById(R.id.listview);
        data = new ArrayList<History>();
        mhandler=new mHandler();
        new Thread(postRun).start();
        adapter = new HistoryAdapter(TestHistory.this, data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {// ??????????????????
                Intent intent=new Intent(TestHistory.this,HistoryResult.class);

                intent.putExtra("score",data.get(position).getTestScore());
                intent.putExtra("testdate",data.get(position).getTestDate());
                intent.putExtra("testtime",data.get(position).getTestTime());
                intent.putExtra("testname",data.get(position).getTestName());
                intent.putExtra("strength",data.get(position).getStrengthScore());
                intent.putExtra("health",data.get(position).getHealthScore());
                intent.putExtra("judgement",data.get(position).getJudgementScore());
                intent.putExtra("memory",data.get(position).getMemoryScore());
                intent.putExtra("cognition",data.get(position).getCognitionScore());

                startActivity(intent);
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

    private void requestPost(HashMap<String, String> paramsMap) {
        int code = 20;
        try {
            String baseUrl = "http://101.132.97.43:8080/ServiceTest/servlet/GetHistoryServlet";
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
                            String jsondata = jsonObject.optString("data");
                            JSONArray jsonObject2 = new JSONArray(jsondata);
                            List<String> list2 = new ArrayList<String>();
                            for (int i=0; i<jsonObject2.length(); i++) {
                                list2.add( jsonObject2.getString(i) );
                            }
                            Log.e(TAG,list2.toString());
                            for (int i = 0; i <jsonObject2.length() ; i++) {
                                JSONObject item = jsonObject2.getJSONObject(i);
                                History datas = new History();
                                datas.setCognitionScore(item.getString("cognitionScore"));
                                datas.setHealthScore(item.getString("healthScore"));
                                datas.setTestDate(item.getString("testDate"));
                                datas.setJudgementScore(item.getString("judgementScore"));
                                datas.setTestName(item.getString("testName"));
                                datas.setTestTime(item.getString("testTime"));
                                datas.setTestScore(item.getString("testScore"));
                                datas.setStrengthScore(item.getString("strengthScore"));
                                datas.setMemoryScore(item.getString("memoryScore"));
                                data.add(datas);
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            msg.setData(bundle);//mes??????Bundle????????????
                            mhandler.sendMessage(msg);
                            break;
                        default:
                            Looper.prepare();
                            Toast.makeText(TestHistory.this,"???????????????", Toast.LENGTH_LONG).show();
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
                Toast.makeText(TestHistory.this,"Post??????????????????", Toast.LENGTH_LONG).show();
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

//
    class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

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