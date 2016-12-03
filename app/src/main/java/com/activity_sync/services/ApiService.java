package com.activity_sync.services;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.services.IActivitySyncApi;
import com.activity_sync.presentation.services.IApiService;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class ApiService implements IApiService
{
    private IActivitySyncApi apiService;

    public ApiService(String baseUrl)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();

        apiService = retrofit.create(IActivitySyncApi.class);
    }

    @Override
    public Observable<List<Event>> getEvents()
    {
        return apiService.getEvents();
    }

    @Override
    public Observable<Event> getEvent(int eventID)
    {
        return apiService.getEvent(eventID);
    }
}
