package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;
import java.util.Collection;
import rx.Observable;

public interface IParticipantsView
{
    Observable<User> selectedUser();

    void addUsersList(Collection<User> users);

    Observable refreshParticipants();

    void refreshingVisible(boolean isRefreshing);
}
