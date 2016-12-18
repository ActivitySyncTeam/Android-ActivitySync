package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.User;

import rx.Observable;

public interface IMyProfileView
{
    void setData(User user);

    Observable editUserAccount();

    Observable changeUserPassword();
}
