package com.example.e_covid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.M)
public class fingerPrintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;
    private DatabaseHelper db;
    private String url1 = "http://156.67.221.101:4000/";
    public fingerPrintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager,FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        this.notif("Terdapat error authentikasi : "+errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        this.notif("Error : "+helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        this.notif("Autentikasi sukses");
        db = new DatabaseHelper(this.context);
        String id = db.ambil_id();
        Toast.makeText(context,id,Toast.LENGTH_LONG).show();
        get_jadwal(id);
        launch();
        ((fingerPrintauth)context).finish();

    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.notif("Autentikasi gagal");

    }
    private void notif(String s){
        TextView label = (TextView)((Activity)context).findViewById(R.id.notif_finger_print_auth);
        label.setText(s);
    }
    private void launch(){
        this.context.startActivity(new Intent(this.context,showJadwal.class));
    }
    private void finish(){
        this.finish();
    }
    public void get_jadwal(String a){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url1).addConverterFactory(GsonConverterFactory.create()).build();
        db = new DatabaseHelper(this.context);
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        final jadwalPost jadwalPost = new jadwalPost(a);
        Call<List<jadwalGet>> call = jsonPlaceHolder.getjadwalGet(jadwalPost);
        call.enqueue(new Callback<List<jadwalGet>>() {
            @Override
            public void onResponse(Call<List<jadwalGet>> call, Response<List<jadwalGet>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context,"Code : "+response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                List<jadwalGet> jadwalGets = response.body();
                if (jadwalGets.isEmpty()){
                    Toast.makeText(context,"Jadwal Kosong",Toast.LENGTH_LONG).show();
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
                        Boolean check = db.check_jadwal(e,f);
                        if(check==true){
                            String timestart = a+":"+b;
                            String timeend = c+":"+d;
                            db.insert_jadwal(timestart,timeend,e,f);
                        }
                        else{
                            Toast.makeText(context,"jadwal sudah tersedia",Toast.LENGTH_LONG).show();
                        }
                    }
                    Toast.makeText(context,"download jadwal berhasil",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<jadwalGet>> call, Throwable t) {
                Toast.makeText(context,"download failed",Toast.LENGTH_LONG).show();
            }
        });

    }

}
