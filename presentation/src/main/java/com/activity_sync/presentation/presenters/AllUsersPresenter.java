package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IAllUsersScreen;

import rx.Scheduler;
import timber.log.Timber;

public class AllUsersPresenter extends Presenter<IAllUsersScreen>
{
    private final INavigator navigator;
    private final Scheduler uiThread;
    private final IApiService apiService;
    private final CurrentUser currentUser;

    public AllUsersPresenter(IAllUsersScreen view, INavigator navigator, Scheduler uiThread, IApiService apiService, CurrentUser currentUser)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
        this.apiService = apiService;
        this.currentUser = currentUser;
    }

    @Override
    public void start()
    {
        super.start();

        loadUsers();

        subscriptions.add(view.refreshUsers()
                .observeOn(uiThread)
                .subscribe(o -> {
                    loadUsers();
                    view.refreshingVisible(false);
                })
        );

        subscriptions.add(view.selectedUser()
                .subscribe(findUsersResponse -> {
                    int userID = findUsersResponse.getUserId();
                    if (userID == currentUser.userId())
                    {
                        navigator.openMyProfileScreen();
                    } else
                    {
                        navigator.openUserDetailsScreen(userID);
                    }
                })
        );
    }

    private void loadUsers()
    {
        apiService.getAllUsers()
                .observeOn(uiThread)
                .subscribe(usersCollection -> {
                    view.refreshingVisible(false);
                    view.addUsersList(usersCollection.getUsers());
                }, this::handleError);
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
    }
}
