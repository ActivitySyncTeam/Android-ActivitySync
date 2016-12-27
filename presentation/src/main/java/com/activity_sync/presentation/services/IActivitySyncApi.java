package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;
import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.RegisterResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface IActivitySyncApi
{
    @FormUrlEncoded
    @POST("/api/register")
    Observable<RegisterResponse> register(@Field("username") String username, @Field("password") String password, @Field("first_name") String firstName, @Field("last_name") String lastName, @Field("email") String email);

    @GET("/api/auth/client")
    Observable<ClientDetails> getClientDetails();

    @POST("/api/auth/token/get")
    Observable<Void> login(@Header("Authorization") String authToken, @Query("grant_type") String grantType, @Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("/api/event/create")
    Observable<Void> createEvent(@Header("Authorization") String accessToken, @Query("description") String description, @Field("disciplineID") int disciplineID, @Field("levelID") int levelID, @Field("numberOfPlayers") int playersNumber, @Field("address") Location location, @Field("date") String date, @Field("addMe") boolean addMe);

    @GET("/api/disciplines")
    Observable<AvailableDisciplines> getAvailableDisciplines();

    @GET("/api/levels")
    Observable<AvailableLevels> getAvailableLevels();
}
