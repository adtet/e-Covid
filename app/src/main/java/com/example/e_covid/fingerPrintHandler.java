package com.example.e_covid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.M)
public class fingerPrintHandler extends FingerprintManager.AuthenticationCallback {
    DatabaseHelper db;
    String url = "http://156.67.221.101:4000/user/absen/";
    private Context context;
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
        this.notif("Autentikasi sukses"+result);
        String get_id = db.ambil_id();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        final absenPost loginGet = new absenPost(get_id);
        Call<absenPost>call = jsonPlaceHolder.getabsenPost(get_id);
        call.enqueue(new Callback<absenPost>() {
            @Override
            public void onResponse(Call<absenPost> call, Response<absenPost> response) {
                absenPost absenPost = response.body();
                String x = absenPost.getLink();
                TextView link = (TextView)((Activity)context).findViewById(R.id.txtlinkmasukkelas);
                link.setText(x);
                launch();
            }

            @Override
            public void onFailure(Call<absenPost> call, Throwable t) {

            }
        });

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
        this.context.startActivity(new Intent(this.context,masuk_kelas.class));
    }
}
