package com.example.salesapp.api;

import com.example.salesapp.model.AccessToken;
import com.example.salesapp.model.GetResponseHistoryTransactions;
import com.example.salesapp.model.GetResponseProduct;
import com.example.salesapp.model.GetResponseToken;
import com.example.salesapp.model.Transaction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<GetResponseToken> forgotPassword(
            @Field("email") String email
    );

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken
    );

    @GET("product")
    Call<GetResponseProduct> getProduct();

    @POST("transactions")
    Call<Transaction> booking(
            @Body Transaction detail
    );

    @POST("logout")
    Call<AccessToken> logout();

    @GET("transactions")
    Call<GetResponseHistoryTransactions> getHistoryTransactions();
}

