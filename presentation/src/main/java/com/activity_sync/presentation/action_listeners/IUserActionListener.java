package com.activity_sync.presentation.action_listeners;

import com.activity_sync.presentation.models.User;

public interface IUserActionListener
{
    void onDeclineButtonAction(User user);

    void onAcceptButtonAction(User user);
}
