package com.example.e_covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class Biometric_auth extends AppCompatActivity {
    TextView msg;
    private BiometricPrompt biometricPrompt;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_auth);
        msg = findViewById(R.id.biometric_auth);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC)!= PackageManager.PERMISSION_GRANTED){
            msg.setText("Biometric auntentication permission denied");
            startActivity(new Intent(Biometric_auth.this,fingerPrintauth.class));
            onBackPressed();
        }
        else{
            FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);

                if(!fingerprintManagerCompat.isHardwareDetected()){
                    msg.setText("No fingerprint sensor hardware");
                }
                else if(!fingerprintManagerCompat.hasEnrolledFingerprints()){
                    msg.setText("No enrolledFingerprint");
                }
                else{
                    msg.setText("Letakkan jari anda");

                    BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this).setTitle("Biometric Auntentikasi").setSubtitle("Letakkan jari anda").setNegativeButton("Batal", this.getMainExecutor(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.setText("autentikasi batal");
                        }
                    }).build();

                    biometricPrompt.authenticate(getCancellationSignal(),
                            getMainExecutor(),
                            getAuthenticationCallback());
                }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    private BiometricPrompt.AuthenticationCallback
    getAuthenticationCallback() {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                msg.setText("error");
            }

            @Override
            public void onAuthenticationHelp(int helpCode,
                                             CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    BiometricPrompt.AuthenticationResult result) {
                msg.setText("success");
                super.onAuthenticationSucceeded(result);
            }
        };
    }
    private CancellationSignal cancellationSignal;
    private CancellationSignal getCancellationSignal() {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new
                                                       CancellationSignal.OnCancelListener() {
                                                           @Override
                                                           public void onCancel() {
                                                               msg.setText("cancel signal");
                                                           }
                                                       });

        return cancellationSignal;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}