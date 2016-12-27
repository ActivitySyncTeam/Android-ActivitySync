package com.activity_sync.services;

import com.activity_sync.presentation.models.AvailableDisciplines;
import com.activity_sync.presentation.models.AvailableLevels;
import com.activity_sync.presentation.models.RegisterResponse;
import com.activity_sync.presentation.services.IActivitySyncApi;
import com.activity_sync.presentation.services.IApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

public class ApiService implements IApiService
{
    private IActivitySyncApi api;

    public ApiService(String baseUrl)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        api = retrofit.create(IActivitySyncApi.class);
    }


    @Override
    public Observable<RegisterResponse> registerUser(String username, String password, String firstName, String lastName, String email)
    {
        return api.registerUser(username, password, firstName, lastName, email);
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
}
