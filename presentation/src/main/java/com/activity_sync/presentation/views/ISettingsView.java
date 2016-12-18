package com.activity_sync.presentation.views;

import com.activity_sync.presentation.models.UserUpdate;

import rx.Observable;

public interface ISettingsView
{
    void loadSavedValues();

    void setAccountData(UserUpdate userUpdateDetails);

    Observable<Boolean> enableNotificationsChange();

    Observable<Boolean> enableNotificationsSoundChange();

    Observable<Boolean> enableNotificationsVibrateChange();

    Observable<Integer> searchDaysChange();

    Observable<Integer> searchDaysStopTracking();

    Observable<Integer> searchRangeChange();

    Observable<Integer> searchRangeStopTracking();

    Observable editUserAccount();

    Observable changeUserPassword();

    void setSearchDaysAhead(int progress);

    void setSearchRange(int progress);

    void setSearchDaysAheadLabelText(String value);

    void setSearchRangeLabelText(String value);
}
