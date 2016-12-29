package com.activity_sync.tests;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.AllEventsPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.services.IPermanentStorage;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllEventsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IEventsFragmentView view;

    @Mock
    IApiService apiService;

    @Mock
    IPermanentStorage permanentStorage;

    PublishSubject eventSelectedEvent = PublishSubject.create();
    PublishSubject refreshEventsEvent = PublishSubject.create();
    PublishSubject enableLocationClickEvent = PublishSubject.create();
    PublishSubject<Boolean> locationEnabledEvent = PublishSubject.create();
    PublishSubject<Location> locationFoundedEvent = PublishSubject.create();
    PublishSubject dateLayoutClickEvent = PublishSubject.create();
    PublishSubject<DateTime> dateSelectedEvent = PublishSubject.create();
    PublishSubject refreshFilterClickEvent = PublishSubject.create();

    Event testedEvent;

    @Before
    public void setup()
    {
        testedEvent = new EventBuilder()
                .setOrganizer(new UserBuilder()
                        .setUserId(12)
                        .setName("Marcin")
                        .setSurname("Zielinski")
                        .createUser())
                .setDate(new Date("2016/10/15"))
                .setLocation(new LocationBuilder()
                        .setName("Park Jordana")
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setMaxPlaces(12)
                .setId(123)
                .createEvent();

        Mockito.when(view.selectedEvent()).thenReturn(eventSelectedEvent);
        Mockito.when(view.refreshEvents()).thenReturn(refreshEventsEvent);
        Mockito.when(view.checkLocationPermissions()).thenReturn(true);
        Mockito.when(view.enableLocationButtonClick()).thenReturn(enableLocationClickEvent);
        Mockito.when(view.locationEnabled()).thenReturn(locationEnabledEvent);
        Mockito.when(view.locationFound()).thenReturn(locationFoundedEvent);
        Mockito.when(view.newDateEvent()).thenReturn(dateSelectedEvent);
        Mockito.when(view.dateLayoutClicked()).thenReturn(dateLayoutClickEvent);
        Mockito.when(view.refreshWithFilterClick()).thenReturn(refreshFilterClickEvent);
    }

    @Test
    public void allEventsPresenterInit_permissionsGranted_normalStart()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view, never()).askForPermission();
        Mockito.verify(view).filterLayoutVisible(true);
        Mockito.verify(view).prepareDisciplineSpinner(any());
    }

    @Test
    public void allEventsPresenterInit_permissionsNotGranted_ask()
    {
        Mockito.when(view.checkLocationPermissions()).thenReturn(false);
        Mockito.when(permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_LONGITUDE_DEFAULT)).thenReturn(IPermanentStorage.LAST_LONGITUDE_DEFAULT);

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).askForPermission();
        Mockito.verify(view).noPermissionLayoutVisible();
        Mockito.verify(view).refreshingVisible(false);
    }

    @Test
    public void allEventsPresenterInit_permissionsNotGranted_waitForLocation()
    {
        Mockito.when(view.checkLocationPermissions()).thenReturn(true);
        Mockito.when(permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_LONGITUDE_DEFAULT)).thenReturn(IPermanentStorage.LAST_LONGITUDE_DEFAULT);

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view).searchingForCordsVisible();
    }

    @Test
    public void allEventsPresenter_selectEvent_openEventDetails()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        eventSelectedEvent.onNext(testedEvent);
        verify(navigator).openEventDetailsScreen(testedEvent.getId());
    }

    @Test
    public void allEventsPresenter_refreshEvent_loadEvents()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        refreshEventsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view, times(2)).refreshingVisible(false);
    }

    @Test
    public void allEventsPresenter_enableLocationClickEvent_askForPermission()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        enableLocationClickEvent.onNext(this);
        verify(view).askForPermission();
    }

    @Test
    public void allEventsPresenter_locationEnabled_showSearchingCords()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        locationEnabledEvent.onNext(true);
        view.postLocationPermissionsMessage();
        view.searchingForCordsVisible();
    }

    @Test
    public void allEventsPresenter_locationNotEnabled_showNoPermissionView()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        locationEnabledEvent.onNext(false);
        view.noPermissionLayoutVisible();
    }

    @Test
    public void allEventsPresenter_locationFound_loadEvents()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        locationFoundedEvent.onNext(null);
        Mockito.verify(view).eventsListVisible();
        Mockito.verify(view, never()).noPermissionLayoutVisible();
        Mockito.verify(view, never()).searchingForCordsVisible();

        Mockito.verify(view).filterLayoutVisible(true);
        Mockito.verify(view).prepareDisciplineSpinner(any());
    }

    @Test
    public void allEventsPresenter_dateLayoutClicked_openDatePicker()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        dateLayoutClickEvent.onNext(null);
        Mockito.verify(view).openDatePicker(presenter.getDateTimeSelected());
    }

    @Test
    public void allEventsPresenter_dateSelected_setDate()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        DateTime dateTime = new DateTime();

        dateSelectedEvent.onNext(dateTime);
        Mockito.verify(view).setDate(dateTime);
    }

    @Test
    public void allEventsPresenter_refreshClick_reloadData()
    {
        Mockito.when(view.disciplineFilter()).thenReturn(new Discipline(123, "discipline"));

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshFilterClickEvent.onNext(null);
        Mockito.verify(view).refreshingVisible(true);
        Mockito.verify(view, times(2)).addEventsList(any());
    }

    @Test
    public void allEventsPresenter_fragmentNotDisplayed_permissionNotChecked() throws Exception
    {
        final int index = 0;
        when(view.checkLocationPermissions()).thenReturn(false);
        when(view.getViewPagerCurrentFragmentIndex()).thenReturn(index + 1);
        when(view.getCurrentFragmentIndex()).thenReturn(index);
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        verify(view, never()).askForPermission();
    }

    @Test
    public void allEventsPresenter_fragmentDisplayed_permissionChecked() throws Exception
    {
        final int index = 0;
        when(view.checkLocationPermissions()).thenReturn(false);
        when(view.getViewPagerCurrentFragmentIndex()).thenReturn(index);
        when(view.getCurrentFragmentIndex()).thenReturn(index);
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        verify(view).askForPermission();
        verify(view).eventsListVisible(false);
        verify(view).refreshingVisible(false);
    }

    private AllEventsPresenter createPresenter()
    {
        return new AllEventsPresenter(view, navigator, Schedulers.immediate(), apiService, permanentStorage);
    }
}
