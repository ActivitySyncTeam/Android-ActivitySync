package com.activity_sync.presentation.action_listeners;

import com.activity_sync.presentation.models.User;

public interface IParticipantActionListener
{
    void onDeclineButtonAction(User user);

    void onAcceptButtonAction(User user);
}
