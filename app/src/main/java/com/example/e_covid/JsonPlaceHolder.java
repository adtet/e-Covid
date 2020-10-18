package com.example.e_covid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolder {

    @POST("login")
    Call<loginPost>getloginPost(@Body loginPost loginPost);
    @POST("regist")
    Call<registPost>getregistPost(@Body registPost registPost);
}
