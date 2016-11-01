package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;
import java.util.Collection;
import rx.Observable;

public interface IParticipantsView
{
    public Observable<User> selectedUser();

    void addUsersList(Collection<User> users);

    Observable refreshEvents();

    void refreshingVisible(boolean isRefreshing);
}
