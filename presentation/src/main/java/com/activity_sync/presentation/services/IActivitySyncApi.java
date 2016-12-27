package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;
import com.activity_sync.presentation.models.RegisterResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface IActivitySyncApi
{
    @FormUrlEncoded
    @POST("/api/register")
    Observable<RegisterResponse> registerUser(@Field("username") String username, @Field("password") String password, @Field("first_name") String firstName, @Field("last_name") String lastName, @Field("email") String email);

    @GET("/api/disciplines")
    Observable<AvailableDisciplines> getAvailableDisciplines();

    @GET("/api/levels")
    Observable<AvailableLevels> getAvailableLevels();
}
