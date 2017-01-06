package com.activity_sync.presentation.services;

import com.activity_sync.presentation.models.ApiMessage;

public interface INotificationService
{
    void handleMessage(ApiMessage apiMessage);
}
