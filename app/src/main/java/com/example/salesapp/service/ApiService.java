package com.example.salesapp.service;

import com.example.salesapp.model.Success;
import com.example.salesapp.model.Token;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("register")
    @FormUrlEncoded
    Call<Success> daftar(
            @Field("nama") String nama,
           @Field("pass") String pass

    );

    @POST("login")
    @FormUrlEncoded
    Call<Success> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("forgot")
    @FormUrlEncoded
    Call<Success> forgot(@Field("forgot") String email
    );


    @POST("refresh")
    @FormUrlEncoded
    Call<Token> refresh(@Field("refresh_token") String refreshToken
    );

    @POST("logout")
    Call<Token> logout();
}

