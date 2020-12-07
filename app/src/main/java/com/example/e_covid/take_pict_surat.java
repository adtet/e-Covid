package com.example.e_covid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class take_pict_surat extends AppCompatActivity {
    File photofile = null;
    public static int CAPTURE_IMAGE_REQUEST = 1;
    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "e-covid";
    ImageView imageView;
    public String url = "http://156.67.221.101:4000/user/";
    public String id,matakuliah,dosen;
    ImageButton back;
    DatabaseHelper db;
    ImageButton capture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pict_surat);
        imageView = findViewById(R.id.imgsuratShow);
        capture = findViewById(R.id.btncaptureimage);
        db = new DatabaseHelper(this);
        id = db.ambil_id();
        matakuliah = getIntent().getStringExtra("matakuliah");
        dosen = getIntent().getStringExtra("dosen");

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final izinPost izinPost = new izinPost(id,matakuliah,dosen);
                final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
                Call<izinPost>call = jsonPlaceHolder.getIzinPost(izinPost);
                call.enqueue(new Callback<com.example.e_covid.izinPost>() {
                    @Override
                    public void onResponse(Call<com.example.e_covid.izinPost> call, Response<com.example.e_covid.izinPost> response) {
                        izinPost izinPost1 = response.body();
                        String pesan = izinPost1.getIzin();
                        if(pesan.equals("prosedur izin berhasil")){
                            captureImage();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),pesan,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.e_covid.izinPost> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Lost connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void captureImage2(){
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photofile = createImageFile4();
            if(photofile!=null){
                displayMessage(getBaseContext(),photofile.getAbsolutePath());
                Log.i("Mayank",photofile.getAbsolutePath());
                Uri photoURI = Uri.fromFile(photofile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(cameraIntent,CAPTURE_IMAGE_REQUEST);
            }
        }catch (Exception e){
            displayMessage(getBaseContext(),"Camera is not Available"+e.toString());
        }
    }
    private File createImageFile4(){
        File mediastorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
        if(!mediastorageDir.exists()){
            mediastorageDir.mkdir();
            if(!mediastorageDir.mkdir()){
                displayMessage(getBaseContext(),"Unable to create directory.");
                return null;
            }
            else{
                displayMessage(getBaseContext(),"Create directory");
            }
        }
        else{
            displayMessage(getBaseContext(),"Directory exists");
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile = new File(mediastorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
        return mediaFile;
    }
    private void captureImage(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
        else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getPackageManager())!=null){
                try{
                    photofile = createImageFile();
                    displayMessage(getBaseContext(),photofile.getAbsolutePath());
                    Log.i("Mayank",photofile.getAbsolutePath());

                    if(photofile!=null){
                        Uri photoURI = FileProvider.getUriForFile(this,"com.example.e_covid.fileprovider",photofile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                        startActivityForResult(takePictureIntent,CAPTURE_IMAGE_REQUEST);
                    }
                }catch (Exception e){
                    displayMessage(getBaseContext(),e.getMessage().toString());
                }
            }
            else{
                displayMessage(getBaseContext(),"Null");
            }

        }
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy_MM",Locale.getDefault()).format(new Date());
        String imageFileName = "Suratsakit"+id+"_"+"_"+matakuliah+"_"+dosen+"_"+timeStamp+"_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
        storageDir.mkdir();
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void displayMessage(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        db = new DatabaseHelper(this);
        if(requestCode==CAPTURE_IMAGE_REQUEST && resultCode==RESULT_OK){
            Bitmap myBitmap = BitmapFactory.decodeFile(photofile.getAbsolutePath());

            imageView.setImageBitmap(myBitmap);


        }
        else{
            displayMessage(getBaseContext(),"Request cancelled or something went wrong");

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }
    }
    public void upload_gambar(Bitmap a,String b){
        RequestBody reqfile = RequestBody.create(MediaType.parse(b),photofile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file",photofile.getName(),reqfile);
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("http://156.67.221.101:6002/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder2 = retrofit2.create(JsonPlaceHolder.class);
        Call<Response>call2 =  jsonPlaceHolder2.uploadimage(body);
        call2.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, Response<Response> response) {
                displayMessage(getApplicationContext(),"upload sukses "+photofile.getName());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                displayMessage(getApplicationContext(),"gagal upload");
            }
        });
    }
}