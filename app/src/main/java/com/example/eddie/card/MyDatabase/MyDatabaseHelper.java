package com.example.eddie.card.MyDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_TABLE_A = "CREATE TABLE IF NOT EXISTS info(" +
            "id integer primary key autoincrement," +
            "name text," +
            "array text)";

    private static final String CREATE_TABLE_B = "CREATE TABLE IF NOT EXISTS community(" +
            "id integer primary key autoincrement," +
            "name text)";

    private static final String CREATE_TABLE_C = "CREATE TABLE IF NOT EXISTS unit(" +
            "id integer primary key autoincrement," +
            "name text," +
            "unit text," +
            "number text)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_A);
        sqLiteDatabase.execSQL(CREATE_TABLE_B);
        sqLiteDatabase.execSQL(CREATE_TABLE_C);
        Toast.makeText(context, "create success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        
    }
}
