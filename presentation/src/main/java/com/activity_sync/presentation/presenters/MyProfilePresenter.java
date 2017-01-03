package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IMyProfileView;

import rx.Scheduler;

public class MyProfilePresenter extends Presenter<IMyProfileView>
{
    private final Scheduler uiThread;
    private final INavigator navigator;
    private final IApiService apiService;
    private final CurrentUser currentUser;

    private User me;

    public MyProfilePresenter(IMyProfileView view, INavigator navigator, Scheduler uiThread, CurrentUser currentUser, IApiService apiService)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.currentUser = currentUser;
        this.apiService = apiService;
    }

    @Override
    public void start()
    {
        super.start();

        apiService.getUserData(currentUser.userId())
                .observeOn(uiThread)
                .subscribe(user -> {

                    view.setData(user);
                    me = user;
                });

        subscriptions.add(view.editUserAccount()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEditAccountScreen(me);
                }));

        subscriptions.add(view.changeUserPassword()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openChangePasswordScreen();
                }));
    }
}
