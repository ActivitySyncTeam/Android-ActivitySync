package com.activity_sync.presentation.action_listeners;

import com.activity_sync.presentation.models.User;

public interface IParticipantActionListener
{
    void onDeclineButtonClick(User user);

    void onApproveButtonClick(User user);
}
