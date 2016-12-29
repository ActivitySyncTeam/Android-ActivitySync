package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;
import timber.log.Timber;

public class EventCandidatesPresenter extends UsersFragmentBasePresenter
{
    public EventCandidatesPresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, int eventId)
    {
        super(view, navigator, uiThread, apiService, eventId);
    }

    @Override
    public void start()
    {
        super.start();

        subscriptions.add(view.acceptEventClick()
                .subscribe(view::openAcceptConfirmationDialog)
        );

        subscriptions.add(view.removeEventClick()
                .subscribe(view::openRemoveConfirmationDialog)
        );

        subscriptions.add(view.acceptConfirmClick()
                .subscribe(user -> {
                    view.acceptSuccessMessage(user);
                    view.removeUser(user);
                })
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

                    view.addUsersList(participants.getCandidates());
                    view.refreshingVisible(false);

                }, this::handleError);
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
    }
}
