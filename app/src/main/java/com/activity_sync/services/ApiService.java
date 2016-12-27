package com.activity_sync.services;

import android.util.Base64;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;
import com.activity_sync.presentation.models.ClientDetails;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.LoginResponse;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.services.IActivitySyncApi;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.utils.StringUtils;

import java.io.UnsupportedEncodingException;

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
        Retrofit retrofit = new Retrofit.Builder()
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
        return api.login(authToken(), "password", username, password);
    }

    @Override
    public Observable<Void> createEvent(String description, int disciplineID, int levelID, int playersNumber, Location location, String date, boolean addMe)
    {
        return api.createEvent(accessToken(), description, disciplineID, levelID, playersNumber, location, date, addMe);
    }

    @Override
    public Observable<ClientDetails> getClientDetails()
    {
        return api.getClientDetails();
    }

    @Override
    public Observable<AvailableDisciplines> getAvailableDisciplines()
    {
        return api.getAvailableDisciplines();
    }

    @Override
    public Observable<AvailableLevels> getAvailableLevels()
    {
        return api.getAvailableLevels();
    }

    private String accessToken()
    {
        return String.format("Bearer %s", permanentStorage.retrieveString(IPermanentStorage.ACCESS_TOKEN, StringUtils.EMPTY));
    }

    private String authToken()
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
}
