package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    EditText email,pass;
    Button login;
    DatabaseHelper db;
    public String url = "http://156.67.221.101:4000/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtemaillogin);
        pass = findViewById(R.id.txtpasslogin);
        login = findViewById(R.id.btnproseslogin);
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        db = new DatabaseHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
                final loginPost loginPost = new loginPost(email.getText().toString(),pass.getText().toString());
                Call<loginPost>call = jsonPlaceHolder.getloginPost(loginPost);
                call.enqueue(new Callback<com.example.e_covid.loginPost>() {
                    @Override
                    public void onResponse(Call<com.example.e_covid.loginPost> call, Response<com.example.e_covid.loginPost> response) {
                        Toast.makeText(getApplicationContext(),"post berhasil",Toast.LENGTH_LONG).show();
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Code : "+response.code(),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        loginPost loginPost1 = response.body();
                        String a = loginPost1.getId();
                        Boolean ins = db.insert1(a,email.getText().toString());
                        if(ins==true){
                            Toast.makeText(getApplicationContext(),"ID : "+a+" berhasil Login",Toast.LENGTH_LONG).show();
                            email.setText("");
                            pass.setText("");
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"LOL",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.e_covid.loginPost> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed POST",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}