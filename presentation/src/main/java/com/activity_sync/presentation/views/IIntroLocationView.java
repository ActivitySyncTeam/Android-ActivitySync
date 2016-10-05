package com.activity_sync.presentation.views;

import rx.Observable;

public interface IIntroLocationView
{
    Observable allowPermissionClick();

    void openPermissionDialog();
}
