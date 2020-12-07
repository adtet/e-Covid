package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class showJadwal2 extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList timestartList;
    private ArrayList timeendList;
    private ArrayList matakuliahList;
    private ArrayList dosenList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jadwal2);
        db = new DatabaseHelper(this);
        timestartList = new ArrayList<>();
        timeendList = new ArrayList<>();
        matakuliahList = new ArrayList<>();
        dosenList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler3);
        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter3(timestartList,timeendList,matakuliahList,dosenList,this);
        recyclerView.setAdapter(adapter);

    }
    protected void getData(){
        SQLiteDatabase readData = db.getReadableDatabase();
        Cursor cursor = readData.rawQuery("select * from jadwal",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            for(int i = 0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                timestartList.add(cursor.getString(0));
                timeendList.add(cursor.getString(1));
                matakuliahList.add(cursor.getString(2));
                dosenList.add(cursor.getString(3));
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Jadwal Kosong",Toast.LENGTH_LONG).show();
        }

    }
}