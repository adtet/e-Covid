package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accessibilityservice.FingerprintGestureController;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;

import javax.crypto.Cipher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private String url1 = "http://156.67.221.101:4000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url1).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<welcomeGet>call = jsonPlaceHolder.getWelcome();
        call.enqueue(new Callback<welcomeGet>() {
            @Override
            public void onResponse(Call<welcomeGet> call, Response<welcomeGet> response) {
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,login.class));
                onBackPressed();
            }
            @Override
            public void onFailure(Call<welcomeGet> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lost Connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}