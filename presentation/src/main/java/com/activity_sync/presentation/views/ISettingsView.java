package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.UserUpdate;

import rx.Observable;

public interface ISettingsView
{
    void loadSavedValues();

    void setAccountData(UserUpdate userUpdateDetails);

    Observable<Boolean> enableNotificationsChange();

    Observable<Boolean> enableLocationChange();

    Observable<Boolean> enableNotificationsSoundChange();

    Observable<Boolean> enableNotificationsVibrateChange();

    Observable<Integer> searchDaysChange();

    Observable<Integer> searchRangeChange();

    Observable editUserAccount();

    Observable changeUserPassword();
}
