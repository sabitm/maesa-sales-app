package com.example.salesapp.api;

import com.example.salesapp.model.AccessToken;
import com.example.salesapp.model.GetResponseToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    @FormUrlEncoded
    Call<GetResponseToken> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @POST("forgot")
    @FormUrlEncoded
    Call<GetResponseToken> forgot(@Field("forgot") String email);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken
    );

    @POST("logout")
    Call<AccessToken> logout();
}

