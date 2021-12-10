package com.example.cognitive.Activity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cognitive.SQLiteDB.DatabaseHelper;

public class ZeroReceiver extends BroadcastReceiver {
    private DatabaseHelper dbHelper;
    private int step;
    private String TAG="ZeroReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        /*String action= intent.getAction();
        step=intent.getIntExtra("intStep",0);
        if(action=="zero_store_step"){
            Log.d(TAG,"接受0点广播"+step);
            dbHelper=new DatabaseHelper(context,"database1",null,1);
            dbHelper.getWritableDatabase();
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor cursor= db.query("Step",null,null,null,null,null,null);
            ContentValues values=new ContentValues();
            values.put("yesterday_step",step);
            db.update("Step",values,"id=?", new String[] {"1"});
            values.clear();
            Log.d(TAG,"更新");
            if(cursor.moveToFirst()){
                do{
                    int id=cursor.getInt(cursor.getColumnIndex("id"));
                    int yestStep=cursor.getInt(cursor.getColumnIndex("yesterday_step"));
                    int shutStep=cursor.getInt(cursor.getColumnIndex("shutdown_step"));
                    Log.d(TAG,"id:"+id);
                    Log.d(TAG,"yesterday_step:"+yestStep);
                    Log.d(TAG,"shutdown_step:"+shutStep);
                    Log.d(TAG,"更新后数据");
                } while (cursor.moveToNext());
            }
            cursor.close();

        }*/

    }
}
