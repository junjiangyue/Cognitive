package com.example.cognitive.SQLiteDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG="DatabaseHelper";
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate 方法；创建时回调
        Log.d(TAG,"创建数据库");
        //创建字段   这种方法是通过写好sql语句，然后交给execSQL()方法执行，来创建；
        //sql : create table table_name(id integer,GradeUnit integer,Unit integer,English varchar, Chinese varchar)
        //Database.TABLE_NAME = "vocabulary"  是在常量文件中写好的
        String sql ="create table Step (id integer,step_num integer,step_time text)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("drop table if exists Step");
        //onCreate(sqLiteDatabase);
    }
}
