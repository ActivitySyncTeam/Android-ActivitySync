package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISettingsView;

import rx.Scheduler;

public class SettingsPresenter extends Presenter<ISettingsView>
{
    private final INavigator navigator;
    private Scheduler uiThread;
    private IPermanentStorage permanentStorage;

    public SettingsPresenter(ISettingsView view, INavigator navigator, Scheduler uiThread, IPermanentStorage permanentStorage)
    {
        super(view);
        this.navigator = navigator;
        this.uiThread = uiThread;
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
    }
}
