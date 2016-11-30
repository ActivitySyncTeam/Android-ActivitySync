package com.activity_sync.di;

import com.activity_sync.App;
import com.activity_sync.screens.EventCreatorScreen;
import com.activity_sync.screens.EventDetailsScreen;
import com.activity_sync.screens.EventsFragmentBase;
import com.activity_sync.screens.DummyScreen;
import com.activity_sync.screens.EventsScreen;
import com.activity_sync.screens.IntroLastScreen;
import com.activity_sync.screens.IntroScreen;
import com.activity_sync.screens.ParticipantsFragmentBase;
import com.activity_sync.screens.ParticipantsScreen;
import com.activity_sync.screens.RegisterScreen;
import com.activity_sync.screens.SplashScreen;
import com.activity_sync.screens.LoginScreen;

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

    void inject(LoginScreen loginScreen);

    void inject(EventsScreen eventsScreen);

    void inject(EventsFragmentBase eventsFragmentBase);

    void inject(EventDetailsScreen eventDetailsScreen);

    void inject(ParticipantsScreen participantsScreen);

    void inject(SplashScreen splashScreen);

    void inject(RegisterScreen registerScreen);

    void inject(EventCreatorScreen eventCreatorScreen);

    void inject(ParticipantsFragmentBase participantsFragmentBase);
}