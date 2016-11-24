package com.activity_sync.presentation.views;

import rx.Observable;

public interface ISettingsView
{
    void loadSavedValues();

    Observable<Boolean> enableNotificationsChange();

    Observable<Boolean> enableLocationChange();

    Observable<Boolean> enableNotificationsSoundChange();

    Observable<Boolean> enableNotificationsVibrateChange();

    Observable<Integer> searchDaysChange();
}
