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

    Observable<Integer> searchDaysStopTracking();

    Observable<Integer> searchRangeChange();

    Observable<Integer> searchRangeStopTracking();

    void setSearchDaysAhead(int progress);

    void setSearchRange(int progress);

    void setSearchDaysAheadLabelText(String value);

    void setSearchRangeLabelText(String value);
}
