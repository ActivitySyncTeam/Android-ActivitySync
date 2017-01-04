package com.activity_sync.di;

import com.activity_sync.App;
import com.activity_sync.screens.AllUsersScreen;
import com.activity_sync.screens.ChangePasswordScreen;
import com.activity_sync.screens.CommentsScreen;
import com.activity_sync.screens.DummyScreen;
import com.activity_sync.screens.EditAccountScreen;
import com.activity_sync.screens.EventDetailsScreen;
import com.activity_sync.screens.EventEditorScreenBase;
import com.activity_sync.screens.EventsFragmentBase;
import com.activity_sync.screens.EventsScreen;
import com.activity_sync.screens.IntroLastScreen;
import com.activity_sync.screens.IntroScreen;
import com.activity_sync.screens.LoginScreen;
import com.activity_sync.screens.MyProfileScreen;
import com.activity_sync.screens.RegisterScreen;
import com.activity_sync.screens.SettingsScreen;
import com.activity_sync.screens.SplashScreen;
import com.activity_sync.screens.UserDetailsScreen;
import com.activity_sync.screens.UsersFragmentBase;
import com.activity_sync.services.BackgroundService;

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

    void inject(SplashScreen splashScreen);

    void inject(RegisterScreen registerScreen);

    void inject(EventEditorScreenBase eventEditorScreenBase);

    void inject(UsersFragmentBase participantsFragmentBase);

    void inject(CommentsScreen commentsScreen);

    void inject(SettingsScreen settingsScreen);

    void inject(UserDetailsScreen userDetailsScreen);

    void inject(MyProfileScreen myProfileScreen);

    void inject(BackgroundService backgroundService);

    void inject(AllUsersScreen allUsersScreen);

    void inject(ChangePasswordScreen changePasswordScreen);

    void inject(EditAccountScreen editAccountScreen);
}