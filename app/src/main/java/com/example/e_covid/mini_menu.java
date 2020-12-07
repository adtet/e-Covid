package com.example.e_covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class mini_menu extends AppCompatActivity {
    ImageButton online,offline,cekdata,absen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_menu);
        online = findViewById(R.id.btnonlinemenu);
        offline = findViewById(R.id.btnofflinemenu);
        cekdata = findViewById(R.id.btncekdatamenu);
        absen = findViewById(R.id.btnabsenmenu);
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mini_menu.this,fingerPrintauth.class));

            }
        });
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mini_menu.this,fingerPrintauth2.class));
            }
        });
        cekdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mini_menu.this,after_splash.class));
            }
        });
        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mini_menu.this,keterangan_absensi.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}