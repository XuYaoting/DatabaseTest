package com.example.apple.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by apple on 06/03/2018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK="create table Book ("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    public static final String CREATE_CATEGORY="create table Category("
            +"id integer primary key autoincrement,"
            +"category_name text,"
            +"category_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //一旦创建成功，之后就不会再继续执行创建数据库的操作
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
        //这里是先舍弃然后再创建数据库
    }
}
