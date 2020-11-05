package com.example.e_covid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context){
        super(context,"dbecovid",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table data_id (id varchar(100),email varchar(100),kelas varchar(100))");
        db.execSQL("create table jadwal (timestart varchar(10),timeend varchar(10),matakuliah varchar(50),dosen varchar(50))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists data_id");
        db.execSQL("drop table if exists jadwal");
    }

    public Boolean insert1(String a, String b,String c){
        SQLiteDatabase read = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",a);
        contentValues.put("email",b);
        contentValues.put("kelas",c);
        long ins = read.insert("data_id",null,contentValues);
        if(ins==-1)return false;
        else return true;
    }
    public String ambil_id(){
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor cursor = read.rawQuery("select id from data_id" ,null);
        String c = null;
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            c = cursor.getString(0);
            return c;
        }
        else
            return c;
    }
    public Integer delete(String a){
        SQLiteDatabase read = this.getReadableDatabase();
        return read.delete("data_id","id=?",new String[]{a});
    }
    public Boolean insert_jadwal(String a, String b,String c,String d){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timestart",a);
        contentValues.put("timeend",b);
        contentValues.put("matakuliah",c);
        contentValues.put("dosen",d);
        long ins = db.insert("jadwal",null,contentValues);
        if(ins==-1)return  false;
        else return true;
    }
    public Integer delete_jadwal(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("jadwal",null,null);
    }
    public Boolean check_jadwal(String a,String b){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from jadwal where matakuliah=? and dosen=?",new String[]{a,b});
        if(cursor.getCount()>0){
            return false;
        }
        else{
            return true;
        }


    }





}
