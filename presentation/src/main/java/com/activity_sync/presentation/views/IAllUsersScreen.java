package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import java.util.Collection;

import rx.Observable;

public interface IAllUsersScreen
{
    Observable refreshUsers();

    Observable filterUsers();

    Observable<User> selectedUser();

    String getUserFilterText();

    void refreshingVisible(boolean isRefreshing);

    void addUsersList(Collection<User> users);

    void showProgress();

    void hideProgress();
}
