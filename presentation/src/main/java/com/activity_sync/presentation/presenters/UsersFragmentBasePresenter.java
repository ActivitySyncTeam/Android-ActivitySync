package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;

abstract public class UsersFragmentBasePresenter extends Presenter<IUsersFragmentView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    protected final INavigator navigator;
    protected final Scheduler uiThread;
    protected final IApiService apiService;
    protected final int eventId;

    public UsersFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, int eventId)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.eventId = eventId;
    }

    public UsersFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.eventId = 0;
    }

    @Override
    public void start()
    {
        super.start();
        loadParticipants();

        subscriptions.add(view.refreshUsers()
                .subscribe(participants -> {
                    loadParticipants();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedUser()
                .subscribe(participant -> {
                    navigator.openUserDetailsScreen(participant.getUserId());
                })
        );
    }

    abstract void loadParticipants();
}
