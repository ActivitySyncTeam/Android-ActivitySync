package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.ChangePasswordResponse;
import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.Comment;
import com.activity_sync.presentation.models.CommentsCollection;
import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventID;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.Friends;
import com.activity_sync.presentation.models.Level;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.UserID;
import com.activity_sync.presentation.models.body_models.EventBody;
import com.activity_sync.presentation.models.body_models.EventIDBody;
import com.activity_sync.presentation.models.body_models.OrganizerApprovalBody;
import com.activity_sync.presentation.models.body_models.UserIDBody;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

    @GET("/api/user/me")
    Observable<User> getMyProfile(@Header("Authorization") String accessToken);

    @GET("/api/user/{user_id}")
    Observable<User> getUserData(@Header("Authorization") String accessToken, @Path("user_id") int userId);

    @FormUrlEncoded
    @POST("/api/user/rates/update/")
    Observable<User> rateUser(@Header("Authorization") String accessToken, @Field("userID") int userId, @Field("rate") int rate);

    @POST("/api/event/create")
    Observable<EventID> createEvent(@Header("Authorization") String accessToken, @Body EventBody eventBody);

    @POST("/api/event/update/{event_id}")
    Observable<EventID> updateEvent(@Header("Authorization") String accessToken, @Path("event_id") int eventId, @Body EventBody eventBody);

    @GET("/api/events")
    Observable<EventsCollection> getAllEvents();

    @GET("/api/myEvents")
    Observable<EventsCollection> getMyEvents(@Header("Authorization") String accessToken);

    @GET("api/event/{event_id}")
    Observable<Event> getEventDetails(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @FormUrlEncoded
    @POST("api/event/user/join")
    Observable<Participants> joinEvent(@Header("Authorization") String accessToken, @Field("eventID") int eventID);

    @FormUrlEncoded
    @POST("api/event/user/joinAdmin")
    Observable<Participants> joinEventAsAdmin(@Header("Authorization") String accessToken, @Field("eventID") int eventID);

    @HTTP(method = "DELETE", path = "api/event/user/cancel", hasBody = true)
    Observable<Participants> cancelEventJoinRequest(@Header("Authorization") String accessToken, @Body EventIDBody eventID);

    @HTTP(method = "DELETE", path = "api/event/user/resign", hasBody = true)
    Observable<Participants> leaveEvent(@Header("Authorization") String accessToken, @Body EventIDBody eventID);

    @HTTP(method = "DELETE", path = "api/event/user/remove", hasBody = true)
    Observable<Participants> removeParticipant(@Header("Authorization") String accessToken, @Body OrganizerApprovalBody eventID);

    @HTTP(method = "DELETE", path = "api/event/delete/{event_id}", hasBody = true)
    Observable<Void> deleteEvent(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @HTTP(method = "DELETE", path = "api/event/user/reject", hasBody = true)
    Observable<Participants> rejectCandidate(@Header("Authorization") String accessToken, @Body OrganizerApprovalBody eventID);

    @FormUrlEncoded
    @POST("api/event/user/accept")
    Observable<Participants> acceptCandidate(@Header("Authorization") String accessToken, @Field("eventID") int eventID, @Field("personID") int personID);

    @GET("api/event/comments/{event_id}")
    Observable<CommentsCollection> getEventComments(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @FormUrlEncoded
    @POST("api/event/comments/add/")
    Observable<Comment> addComment(@Header("Authorization") String accessToken, @Field("eventID") int eventId, @Field("comment") String comment);

    @GET("api/event/persons/{event_id}")
    Observable<Participants> getEventParticipants(@Header("Authorization") String accessToken, @Path("event_id") int eventId);

    @GET("api/user/persons/{user_id}")
    Observable<Friends> getFriends(@Header("Authorization") String accessToken, @Path("user_id") int userId);

    @HTTP(method = "DELETE", path = "api/user/user/remove", hasBody = true)
    Observable<Void> deleteFriend(@Header("Authorization") String accessToken, @Body UserIDBody userIDBody);

    @HTTP(method = "DELETE", path = "api/user/user/reject", hasBody = true)
    Observable<Void> rejectFriendRequest(@Header("Authorization") String accessToken, @Body UserIDBody userIDBody);

    @FormUrlEncoded
    @POST("api/user/user/accept")
    Observable<Friends> acceptFriendInvitation(@Header("Authorization") String accessToken, @Field("userID") int userID);

    @FormUrlEncoded
    @POST("api/user/user/join")
    Observable<Friends> sendFriendRequest(@Header("Authorization") String accessToken, @Field("userID") int userID);

    @FormUrlEncoded
    @POST("api/user/user/cancel")
    Observable<Friends> cancelFriendInvitation(@Header("Authorization") String accessToken, @Field("userID") int userID);

    @GET("/api/disciplines")
    Observable<List<Discipline>> getAvailableDisciplines();

    @GET("/api/levels")
    Observable<List<Level>> getAvailableLevels();

    @FormUrlEncoded
    @POST("/api/user/changepassword")
    Observable<ChangePasswordResponse> changePassword(@Header("Authorization") String accessToken, @Field("current_password") String currentPassword, @Field("new_password") String newPassword);

    @FormUrlEncoded
    @POST("/api/user/update")
    Observable<UserID> updateUser(@Header("Authorization") String accessToken, @Field("name") String name, @Field("surname") String surname, @Field("signature") String signature, @Field("email") String email);
}
