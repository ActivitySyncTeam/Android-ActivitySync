package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;
import timber.log.Timber;

abstract public class UsersFragmentBasePresenter extends Presenter<IUsersFragmentView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    protected final INavigator navigator;
    protected final Scheduler uiThread;
    protected final IApiService apiService;
    protected final CurrentUser currentUser;
    protected final int eventId;

    public UsersFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, CurrentUser currentUser)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.currentUser = currentUser;
        this.eventId = 0;
    }

    public UsersFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, int eventId)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.currentUser = null;
        this.eventId = eventId;
    }

    public UsersFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.currentUser = null;
        this.eventId = 0;
    }

    @Override
    public void start()
    {
        super.start();
        loadUsers();

        subscriptions.add(view.refreshUsers()
                .subscribe(participants -> {
                    loadUsers();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedUser()
                .subscribe(participant -> {
                    navigator.openUserDetailsScreen(participant.getUserId());
                })
        );
    }

    abstract void loadUsers();

    protected void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
    }
}
