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
import com.activity_sync.presentation.utils.StringUtils;
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
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
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
    PublishSubject endListReached = PublishSubject.create();

    Event testedEvent;
    List<Event> events = new ArrayList<>();
    List<Discipline> disciplines = new ArrayList<>();
    EventsCollection eventsCollection;
    Discipline discipline;

    int standardRange = 1;
    float standardCoordinations = 10.0f;

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
        Mockito.when(view.pageDownReached()).thenReturn(endListReached);

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

        discipline = new DisciplineBuilder().setId(1).createDiscipline();

        events.add(testedEvent);
        eventsCollection = new EventsCollectionBuilder().setEvents(events).create();

        Mockito.when(apiService.getAllEvents()).thenReturn(Observable.just(eventsCollection));
        Mockito.when(apiService.getAvailableDisciplines()).thenReturn(Observable.just(disciplines));
        Mockito.when(apiService.getFilteredEvents(anyInt(), anyInt(), anyFloat(), anyFloat())).thenReturn(Observable.just(eventsCollection));
        Mockito.when(apiService.getFilteredEvents(anyInt(), anyInt(), anyFloat(), anyFloat(), anyInt())).thenReturn(Observable.just(eventsCollection));
        Mockito.when(apiService.getFilteredEvents(anyInt(), anyInt(), anyFloat(), anyFloat(), anyString())).thenReturn(Observable.just(eventsCollection));
        Mockito.when(apiService.getFilteredEvents(anyInt(), anyInt(), anyFloat(), anyFloat(), anyInt(), anyString())).thenReturn(Observable.just(eventsCollection));

        Mockito.when(view.getSelectedDate()).thenReturn(StringUtils.EMPTY);

        Mockito.when(permanentStorage.retrieveInteger(IPermanentStorage.SEARCH_RANGE, IPermanentStorage.SEARCH_RANGE_DEFAULT)).thenReturn(standardRange);
        Mockito.when(permanentStorage.retrieveFloat(IPermanentStorage.LAST_LATITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT)).thenReturn(standardCoordinations);
        Mockito.when(permanentStorage.retrieveFloat(IPermanentStorage.LAST_LONGITUDE, IPermanentStorage.LAST_COORDINATION_DEFAULT)).thenReturn(standardCoordinations);
    }

    @Test
    public void allEventsPresenterInit_permissionsGranted_normalStart()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(view, never()).askForPermission();
        Mockito.verify(view).filterLayoutVisible(true);
        Mockito.verify(view).prepareDisciplineSpinner(any());
        Mockito.verify(apiService).getAvailableDisciplines();
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
        String date = "2017-01-05 23:23:23";
        Mockito.when(view.getSelectedDate()).thenReturn(date);
        Mockito.when(view.disciplineFilter()).thenReturn(new Discipline(123, "discipline"));

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshEventsEvent.onNext(this);
        Mockito.verify(apiService).getFilteredEvents(1, standardRange, standardCoordinations, standardCoordinations, view.disciplineFilter().getId(), date);
        Mockito.verify(view, times(3)).refreshingVisible(false);
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
        Mockito.verify(apiService).getAvailableDisciplines();
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
    public void allEventsPresenter_refreshClick_reloadDataWithDisciplineFilter()
    {
        Mockito.when(view.disciplineFilter()).thenReturn(new Discipline(123, "discipline"));

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshFilterClickEvent.onNext(null);
        Mockito.verify(apiService).getFilteredEvents(1, standardRange, standardCoordinations, standardCoordinations, view.disciplineFilter().getId());
        Mockito.verify(view, times(2)).addEventsListAndClear(events);
        Mockito.verify(view, times(2)).refreshingVisible(true);
    }

    @Test
    public void allEventsPresenter_refreshClick_reloadDataNoFilter()
    {
        Mockito.when(view.disciplineFilter()).thenReturn(new Discipline(AllEventsPresenter.ALL_EVENTS_ID, "discipline"));

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshFilterClickEvent.onNext(null);
        Mockito.verify(apiService, times(2)).getFilteredEvents(1, standardRange, standardCoordinations, standardCoordinations);
        Mockito.verify(view, times(2)).addEventsListAndClear(events);
        Mockito.verify(view, times(2)).refreshingVisible(true);
    }

    @Test
    public void allEventsPresenter_refreshClick_reloadDataWithDisciplineFilterAndDate()
    {
        String date = "2017-01-05 23:23:23";

        Mockito.when(view.disciplineFilter()).thenReturn(new Discipline(123, "discipline"));
        Mockito.when(view.getSelectedDate()).thenReturn(date);

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshFilterClickEvent.onNext(null);
        Mockito.verify(apiService).getFilteredEvents(1, standardRange, standardCoordinations, standardCoordinations, view.disciplineFilter().getId(), date);
        Mockito.verify(view, times(2)).addEventsListAndClear(events);
        Mockito.verify(view, times(2)).refreshingVisible(true);
    }

    @Test
    public void allEventsPresenter_refreshClick_reloadDataWithDateFilter()
    {
        String date = "2017-01-05 23:23:23";

        Mockito.when(view.disciplineFilter()).thenReturn(new Discipline(AllEventsPresenter.ALL_EVENTS_ID, "discipline"));
        Mockito.when(view.getSelectedDate()).thenReturn(date);

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshFilterClickEvent.onNext(null);
        Mockito.verify(apiService).getFilteredEvents(1, standardRange, standardCoordinations, standardCoordinations, date);
        Mockito.verify(view, times(2)).addEventsListAndClear(events);
        Mockito.verify(view, times(2)).refreshingVisible(true);
    }

    private AllEventsPresenter createPresenter()
    {
        return new AllEventsPresenter(view, navigator, Schedulers.immediate(), apiService, permanentStorage);
    }
}
