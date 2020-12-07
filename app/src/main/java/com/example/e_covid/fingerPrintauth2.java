package com.example.e_covid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.TextView;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.M)
public class fingerPrintauth2 extends AppCompatActivity {
    private TextView notifikasi,txtlong,txtlat,txtpesan;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";
    private static final int REQUEST_LOCATION = 1;
    private static String url = "http://156.67.221.101:4000/area/";
    LocationManager locationManager;
    String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_printauth2);
        notifikasi = findViewById(R.id.notif2_finger_print_auth);
        txtlong = findViewById(R.id.longFA);
        txtlat = findViewById(R.id.latFA);
        txtpesan = findViewById(R.id.pesanFA);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            OnGPS();
        }
        else{
            getLocation();
            if(txtlat.getText().equals("Text View")){
                Toast.makeText(getApplicationContext(),"cek permission GPS",Toast.LENGTH_SHORT).show();
            }
            else{
                float lat = Float.parseFloat(txtlat.getText().toString());
                float lon = Float.parseFloat(txtlong.getText().toString());
                cek_lokasi(lat,lon);
                Boolean cek = ada_ga_lokasi(txtpesan.getText().toString());
                if (cek==true){
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
                                fingerPrintHandler2 fingerprintHandler = new fingerPrintHandler2(this);
                                fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Lakukan absensi pada lokasi kampus Polban",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                fingerPrintauth2.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                fingerPrintauth2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                txtlat.setText(latitude);
                txtlong.setText(longitude);
//                Toast.makeText(getApplicationContext(),longitude+"/n"+latitude,Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void cek_lokasi(float a,float b){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        final locationPost locationPost = new locationPost(a,b);
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<locationPost> call = jsonPlaceHolder.getLocationPost(locationPost);
        call.enqueue(new Callback<com.example.e_covid.locationPost>() {
            @Override
            public void onResponse(Call<com.example.e_covid.locationPost> call, Response<com.example.e_covid.locationPost> response) {
                locationPost locationPost = response.body();
                String pesan = locationPost.getPesan();
                txtpesan.setText(pesan);
                Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<com.example.e_covid.locationPost> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lost Connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Boolean ada_ga_lokasi(String a){
        if(a.equals("sukses")) return true;
        else return false;
    }
}