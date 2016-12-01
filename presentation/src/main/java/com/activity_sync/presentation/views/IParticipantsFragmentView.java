package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import java.util.Collection;

import rx.Observable;

public interface IParticipantsFragmentView
{
    Observable<User> selectedParticipant();
    Observable refreshParticipants();
    void addParticipantsList(Collection<User> participants);
    void refreshingVisible(boolean isRefreshing);

    Observable<User> acceptEvent();
    Observable<User> declinedEvent();

    void removedMessage();
    void acceptMessage();
    void declinedMessage();
}
