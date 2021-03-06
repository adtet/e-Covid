package com.example.e_covid;

import android.os.CancellationSignal;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface JsonPlaceHolder {

    @POST("login")
    Call<loginPost>getloginPost(@Body loginPost loginPost);
    @POST("regist")
    Call<registPost>getregistPost(@Body registPost registPost);
    @POST("absen")
    Call<absenPost>getabsenPost(@Body absenPost absenPost);
    @POST("schedule")
    Call<List<jadwalGet>>getjadwalGet(@Body jadwalPost jadwalPost);
    @GET("welcome")
    Call<welcomeGet>getWelcome();
    @POST("history")
    Call<List<getCekdata>>getCekdata(@Body getCekdata getCekdata);
    @POST("location")
    Call<locationPost>getLocationPost(@Body locationPost locationPost);
    @POST("izin")
    Call<izinPost>getIzinPost(@Body izinPost izinPost);
    @Multipart
    @POST("uploadfile")
    Call<ResponseBody>uploadimage(@Part MultipartBody.Part body);
    @POST("sakit")
    Call<sakitPost>getSakitPost(@Body sakitPost sakitPost);

}
