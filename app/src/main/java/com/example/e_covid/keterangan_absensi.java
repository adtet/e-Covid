package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class keterangan_absensi extends AppCompatActivity {
    ImageButton hadir,izin,sakit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_absensi);
        sakit = findViewById(R.id.btnsakitKeterangan);
        izin = findViewById(R.id.btnizinKeterangan);

        sakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(keterangan_absensi.this,fingerPrintAuth3.class));
                onBackPressed();
            }
        });
        izin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(keterangan_absensi.this,fingerPrintAuth3.class));
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}