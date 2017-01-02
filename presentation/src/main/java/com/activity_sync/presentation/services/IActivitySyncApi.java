package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.CommentsCollection;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventBody;
import com.activity_sync.presentation.models.EventID;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.models.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    Observable<LoginResponse> login(@Header("Authorization") String authToken, @Query("grant_type") String grantType, @Query("username") String username, @Query("password") String password);

    @POST("/api/auth/token/revoke")
    Observable<Void> logout(@Header("Authorization") String authToken, @Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("token") String token);

    @GET("/api/user/id")
    Observable<User> getUserID();

    @POST("/api/event/create")
    Observable<EventID> createEvent(@Header("Authorization") String accessToken, @Body EventBody eventBody);

    @POST("/api/event/update/{event_id}")
    Observable<EventID> updateEvent(@Header("Authorization") String accessToken, @Path("event_id") int eventId, @Body EventBody eventBody);

    @GET("/api/events")
    Observable<EventsCollection> getAllEvents();

    @GET("api/event/{event_id}")
    Observable<Event> getEventDetails(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @FormUrlEncoded
    @POST("api/event/user/join")
    Observable<Participants> joinEvent(@Header("Authorization") String accessToken, @Field("eventID") int eventID);

    @FormUrlEncoded
    @POST("api/event/user/joinAdmin")
    Observable<Participants> joinEventAsAdmin(@Header("Authorization") String accessToken, @Field("eventID") int eventID);

    @FormUrlEncoded
    @DELETE("api/event/user/cancel")
    Observable<Participants> cancelEventJoinRequest(@Header("Authorization") String accessToken, @Field("eventID") int eventID);

    @FormUrlEncoded
    @DELETE("api/event/user/resign")
    Observable<Participants> leaveEvent(@Header("Authorization") String accessToken, @Field("eventID") int eventID);

    @GET("api/event/comments/{event_id}")
    Observable<CommentsCollection> getEventComments(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @FormUrlEncoded
    @POST("api/event/comments/add/")
    Observable<Void> addComment(@Header("Authorization") String accessToken, @Field("eventID") int eventId, @Field("comment") String comment);

    @GET("api/event/persons/{event_id}")
    Observable<Participants> getEventParticipants(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @GET("/api/disciplines")
    Observable<List<Discipline>> getAvailableDisciplines();

    @GET("/api/levels")
    Observable<List<Level>> getAvailableLevels();
}
