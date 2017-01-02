package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;

public class RegisteredUsersPresenter extends UsersFragmentBasePresenter
{
    public RegisteredUsersPresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, int eventId)
    {
        super(view, navigator, uiThread, apiService, eventId);
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.removeEventClick()
                .subscribe(view::openRemoveConfirmationDialog)
        );

        subscriptions.add(view.removeConfirmClick()
                .subscribe(user -> {
                    view.removeSuccessMessage(user);
                    view.removeUser(user);
                })
        );
    }

    @Override
    void loadParticipants()
    {
        apiService.getEventParticipants(eventId)
                .observeOn(uiThread)
                .subscribe(participants -> {

                    view.addUsersList(participants.getParticipants());
                    view.refreshingVisible(false);

                }, this::handleError);
    }
}
