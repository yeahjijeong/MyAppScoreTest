package com.example.myappscoretest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static String DB = "student.db";
    public static int VERSION = 1;
    public static  String TABLE = "score";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //디비가 생성 될 때!
        String sql = "create table if not exists "+TABLE
                +"( _id integer primary key autoincrement, name text, kor integer, eng integer, math integer)";
        db.execSQL(sql);
        Log.i("MyTAG", DB+"database 생성");
        Log.i("MyTAG", TABLE+"table 생성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("MyTAG", "onUpgrade: 기존버전: "+oldVersion+", 최신버전: "+newVersion);
        if(newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        }
    }
}
