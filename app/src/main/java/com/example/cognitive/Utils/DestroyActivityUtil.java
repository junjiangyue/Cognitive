package com.example.cognitive.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cognitive.Activity.LoginActivity;

import java.util.LinkedList;
import java.util.List;

/**
 *      退出程序时，销毁所有Activity
 */
public class DestroyActivityUtil {
    public static List<Activity> activityList = new LinkedList();       //将要销毁的Activity事先存在这个集合中
    //添加activity到容器中
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void exit()              //  启动退出程序的按钮时，调用该方法，遍历一遍集合，销毁所有的Activity
    {
        for(Activity act:activityList)
        {
            Log.d("TAGS",act.toString());
            SharedPreferences preferences = act.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            SharedPreferences preferences2 = act.getSharedPreferences("congfig", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = preferences2.edit();
            editor2.clear();
            editor2.commit();
            act.finish();

        }
        System.exit(0);
    }
}

