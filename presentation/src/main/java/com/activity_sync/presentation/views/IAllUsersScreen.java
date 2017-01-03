package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.FindUsersResponse;

import java.util.Collection;

import rx.Observable;

public interface IAllUsersScreen
{
    Observable refreshUsers();

    Observable<FindUsersResponse> selectedUser();

    void refreshingVisible(boolean isRefreshing);

    void addUsersList(Collection<FindUsersResponse> users);
}
