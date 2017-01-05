package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.CurrentUser;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.utils.StringUtils;
import com.activity_sync.presentation.views.IAllUsersScreen;

import rx.Scheduler;
import timber.log.Timber;

public class AllUsersPresenter extends Presenter<IAllUsersScreen>
{
    private final INavigator navigator;
    private final Scheduler uiThread;
    private final IApiService apiService;
    private final CurrentUser currentUser;
    private boolean isFiltered = false;

    private int currentPage = 1;
    private boolean endAlreadyReached = false;

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

        loadUsers(true);

        subscriptions.add(view.refreshUsers()
                .observeOn(uiThread)
                .subscribe(o -> {

                    currentPage = 1;
                    endAlreadyReached = false;

                    loadUsers(false);
                })
        );

        subscriptions.add(view.selectedUser()
                .subscribe(user -> {
                    int userID = user.getUserId();
                    if (userID == currentUser.userId())
                    {
                        navigator.openMyProfileScreen();
                    } else
                    {
                        navigator.openUserDetailsScreen(userID);
                    }
                })
        );

        subscriptions.add(view.filterUsers()
                .observeOn(uiThread)
                .subscribe(o -> {
                    setFiltered();
                    loadUsers(true);
                })
        );

        subscriptions.add(view.endListReached()
                .subscribe(event -> {

                    if (!endAlreadyReached)
                    {
                        currentPage++;
                        loadUsers(false);
                    }
                })
        );
    }

    private void setFiltered()
    {
        isFiltered = !StringUtils.isNullOrEmpty(view.getUserFilterText());
    }

    private void loadUsers(boolean showProgress)
    {
        if (showProgress)
        {
            view.showProgress();
        }
        apiService.getUsers(isFiltered, currentPage, view.getUserFilterText())
                .observeOn(uiThread)
                .subscribe(usersCollection -> {

                    if (usersCollection.getNext() == null)
                    {
                        endAlreadyReached = true;
                    }
                    else
                    {
                        endAlreadyReached = false;
                    }

                    if (currentPage == 1)
                    {
                        view.addUsersListAndClear(usersCollection.getUsers());
                    }
                    else
                    {
                        view.addUsersListAtTheEnd(usersCollection.getUsers());
                    }

                    view.refreshingVisible(false);
                    view.hideProgress();
                }, this::handleError);
    }

    private void handleError(Throwable error)
    {
        error.printStackTrace();
        Timber.d(error.getMessage());
        view.refreshingVisible(false);
        view.hideProgress();
    }
}
