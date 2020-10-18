package com.example.e_covid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context){
        super(context,"dbecovid",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table data_id (id varchar(100),email varchar(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists data_id");
    }

    public Boolean insert1(String a, String b){
        SQLiteDatabase read = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",a);
        contentValues.put("email",b);
        long ins = read.insert("data_id",null,contentValues);
        if(ins==-1)return false;
        else return true;
    }
    public String id(String a){
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor cursor = read.rawQuery("select * from data_id where id=?",new String[]{a});
        String c = null;
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            c = cursor.getString(1).toString();
            return c;
        }
        else
            return null;
    }


}
