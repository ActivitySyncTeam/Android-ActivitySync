package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.SettingsPresenter;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISettingsView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.subjects.PublishSubject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettingsPresenterTest
{
    @Mock
    ISettingsView view;

    @Mock
    IPermanentStorage permanentStorage;

    private SettingsPresenter settingsPresenter;

    private PublishSubject<Boolean> enableNotificationsChange = PublishSubject.create();
    private PublishSubject<Boolean> enableLocationChange = PublishSubject.create();
    private PublishSubject<Boolean> enableNotificationsSoundChange = PublishSubject.create();
    private PublishSubject<Boolean> enableNotificationsVibrateChange = PublishSubject.create();
    private PublishSubject<Integer> searchDaysChange = PublishSubject.create();
    private PublishSubject<Integer> searchRangeChange = PublishSubject.create();

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        settingsPresenter = new SettingsPresenter(view, permanentStorage);

        when(view.enableNotificationsChange()).thenReturn(enableNotificationsChange);
        when(view.enableLocationChange()).thenReturn(enableLocationChange);
        when(view.enableNotificationsSoundChange()).thenReturn(enableNotificationsSoundChange);
        when(view.enableNotificationsVibrateChange()).thenReturn(enableNotificationsVibrateChange);
        when(view.searchDaysChange()).thenReturn(searchDaysChange);
        when(view.searchRangeChange()).thenReturn(searchRangeChange);

        settingsPresenter.start();
    }

    @Test
    public void settingsPresenter_toggleEnableNotifications_persistInPermanentStorage() throws Exception
    {
        boolean toogleValue = true;
        enableNotificationsChange.onNext(toogleValue);
        verify(permanentStorage).saveBoolean(IPermanentStorage.IS_NOTIFICATION_ENABLED, toogleValue);
    }

    @Test
    public void settingsPresenter_toggleEnableLocation_persistInPermanentStorage() throws Exception
    {
        boolean toogleValue = true;
        enableLocationChange.onNext(toogleValue);
        verify(permanentStorage).saveBoolean(IPermanentStorage.IS_LOCATION_ENABLED, toogleValue);
    }

    @Test
    public void settingsPresenter_toggleEnableNotificationSound_persistInPermanentStorage() throws Exception
    {
        boolean toogleValue = true;
        enableNotificationsSoundChange.onNext(toogleValue);
        verify(permanentStorage).saveBoolean(IPermanentStorage.IS_NOTIFICATION_SOUND_ENABLED, toogleValue);
    }

    @Test
    public void settingsPresenter_toggleEnableNotificationsVibration_persistInPermanentStorage() throws Exception
    {
        boolean toogleValue = true;
        enableNotificationsVibrateChange.onNext(toogleValue);
        verify(permanentStorage).saveBoolean(IPermanentStorage.IS_NOTIFICATION_VIBRATION_ENABLED, toogleValue);
    }

    @Test
    public void settingsPresenter_changeSearchDaysValue_persistInPermanentStorage() throws Exception
    {
        int changeValue = 35;
        searchDaysChange.onNext(changeValue);
        verify(permanentStorage).saveInteger(IPermanentStorage.SEARCH_DAYS_AHEAD, changeValue);
    }

    @Test
    public void settingsPresenter_changeSearchRangeValue_persistInPermanentStorage() throws Exception
    {
        int changeValue = 100;
        searchRangeChange.onNext(changeValue);
        verify(permanentStorage).saveInteger(IPermanentStorage.SEARCH_RANGE, changeValue);
    }

    @After
    public void tearDown() throws Exception
    {
        settingsPresenter = null;
    }
}