package com.activity_sync.presentation.presenters;

import com.activity_sync.presentation.models.UserUpdate;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISettingsView;

import rx.Observable;
import rx.Scheduler;

public class SettingsPresenter extends Presenter<ISettingsView>
{
    private IPermanentStorage permanentStorage;
    private Scheduler uiThread;
    private INavigator navigator;

    private UserUpdate userUpdateDetails;

    public SettingsPresenter(ISettingsView view, INavigator navigator, Scheduler uiThread, IPermanentStorage permanentStorage)
    {
        super(view);
        this.uiThread = uiThread;
        this.navigator = navigator;
        this.permanentStorage = permanentStorage;
    }

    @Override
    public void start()
    {
        super.start();

        view.loadSavedValues();

        Observable.just(getUserUpdateDetails()).observeOn(uiThread).subscribe(view::setAccountData);

        subscriptions.add(view.enableLocationChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_LOCATION_ENABLED, value)));
        subscriptions.add(view.enableNotificationsChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_NOTIFICATION_ENABLED, value)));
        subscriptions.add(view.enableNotificationsSoundChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED, value)));
        subscriptions.add(view.enableNotificationsVibrateChange().subscribe(value -> permanentStorage.saveBoolean(IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED, value)));

        subscriptions.add(view.searchRangeChange()
                .observeOn(uiThread)
                .subscribe(value -> {
                    int progress = value;
                    if (progress < 1)
                    {
                        view.setSearchRange(1);
                        progress = 1;
                    }
                    view.setSearchRangeLabelText(String.valueOf(progress));
                }));

        subscriptions.add(view.searchRangeStopTracking()
                .observeOn(uiThread)
                .subscribe(value -> {
                    permanentStorage.saveInteger(IPermanentStorage.SEARCH_RANGE, value);
                }));

        subscriptions.add(view.searchDaysChange()
                .observeOn(uiThread)
                .subscribe(value -> {
                    int progress = value;
                    if (progress < 1)
                    {
                        view.setSearchDaysAhead(1);
                        progress = 1;
                    }
                    view.setSearchDaysAheadLabelText(String.valueOf(progress));
                }));

        subscriptions.add(view.searchDaysStopTracking()
                .observeOn(uiThread)
                .subscribe(value -> {
                    permanentStorage.saveInteger(IPermanentStorage.SEARCH_DAYS_AHEAD, value);
                }));

        subscriptions.add(view.editUserAccount()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openEditAccountScreen(userUpdateDetails);
                }));

        subscriptions.add(view.changeUserPassword()
                .observeOn(uiThread)
                .subscribe(o -> {
                    navigator.openChangePasswordScreen();
                }));
    }

    private UserUpdate getUserUpdateDetails()
    {
        //API call here UserUpdate created from UserDetails
        userUpdateDetails = new UserUpdate(123, "jkowalski", "Jan", "Kowalski", "jan.kowalski@gmail.com", "Jestem wesoły romek, mam na przedmieściu domek...");
        return userUpdateDetails;
    }
}
