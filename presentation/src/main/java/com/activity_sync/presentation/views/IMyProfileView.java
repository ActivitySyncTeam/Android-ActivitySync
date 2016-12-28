package com.activity_sync.presentation.views;

import rx.Observable;

public interface IMyProfileView extends IUserBaseView
{
    Observable editUserAccount();

    Observable changeUserPassword();
}
