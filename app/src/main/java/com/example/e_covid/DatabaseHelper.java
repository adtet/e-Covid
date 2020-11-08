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
        db.execSQL("create table history(day varchar(50),date varchar(50),matakuliah varchar(50),dosen varchar(50),waktukehadiran varchar(50),keterangan varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists data_id");
        db.execSQL("drop table if exists jadwal");
        db.execSQL("drop table if exists history");
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
        cursor.moveToLast();
        if(cursor.getCount()>0){
            c = cursor.getString(0);
            return c;
        }
        else
            return c;
    }

    public void delete_jadwal(){
        SQLiteDatabase  read = this.getWritableDatabase();
        read.execSQL("delete from jadwal");
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

    public Boolean check_jadwal(){
        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from jadwal ",null);
        if(cursor.getCount()>0){
            return false;
        }
        else{
            return true;
        }
    }
    public Boolean check_history(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from history",null);
        if(cursor.getCount()>0) return false;
        else return true;
    }
    public Boolean insert_history(String a,String b,String c,String d,String e,String f){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("day",a);
        contentValues.put("date",b);
        contentValues.put("matakuliah",c);
        contentValues.put("dosen",d);
        contentValues.put("waktukehadiran",e);
        contentValues.put("keterangan",f);
        long ins = db.insert("history",null,contentValues);
        if(ins==-1)return false;
        else return true;
    }
    public void delete_history(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from history");
    }





}
