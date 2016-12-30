package com.activity_sync.services;

import android.util.Base64;

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
    public Observable<Void> addComment(int eventId, String comment)
    {
        return api.addComment(accessTokenHeader(), eventId, comment);
    }

    @Override
    public Observable<Participants> getEventParticipants(int eventId)
    {
        return api.getEventParticipants(accessTokenHeader(), eventId);
    }

    @Override
    public Observable<ClientDetails> getClientDetails()
    {
        return api.getClientDetails();
    }

    @Override
    public Observable<User> getUserID()
    {
        return api.getUserID();
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
