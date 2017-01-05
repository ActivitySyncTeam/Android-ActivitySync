package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.body_models.UserIDBody;
import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IUsersFragmentView;

import rx.Scheduler;

public class FriendsRequestPresenter extends UsersFragmentBasePresenter
{
    public FriendsRequestPresenter(IUsersFragmentView view, INavigator navigator, Scheduler uiThread, IApiService apiService, CurrentUser currentUser)
    {
        super(view, navigator, uiThread, apiService, currentUser);
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

                    apiService.acceptFriendInvitation(user.getUserId())
                            .observeOn(uiThread)
                            .subscribe(friends -> {

                                view.acceptSuccessMessage(user);
                                view.removeUser(user);

                            }, this::handleError);
                })
        );

        subscriptions.add(view.removeConfirmClick()
                .subscribe(user -> {

                    apiService.rejectFriendRequest(new UserIDBody(user.getUserId()))
                            .observeOn(uiThread)
                            .subscribe(friends -> {

                                view.removeSuccessMessage(user);
                                view.removeUser(user);

                            }, this::handleError);
                })
        );
    }

    @Override
    void loadUsers()
    {
        apiService.getFriends(currentUser.userId())
                .observeOn(uiThread)
                .subscribe(friends -> {

                    view.addUsersList(friends.getCandidates());

                }, this::handleError);
    }
}
