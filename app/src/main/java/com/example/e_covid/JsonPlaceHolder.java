package com.example.e_covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolder {

    @POST("login")
    Call<loginPost>getloginPost(@Body loginPost loginPost);
    @POST("regist")
    Call<registPost>getregistPost(@Body registPost registPost);
    @POST("{id}")
    Call<absenPost>getabsenPost(@Path("id") String getPath);
    @GET("{kelas}")
    Call<List<jadwalGet>>getjadwalGet(@Path("id") String postPath,@Body jadwalPost jadwalPost);
}
