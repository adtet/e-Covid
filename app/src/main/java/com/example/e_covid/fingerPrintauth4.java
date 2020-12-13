package com.example.e_covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class fingerPrintauth4 extends AppCompatActivity {
    private TextView notifikasi;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_printauth4);
        notifikasi = findViewById(R.id.notif2_finger_print_auth4);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager  = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){
                notifikasi.setText("Scanner sidik jari tidak terdeteksi");
            }
            else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){
                notifikasi.setText("Tidak dapat izin menggunakan Scanner sidik jari");
            }
            else if(!keyguardManager.isKeyguardSecure()){
                notifikasi.setText("Tambahkan pengunci pada HP anda");
            }
            else if(!fingerprintManager.hasEnrolledFingerprints()){
                notifikasi.setText("Kamu harus menempatkan paling sedikit 1 sidik jari pada fitur ini");
            }
            else{
                notifikasi.setText("Letakkan sidik jari anda pada Scanner agar dapat mengakses aplikasi");
                generateKey();
                if (chiperInit()){

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    fingerPrintHandler4 fingerprintHandler = new fingerPrintHandler4(this);
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);

                }
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey(){
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | NoSuchProviderException e) {

            e.printStackTrace();

        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean chiperInit(){
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Gagal mendapatkan Cipher", e);
        }


        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Gagal inisiasi Cipher", e);
        }
    }
}