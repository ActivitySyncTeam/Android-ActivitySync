package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IMyProfileView;

import rx.Scheduler;

public class MyProfilePresenter extends Presenter<IMyProfileView>
{
    private Scheduler uiThread;
    private INavigator navigator;
    private User user;

    private final CurrentUser currentUser;

    public MyProfilePresenter(IMyProfileView view, INavigator navigator, Scheduler uiThread, CurrentUser currentUser)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.currentUser = currentUser;
    }

    @Override
    public void start()
    {
        super.start();
        createUser();

        view.setData(user);

        subscriptions.add(view.editUserAccount()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEditAccountScreen(user);
                }));

        subscriptions.add(view.changeUserPassword()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openChangePasswordScreen();
                }));
    }

    private void createUser()
    {
        user = new UserBuilder()
                .setName("Marcin")
                .setSurname("Zielinski")
                .setUsername("mzielu")
                .setEmail("kmarcinzielnski@gmail.com")
                .setRegisterDate("2015-12-12")
                .setSignature("Randomly written text")
                .setEvents(23)
                .setUserId("123")
                .setCredibility(85)
                .createUser();
    }
}
