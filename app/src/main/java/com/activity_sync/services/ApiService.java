package com.activity_sync.services;

import android.util.Base64;

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
import com.activity_sync.presentation.models.UsersCollection;
import com.activity_sync.presentation.models.body_models.EventBody;
import com.activity_sync.presentation.models.body_models.EventIDBody;
import com.activity_sync.presentation.models.body_models.OrganizerApprovalBody;
import com.activity_sync.presentation.models.body_models.UserIDBody;
import com.activity_sync.presentation.services.IActivitySyncApi;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ApiService implements IApiService
{
    private IActivitySyncApi api;
    private IPermanentStorage permanentStorage;

    public ApiService(String baseUrl, IPermanentStorage permanentStorage)
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        this.api = retrofit.create(IActivitySyncApi.class);
        this.permanentStorage = permanentStorage;
    }

    @Override
    public Observable<RegisterResponse> register(String username, String password, String firstName, String lastName, String email)
    {
        return api.register(username, password, firstName, lastName, email);
    }

    @Override
    public Observable<LoginResponse> login(String username, String password)
    {
        return api.login(authTokenHeader(), "password", username, password);
    }

    @Override
    public Observable<Void> logout()
    {
        return api.logout(accessTokenHeader(), clientId(), clientSecret(), accessToken());
    }

    @Override
    public Observable<EventID> createEvent(EventBody eventBody)
    {
        return api.createEvent(accessTokenHeader(), eventBody);
    }

    @Override
    public Observable<EventID> updateEvent(int eventId, EventBody eventBody)
    {
        return api.updateEvent(accessTokenHeader(), eventId, eventBody);
    }

    @Override
    public Observable<EventsCollection> getAllEvents()
    {
        return api.getAllEvents();
    }

    @Override
    public Observable<EventsCollection> getMyEvents()
    {
        return api.getMyEvents(accessTokenHeader());
    }

    @Override
    public Observable<EventsCollection> getFilteredEvents(int page, int range, float lat, float lng)
    {
        return api.getFilteredEvents(accessTokenHeader(), page, true, range, lat, lng);
    }

    @Override
    public Observable<EventsCollection> getFilteredEvents(int page, int range, float lat, float lng, int disciplineID)
    {
        return api.getFilteredEvents(accessTokenHeader(), page, true, range, lat, lng, disciplineID);
    }

    @Override
    public Observable<EventsCollection> getFilteredEvents(int page, int range, float lat, float lng, String since)
    {
        return api.getFilteredEvents(accessTokenHeader(), page, true, range, lat, lng, since);
    }

    @Override
    public Observable<EventsCollection> getFilteredEvents(int page, int range, float lat, float lng, int disciplineID, String since)
    {
        return api.getFilteredEvents(accessTokenHeader(), page, true, range, lat, lng, disciplineID, since);
    }

    @Override
    public Observable<Event> getEventDetails(int eventId)
    {
        return api.getEventDetails(accessTokenHeader(), eventId);
    }

    @Override
    public Observable<CommentsCollection> getEventComments(int eventId)
    {
        return api.getEventComments(accessTokenHeader(), eventId);
    }

    @Override
    public Observable<Comment> addComment(int eventId, String comment)
    {
        return api.addComment(accessTokenHeader(), eventId, comment);
    }

    @Override
    public Observable<Participants> getEventParticipants(int eventId)
    {
        return api.getEventParticipants(accessTokenHeader(), eventId);
    }

    @Override
    public Observable<Friends> getFriends(int userId)
    {
        return api.getFriends(accessTokenHeader(), userId);
    }

    @Override
    public Observable<Participants> joinEvent(int eventID)
    {
        return api.joinEvent(accessTokenHeader(), eventID);
    }

    @Override
    public Observable<Participants> joinEventAsAdmin(int eventID)
    {
        return api.joinEventAsAdmin(accessTokenHeader(), eventID);
    }

    @Override
    public Observable<Void> cancelEventJoinRequest(EventIDBody eventID)
    {
        return api.cancelEventJoinRequest(accessTokenHeader(), eventID);
    }

    @Override
    public Observable<Void> leaveEvent(EventIDBody eventID)
    {
        return api.leaveEvent(accessTokenHeader(), eventID);
    }

    @Override
    public Observable<Void> deleteEvent(int eventId)
    {
        return api.deleteEvent(accessTokenHeader(), eventId);
    }

    @Override
    public Observable<Participants> acceptCandidate(int eventID, int personID)
    {
        return api.acceptCandidate(accessTokenHeader(), eventID, personID);
    }

    @Override
    public Observable<Participants> removeParticipant(OrganizerApprovalBody organizerApprovalBody)
    {
        return api.removeParticipant(accessTokenHeader(), organizerApprovalBody);
    }

    @Override
    public Observable<Participants> rejectCandidate(OrganizerApprovalBody organizerApprovalBody)
    {
        return api.rejectCandidate(accessTokenHeader(), organizerApprovalBody);
    }

    @Override
    public Observable<Void> deleteFriend(UserIDBody userIDBody)
    {
        return api.deleteFriend(accessTokenHeader(), userIDBody);
    }

    @Override
    public Observable<Void> rejectFriendRequest(UserIDBody userIDBody)
    {
        return api.rejectFriendRequest(accessTokenHeader(), userIDBody);
    }

    @Override
    public Observable<Friends> acceptFriendInvitation(int userID)
    {
        return api.acceptFriendInvitation(accessTokenHeader(), userID);
    }

    @Override
    public Observable<Friends> sendFriendRequest(int userID)
    {
        return api.sendFriendRequest(accessTokenHeader(), userID);
    }

    @Override
    public Observable<Friends> cancelFriendInvitation(UserIDBody userIDBody)
    {
        return api.cancelFriendInvitation(accessTokenHeader(), userIDBody);
    }

    @Override
    public Observable<ClientDetails> getClientDetails()
    {
        return api.getClientDetails();
    }

    @Override
    public Observable<User> getMyProfile()
    {
        return api.getMyProfile(accessTokenHeader());
    }

    @Override
    public Observable<User> getUserData(int userId)
    {
        return api.getUserData(accessTokenHeader(), userId);
    }

    @Override
    public Observable<User> rateUser(int userId, int rate)
    {
        return api.rateUser(accessTokenHeader(), userId, rate);
    }

    @Override
    public Observable<List<Discipline>> getAvailableDisciplines()
    {
        return api.getAvailableDisciplines();
    }

    @Override
    public Observable<List<Level>> getAvailableLevels()
    {
        return api.getAvailableLevels();
    }

    @Override
    public Observable<ChangePasswordResponse> changePassword(String currentPassword, String newPassword)
    {
        return api.changePassword(accessTokenHeader(), currentPassword, newPassword);
    }

    @Override
    public Observable<UserID> updateUser(String name, String surname, String signature, String email)
    {
        return api.updateUser(accessTokenHeader(), name, surname, signature, email);
    }

    @Override
    public Observable<UsersCollection> getAllUsers()
    {
        return api.getAllUsers();
    }

    private String accessTokenHeader()
    {
        return String.format("Bearer %s", permanentStorage.retrieveString(IPermanentStorage.ACCESS_TOKEN, StringUtils.EMPTY));
    }

    private String authTokenHeader()
    {
        String text = String.format("%s:%s",
                permanentStorage.retrieveString(IPermanentStorage.CLIENT_ID, StringUtils.EMPTY),
                permanentStorage.retrieveString(IPermanentStorage.CLIENT_SECRET, StringUtils.EMPTY));

        byte[] data;

        try
        {
            data = text.getBytes("UTF-8");
            String convertedToken = Base64.encodeToString(data, Base64.DEFAULT).replaceAll("\\n","");
            return String.format("Basic %s", convertedToken);
        }
        catch (UnsupportedEncodingException e)
        {
            Timber.i("Base64 token conversion error");
            return StringUtils.EMPTY;
        }
    }

    private String clientId()
    {
        return permanentStorage.retrieveString(IPermanentStorage.CLIENT_ID, StringUtils.EMPTY);
    }

    private String clientSecret()
    {
        return permanentStorage.retrieveString(IPermanentStorage.CLIENT_SECRET, StringUtils.EMPTY);
    }

    private String accessToken()
    {
        return permanentStorage.retrieveString(IPermanentStorage.ACCESS_TOKEN, StringUtils.EMPTY);
    }
}
