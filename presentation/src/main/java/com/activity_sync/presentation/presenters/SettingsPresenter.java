package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISettingsView;

public class SettingsPresenter extends Presenter<ISettingsView>
{
    private IPermanentStorage permanentStorage;

    public SettingsPresenter(ISettingsView view, IPermanentStorage permanentStorage)
    {
        super(view);
        this.permanentStorage = permanentStorage;
    }

    @Override
    public void start()
    {
        super.start();

        view.loadSavedValues();

        subscriptions.add(view.enableLocationChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_LOCATION_ENABLED, value)));
        subscriptions.add(view.enableNotificationsChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_NOTIFICATION_ENABLED, value)));
        subscriptions.add(view.enableNotificationsSoundChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED, value)));
        subscriptions.add(view.enableNotificationsVibrateChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED, value)));
        subscriptions.add(view.searchDaysChange().subscribe(value -> permanentStorage.saveInteger(IPermanentStorage.SEARCH_DAYS_AHEAD, value)));
        subscriptions.add(view.searchRangeChange().subscribe(value -> permanentStorage.saveInteger(IPermanentStorage.SEARCH_RANGE, value)));
    }
}
