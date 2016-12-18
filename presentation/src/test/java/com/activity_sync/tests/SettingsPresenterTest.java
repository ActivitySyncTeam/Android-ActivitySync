package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.SettingsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.ISettingsView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SettingsPresenterTest
{
    @Mock
    ISettingsView view;

    @Mock
    INavigator navigator;

    @Mock
    IPermanentStorage permanentStorage;

    private SettingsPresenter settingsPresenter;

    private PublishSubject<Boolean> enableNotificationsChange = PublishSubject.create();
    private PublishSubject<Boolean> enableNotificationsSoundChange = PublishSubject.create();
    private PublishSubject<Boolean> enableNotificationsVibrateChange = PublishSubject.create();
    private PublishSubject<Integer> searchDaysChange = PublishSubject.create();
    private PublishSubject<Integer> searchDaysStopTracking = PublishSubject.create();
    private PublishSubject<Integer> searchRangeChange = PublishSubject.create();
    private PublishSubject<Integer> searchRangeStopTracking = PublishSubject.create();

    private PublishSubject openEditAccountScreenEvent = PublishSubject.create();
    private PublishSubject openChangePasswordScreenEvent = PublishSubject.create();

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        settingsPresenter = new SettingsPresenter(view, navigator, Schedulers.immediate(), permanentStorage);

        when(view.enableNotificationsChange()).thenReturn(enableNotificationsChange);
        when(view.enableNotificationsSoundChange()).thenReturn(enableNotificationsSoundChange);
        when(view.enableNotificationsVibrateChange()).thenReturn(enableNotificationsVibrateChange);
        when(view.searchDaysChange()).thenReturn(searchDaysChange);
        when(view.searchDaysStopTracking()).thenReturn(searchDaysStopTracking);
        when(view.searchRangeChange()).thenReturn(searchRangeChange);
        when(view.searchRangeStopTracking()).thenReturn(searchRangeStopTracking);
        when(view.editUserAccount()).thenReturn(openEditAccountScreenEvent);
        when(view.changeUserPassword()).thenReturn(openChangePasswordScreenEvent);

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
    public void settingsPresenter_changeSearchDaysValue_setSearchDaysLabel() throws Exception
    {
        int changeValue = 35;
        int expectedValue = 35;
        searchDaysChange.onNext(changeValue);
        verify(view).setSearchDaysAheadLabelText(String.valueOf(expectedValue));
    }

    @Test
    public void settingsPresenter_changeSearchDaysValueBelowZero_setSearchDaysLabel() throws Exception
    {
        int changeValue = -5;
        int expectedValue = 1;
        searchDaysChange.onNext(changeValue);
        verify(view).setSearchDaysAhead(expectedValue);
        verify(view).setSearchDaysAheadLabelText(String.valueOf(expectedValue));
    }

    @Test
    public void settingsPresenter_changeSearchDaysValue_persistInPermanentStorage() throws Exception
    {
        int changeValue = 100;
        searchDaysStopTracking.onNext(changeValue);
        verify(permanentStorage).saveInteger(IPermanentStorage.SEARCH_DAYS_AHEAD, changeValue);
    }

    @Test
    public void settingsPresenter_changeSearchRangeValue_setSearchDaysLabel() throws Exception
    {
        int changeValue = 35;
        int expectedValue = 35;
        searchRangeChange.onNext(changeValue);
        verify(view).setSearchRangeLabelText(String.valueOf(expectedValue));
    }

    @Test
    public void settingsPresenter_changeSearchRangeValueBelowZero_setSearchDaysLabel() throws Exception
    {
        int changeValue = -5;
        int expectedValue = 1;
        searchRangeChange.onNext(changeValue);
        verify(view).setSearchRange(expectedValue);
        verify(view).setSearchRangeLabelText(String.valueOf(expectedValue));
    }

    @Test
    public void settingsPresenter_changeSearchRangeValue_persistInPermanentStorage() throws Exception
    {
        int changeValue = 100;
        searchRangeStopTracking.onNext(changeValue);
        verify(permanentStorage).saveInteger(IPermanentStorage.SEARCH_RANGE, changeValue);
    }

    @Test
    public void settingsPresenter_clickEditAccountBtn_openEditAccountScreen()
    {
        openEditAccountScreenEvent.onNext(this);
        Mockito.verify(navigator).openEditAccountScreen(any());
    }

    @Test
    public void settingsPresenter_clickChangePasswordBtn_openChangePasswordScreen()
    {
        openChangePasswordScreenEvent.onNext(this);
        Mockito.verify(navigator).openChangePasswordScreen();
    }

    @After
    public void tearDown() throws Exception
    {
        settingsPresenter = null;
    }
}