package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class alternatif_fingerprint extends AppCompatActivity {
    ImageButton confirm;
    EditText email,pass;
    private String url = "http://156.67.221.101:4000/user/";
    private String url1 = "http://156.67.221.101:4000/";
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternatif_fingerprint);
        confirm = findViewById(R.id.btnconfimralternatif);
        email = findViewById(R.id.emailalternatif);
        pass = findViewById(R.id.passalternatif);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")&&pass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Lengkapi data",Toast.LENGTH_SHORT).show();
                }
                else{
                    masuk(email.getText().toString(),pass.getText().toString());
                    email.setText("");
                    pass.setText("");
                    onBackPressed();
                }
            }
        });
    }
    public void masuk(final String a,final String b){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        final loginPost loginPost = new loginPost(a,b);
        Call<loginPost>call = jsonPlaceHolder.getloginPost(loginPost);
        call.enqueue(new Callback<com.example.e_covid.loginPost>() {
            @Override
            public void onResponse(Call<com.example.e_covid.loginPost> call, Response<com.example.e_covid.loginPost> response) {
                loginPost loginPost1 = response.body();
                String data = loginPost1.getId();
                get_jadwal(data);
            }
            @Override
            public void onFailure(Call<com.example.e_covid.loginPost> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"lost connection",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void get_jadwal(String a){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url1).addConverterFactory(GsonConverterFactory.create()).build();
        db = new DatabaseHelper(this);
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        final jadwalPost jadwalPost = new jadwalPost(a);
        Call<List<jadwalGet>> call = jsonPlaceHolder.getjadwalGet(jadwalPost);
        Boolean check = db.check_jadwal();
        if(check==true){
            call.enqueue(new Callback<List<jadwalGet>>() {
                @Override
                public void onResponse(Call<List<jadwalGet>> call, Response<List<jadwalGet>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Code : "+response.code(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<jadwalGet> jadwalGets = response.body();
                    if (jadwalGets.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Jadwal Kosong",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                    else{
                        for(jadwalGet jadwalGet:jadwalGets){
                            String a = null;
                            String b = null;
                            String c = null;
                            String d = null;
                            String e = null;
                            String f = null;
                            a = jadwalGet.getJamstart();
                            b = jadwalGet.getMenitstart();
                            c = jadwalGet.getJamend();
                            d = jadwalGet.getMenitend();
                            e = jadwalGet.getMatakuliah();
                            f = jadwalGet.getDosen();
                            String timestart = a+":"+b;
                            String timeend = c+":"+d;
                            db.insert_jadwal(timestart,timeend,e,f);
                        }
//                        Toast.makeText(context,"download jadwal berhasil",Toast.LENGTH_SHORT).show();
                        String pesan = getIntent().getStringExtra("pesan");

                        startActivity(new Intent(alternatif_fingerprint.this,showJadwal.class));
                        onBackPressed();
                    }
                }
                @Override
                public void onFailure(Call<List<jadwalGet>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"download failed",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            db.delete_jadwal();
            call.enqueue(new Callback<List<jadwalGet>>() {
                @Override
                public void onResponse(Call<List<jadwalGet>> call, Response<List<jadwalGet>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Code : "+response.code(),Toast.LENGTH_LONG).show();
                        return;
                    }
                    List<jadwalGet> jadwalGets = response.body();
                    if (jadwalGets.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Jadwal Kosong",Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                    else{
                        for(jadwalGet jadwalGet:jadwalGets){
                            String a = null;
                            String b = null;
                            String c = null;
                            String d = null;
                            String e = null;
                            String f = null;
                            a = jadwalGet.getJamstart();
                            b = jadwalGet.getMenitstart();
                            c = jadwalGet.getJamend();
                            d = jadwalGet.getMenitend();
                            e = jadwalGet.getMatakuliah();
                            f = jadwalGet.getDosen();
                            String timestart = a+":"+b;
                            String timeend = c+":"+d;
                            db.insert_jadwal(timestart,timeend,e,f);
                        }
//                        Toast.makeText(context,"download jadwal berhasil 1",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(alternatif_fingerprint.this,showJadwal.class));
                        onBackPressed();
                    }
                }
                @Override
                public void onFailure(Call<List<jadwalGet>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"download failed",Toast.LENGTH_LONG).show();
                }
            });
        }


    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}