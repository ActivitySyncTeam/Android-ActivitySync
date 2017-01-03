package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;

public class DeclinedUsersPresenter extends UsersFragmentBasePresenter
{
    public DeclinedUsersPresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, int eventId)
    {
        super(view, navigator, uiThread, apiService, eventId);
    }

    @Override
    void loadUsers()
    {
        apiService.getEventParticipants(eventId)
                .observeOn(uiThread)
                .subscribe(participants -> {

                    view.addUsersList(participants.getParticipants());
                    view.refreshingVisible(false);

                }, this::handleError);
    }
}
