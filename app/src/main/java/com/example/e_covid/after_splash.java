package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class after_splash extends AppCompatActivity {
    public String url = "http://156.67.221.101:4000/user/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList daydateList;
    private ArrayList matakuliahList;
    private ArrayList dosenList;
    private ArrayList waktukehadiranList;
    private ArrayList keteranganList;
    DatabaseHelper db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_splash);
        daydateList = new ArrayList<>();
        matakuliahList = new ArrayList<>();
        dosenList = new ArrayList<>();
        waktukehadiranList = new ArrayList<>();
        keteranganList = new ArrayList<>();
        db = new DatabaseHelper(this);
        String id = db.ambil_id();
        download(id);

    }
    public void download(final String a){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        db = new DatabaseHelper(this);
        final getCekdata getCekdata = new getCekdata(a);
        Call<List<getCekdata>>call = jsonPlaceHolder.getCekdata(getCekdata);
        Boolean cek = db.check_history();
        if(cek==true){
            call.enqueue(new Callback<List<com.example.e_covid.getCekdata>>() {
                @Override
                public void onResponse(Call<List<com.example.e_covid.getCekdata>> call, Response<List<com.example.e_covid.getCekdata>> response) {
                    List<getCekdata>getCekdatas = response.body();
                    if(getCekdatas.isEmpty()){
                        Toast.makeText(getApplicationContext(),"No history",Toast.LENGTH_SHORT).show();
                        ShowData();
                        onBackPressed();
                    }
                    else{
                        for(getCekdata getCekdata:getCekdatas){
                            String a = null;
                            String b = null;
                            String c = null;
                            String d = null;
                            String e = null;
                            String f = null;
                            a = getCekdata.getDay();
                            b = getCekdata.getDate();
                            c = getCekdata.getMatakuliah();
                            d = getCekdata.getDosen();
                            e = getCekdata.getTime();
                            f = getCekdata.getInfo();
                            db.insert_history(a,b,c,d,e,f);
                        }
                        ShowData();
                    }

                }

                @Override
                public void onFailure(Call<List<com.example.e_covid.getCekdata>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Lost Connection",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            db.delete_history();
            call.enqueue(new Callback<List<com.example.e_covid.getCekdata>>() {
                @Override
                public void onResponse(Call<List<com.example.e_covid.getCekdata>> call, Response<List<com.example.e_covid.getCekdata>> response) {
                    List<getCekdata>getCekdatas = response.body();
                    if(getCekdatas.isEmpty()){
                        Toast.makeText(getApplicationContext(),"No history",Toast.LENGTH_SHORT).show();
                        ShowData();
                        onBackPressed();
                    }
                    else{
                        for(getCekdata getCekdata:getCekdatas){
                            String a = null;
                            String b = null;
                            String c = null;
                            String d = null;
                            String e = null;
                            String f = null;
                            a = getCekdata.getDay();
                            b = getCekdata.getDate();
                            c = getCekdata.getMatakuliah();
                            d = getCekdata.getDosen();
                            e = getCekdata.getTime();
                            f = getCekdata.getInfo();
                            db.insert_history(a,b,c,d,e,f);
                        }
                        ShowData();
                    }

                }

                @Override
                public void onFailure(Call<List<com.example.e_covid.getCekdata>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Lost Connection",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void getData(){
        SQLiteDatabase read = db.getReadableDatabase();
        Cursor cursor = read.rawQuery("select * from history",null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            for(int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                daydateList.add(cursor.getString(0)+","+cursor.getString(1));
                matakuliahList.add(cursor.getString(2));
                dosenList.add(cursor.getString(3));
                waktukehadiranList.add(cursor.getString(4));
                keteranganList.add(cursor.getString(5));
            }
        }
        else Toast.makeText(getApplicationContext(),"Data kosong",Toast.LENGTH_SHORT).show();

    }
    public void ShowData(){
        daydateList = new ArrayList<>();
        matakuliahList = new ArrayList<>();
        dosenList = new ArrayList<>();
        waktukehadiranList = new ArrayList<>();
        keteranganList = new ArrayList<>();
        db = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler2);
        getData();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter2(daydateList,matakuliahList,dosenList,waktukehadiranList,keteranganList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}