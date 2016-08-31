package com.activity_sync.di;

import android.content.Context;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.services.Navigator;
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
}