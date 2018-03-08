package com.example.apple.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.BatchUpdateException;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new MyDatabaseHelper(this,"BookStore.db",null,6);//最后的版本号有关onUPgrade（）
        Button createDatabase=(Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });


        //操作数据库最好还是直接操作比较好

        //下面演示如何用Android给的方法间接往数据库中添加数据
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一个条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.98);
                db.insert("Book", null, values);
                values.clear();//复制完毕，那么就把values清空腾出空间吧
                //开始组装第二条数据
                values.put("name", "The game of iron throne");
                values.put("author", "Martin");
                values.put("pages", "999");
                values.put("price",88.8);
                db.insert("Book", null, values);
            }
        });

        //下面演示如何利用android里面的方法修改数据
        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 9.99);
                db.update("Book", values,"name=?", new String[]{"The game of iron throne"});
            }
        });

        //下面是删除数据库数据
        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
               // db.delete("Book", "pages>?", new String[]{"500"});
                db.execSQL("delete from Book where pages =?",new String[]{"454"});
            }
        });
        
        //下面是最复杂的查询数据操作
        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询BOOK表中所有的数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        //遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.i("MainActivity", "Book name is "+name);
                        Log.i("MainActivity", "Book author is "+author);
                        Log.i("MainActivity", "book pages is "+pages);
                        Log.i("MainActivity", "book price is "+price);
                    }while(cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
