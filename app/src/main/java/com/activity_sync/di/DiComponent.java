package com.activity_sync.di;

import com.activity_sync.App;
import com.activity_sync.screens.EventsFragmentFragmentBase;
import com.activity_sync.screens.DummyScreen;
import com.activity_sync.screens.EventsScreen;
import com.activity_sync.screens.IntroLastScreen;
import com.activity_sync.screens.IntroScreen;
import com.activity_sync.screens.WelcomeScreen;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UtilsModule.class })
public interface DiComponent
{
    final class Initializer
    {
        private Initializer()
        {

        }
        public static DiComponent init(App app)
        {
            return DaggerDiComponent.builder()
                    .contextModule(new ContextModule(app))
                    .build();
        }
    }

    void inject(App app);

    void inject(DummyScreen dummyScreen);

    void inject(IntroScreen introScreen);

    void inject(IntroLastScreen introLastScreen);

    void inject(WelcomeScreen welcomeScreen);

    void inject(EventsScreen eventsScreen);

    void inject(EventsFragmentFragmentBase eventsFragmentBase);
}