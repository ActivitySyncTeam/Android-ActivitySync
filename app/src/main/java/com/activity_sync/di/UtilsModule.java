package com.activity_sync.di;

import android.content.Context;

import com.activity_sync.custom.ErrorHandler;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.IErrorHandler;
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
    public IApiService provideApiRestService(IPermanentStorage permanentStorage)
    {
        return new ApiService("http://149.156.11.4:10141", permanentStorage);
    }

    @Provides
    @Singleton
    CurrentUser provideCurrentUser(IPermanentStorage permanentStorage, INavigator navigator, IApiService apiService)
    {
        return new CurrentUser(permanentStorage, navigator, apiService);
    }

    @Provides
    @Singleton
    IErrorHandler provideErrorHandler(Context context)
    {
        return new ErrorHandler(context);
    }
}