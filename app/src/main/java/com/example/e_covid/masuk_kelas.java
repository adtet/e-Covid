package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class masuk_kelas extends AppCompatActivity {
    public TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_kelas);
        link = findViewById(R.id.txtlinkmasukkelas);
    }
}