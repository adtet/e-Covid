package com.example.e_covid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context){
        super(context,"dbecovid",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbregis(id varchar(50),nim varchar(10),username varchar(50),jurusan varchar(50),prodi varchar(50),kelas varchar(50),email varchar(50),pass varchar(50))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tbregis");
    }

    public boolean insertregis(String a,String b,String c,String d,String e,String f,String g,String h){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",a);
        contentValues.put("nim",b);
        contentValues.put("username",c);
        contentValues.put("jurusan",d);
        contentValues.put("prodi",e);
        contentValues.put("kelas",f);
        contentValues.put("email",g);
        contentValues.put("pass",h);
        long ins = db.insert("tbregis",null,contentValues);
        if(ins==-1)return false;
        else return true;
    }
}
