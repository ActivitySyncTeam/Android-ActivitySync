package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import java.util.Collection;

import rx.Observable;

public interface IUsersFragmentView
{
    Observable<User> selectedUser();
    Observable refreshUsers();
    void addUsersList(Collection<User> users);
    void refreshingVisible(boolean isRefreshing);
    void removeUser(User userToDelete);

    Observable<User> acceptEventClick();
    Observable<User> removeEventClick();

    void removeSuccessMessage(User user);
    void acceptSuccessMessage(User user);

    void openRemoveConfirmationDialog(User user);
    void openAcceptConfirmationDialog(User user);

    Observable<User> acceptConfirmClick();
    Observable<User> removeConfirmClick();
}
