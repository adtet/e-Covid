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

@RequiresApi(api = Build.VERSION_CODES.M)
public class fingerPrintHandler extends FingerprintManager.AuthenticationCallback {

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
        this.context.startActivity(new Intent(this.context,register.class));
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        this.notif("Autentikasi gagal");
        this.context.startActivity(new Intent(this.context,login.class));
    }
    private void notif(String s){
        TextView label = (TextView)((Activity)context).findViewById(R.id.notif_finger_print_auth);
        label.setText(s);
    }
}
