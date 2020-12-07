package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class masuk_kelas2 extends AppCompatActivity {
    private TextView indikator;
    DatabaseHelper db;
    String url = "http://156.67.221.101:4000/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_kelas2);
        indikator = findViewById(R.id.txtindikatorkelasoffline);
        db = new DatabaseHelper(this);
        String id = db.ambil_id();
        String matakuliah = getIntent().getStringExtra("matakuliah");
        final absenPost absenPost = new absenPost(id,matakuliah);
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<absenPost> call = jsonPlaceHolder.getabsenPost(absenPost);
        call.enqueue(new Callback<com.example.e_covid.absenPost>() {
            @Override
            public void onResponse(Call<com.example.e_covid.absenPost> call, Response<com.example.e_covid.absenPost> response) {
                absenPost absenPost1 = response.body();
                String l = absenPost1.getLink();
                if(l.equals("tidak tersedia")){
                    indikator.setText(l);
                }
                else{
                    indikator.setText("Selamat Belajar");
                }
            }
            @Override
            public void onFailure(Call<com.example.e_covid.absenPost> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"lost connection",Toast.LENGTH_SHORT).show();
            }
        });

    }
}