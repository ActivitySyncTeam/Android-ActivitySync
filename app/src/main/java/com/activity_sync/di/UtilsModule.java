package com.activity_sync.di;

import android.content.Context;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.services.ApiService;
import com.activity_sync.services.Navigator;
import com.activity_sync.services.PermanentStorage;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class UtilsModule
{
    @Provides
    @Singleton
    public INavigator provideNavigator(Context context)
    {
        return new Navigator(context);
    }

    @Provides
    @Singleton
    public IPermanentStorage providePermanentStorage(Context context)
    {
        return new PermanentStorage(context);
    }

    @Provides
    @Singleton
    public IApiService provideApiRestService()
    {
        return new ApiService("https://secret-gorge-99838.herokuapp.com");
    }
}