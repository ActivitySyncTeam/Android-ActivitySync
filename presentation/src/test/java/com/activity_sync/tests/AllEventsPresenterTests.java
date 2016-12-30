package com.activity_sync.tests;

import com.activity_sync.presentation.models.Discipline;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.Location;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.EventsCollectionBuilder;
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

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

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
    List<Event> events = new ArrayList<>();
    EventsCollection eventsCollection;

    @Before
    public void setup()
    {
        Mockito.when(view.selectedEvent()).thenReturn(eventSelectedEvent);
        Mockito.when(view.refreshEvents()).thenReturn(refreshEventsEvent);
        Mockito.when(view.checkLocationPermissions()).thenReturn(true);
        Mockito.when(view.enableLocationButtonClick()).thenReturn(enableLocationClickEvent);
        Mockito.when(view.locationEnabled()).thenReturn(locationEnabledEvent);
        Mockito.when(view.locationFound()).thenReturn(locationFoundedEvent);
        Mockito.when(view.newDateEvent()).thenReturn(dateSelectedEvent);
        Mockito.when(view.dateLayoutClicked()).thenReturn(dateLayoutClickEvent);
        Mockito.when(view.refreshWithFilterClick()).thenReturn(refreshFilterClickEvent);

        testedEvent = new EventBuilder()
                .setOrganizer(new UserBuilder()
                        .setUserId(12)
                        .setName("Marcin")
                        .setSurname("Zielinski")
                        .createUser())
                .setDate("2017-12-12 23:23:00")
                .setLocation(new LocationBuilder()
                        .setDescription("Park Jordana")
                        .createLocation())
                .setDiscipline(new DisciplineBuilder()
                        .setName("Basketball")
                        .createDiscipline())
                .setNumberOfPlayers(12)
                .setId(123)
                .createEvent();

        events.add(testedEvent);
        eventsCollection = new EventsCollectionBuilder().setEvents(events).create();

        Mockito.when(apiService.getAllEvents()).thenReturn(Observable.just(eventsCollection));
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
        Mockito.when(permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT)).thenReturn(IPermanentStorage.LAST_COORDINATION_DEFAULT);

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
        Mockito.when(permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT)).thenReturn(IPermanentStorage.LAST_COORDINATION_DEFAULT);

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
        Mockito.verify(navigator).openEventDetailsScreen(testedEvent.getEventId());
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
        Mockito.verify(view).askForPermission();
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
        Mockito.verify(apiService, times(2)).getAllEvents();
        Mockito.verify(view, times(2)).addEventsList(events);
        Mockito.verify(view).refreshingVisible(true);
    }

    private AllEventsPresenter createPresenter()
    {
        return new AllEventsPresenter(view, navigator, Schedulers.immediate(), apiService, permanentStorage);
    }
}
