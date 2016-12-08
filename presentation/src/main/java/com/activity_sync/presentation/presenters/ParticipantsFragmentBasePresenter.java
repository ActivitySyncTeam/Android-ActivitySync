package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;

abstract public class ParticipantsFragmentBasePresenter extends Presenter<IUsersFragmentView>
{
    public static final String EVENT_CHOSEN = "EVENT_CHOSEN";

    private final INavigator navigator;
    private final Scheduler uiThread;
    private final IApiService apiService;
    protected boolean isOrganizer;

    public ParticipantsFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, boolean isOrganizer, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.isOrganizer = isOrganizer;
        this.apiService = apiService;
    }

    public ParticipantsFragmentBasePresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
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
