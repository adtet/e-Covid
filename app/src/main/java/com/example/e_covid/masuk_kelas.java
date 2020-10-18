package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class masuk_kelas extends AppCompatActivity {
    public TextView link;
    DatabaseHelper db;
    String url = "http://156.67.221.101:4000/user/absen/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_kelas);
        link = findViewById(R.id.txtlinkmasukkelas);
        db = new DatabaseHelper(this);
        String id = db.ambil_id();
        final absenPost absenPost= new absenPost(id);
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<absenPost>call = jsonPlaceHolder.getabsenPost(id);
        call.enqueue(new Callback<absenPost>() {
            @Override
            public void onResponse(Call<absenPost> call, Response<absenPost> response) {
                absenPost absenPost = response.body();
                String l = absenPost.getLink();
                link.setText(l);
            }

            @Override
            public void onFailure(Call<absenPost> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}