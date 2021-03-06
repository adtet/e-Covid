package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    EditText email,pass;
    ImageButton login;
    DatabaseHelper db;
    ProgressBar progressBar;
    TextView regist;
    public String url = "http://156.67.221.101:4000/user/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progress);
        email = findViewById(R.id.txtemaillogin);
        pass = findViewById(R.id.txtpasslogin);
        login = findViewById(R.id.btnproseslogin);
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        db = new DatabaseHelper(this);
        regist = findViewById(R.id.btnsignupherelogin);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,register.class));
                onBackPressed();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("")&&pass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Lengkapi data anda",Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
                    final loginPost loginPost = new loginPost(email.getText().toString(),pass.getText().toString());
                    Call<loginPost>call = jsonPlaceHolder.getloginPost(loginPost);
                    call.enqueue(new Callback<com.example.e_covid.loginPost>() {
                        @Override
                        public void onResponse(Call<com.example.e_covid.loginPost> call, Response<com.example.e_covid.loginPost> response) {

//                        Toast.makeText(getApplicationContext(),"post berhasil",Toast.LENGTH_LONG).show();
                            if(!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Code : "+response.code(),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            loginPost loginPost1 = response.body();
                            String a = loginPost1.getId();
                            String b = loginPost1.getKelas();
                            if (a==null){
                                Toast.makeText(getApplicationContext(),"anda belum terdaftar",Toast.LENGTH_LONG).show();
                                email.setText("");
                                pass.setText("");
                            }
                            else{
                                Boolean check_data1 = db.check_user();
                                if (check_data1==true){
                                    Boolean check_data2 = db.check_data_id(a,email.getText().toString(),b);
                                    if(check_data2==true){
                                        email.setText("");
                                        pass.setText("");
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(login.this,mini_menu.class));
                                        finish();
                                    }
                                    else{
                                        email.setText("");
                                        pass.setText("");
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),"Hanya dapat login dengan 1 data user",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Boolean ins = db.insert1(a,email.getText().toString(),b);
                                    if(ins==true){
                                        email.setText("");
                                        pass.setText("");
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(login.this,mini_menu.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"LOL",Toast.LENGTH_LONG).show();
                                    }
                                }

                            }

                        }
                        @Override
                        public void onFailure(Call<com.example.e_covid.loginPost> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Failed POST",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                }

        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}