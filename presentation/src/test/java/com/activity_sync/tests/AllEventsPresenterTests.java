package com.activity_sync.tests;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.AllEventsPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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

    PublishSubject eventSelectedEvent = PublishSubject.create();
    PublishSubject refreshEventsEvent = PublishSubject.create();
    PublishSubject enableLocationClickEvent = PublishSubject.create();
    PublishSubject<Boolean> locationEnabledEvent = PublishSubject.create();

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
    }

    @Test
    public void allEventsPresenterInit_permissionsGranted_notAsk()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        verify(view, never()).askForPermission();
    }

    @Test
    public void allEventsPresenterInit_permissionsNotGranted_ask()
    {
        Mockito.when(view.checkLocationPermissions()).thenReturn(false);

        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        verify(view).askForPermission();
        verify(view).eventsListVisible(false);
        verify(view).refreshingVisible(false);
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

        refreshEventsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        verify(view).refreshingVisible(false);
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
    public void allEventsPresenter_locationEnabled_showList()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        locationEnabledEvent.onNext(true);
        verify(view, times(2)).eventsListVisible(true);
    }

    @Test
    public void allEventsPresenter_locationNotEnabled_showEmptyView()
    {
        AllEventsPresenter presenter = createPresenter();
        presenter.start();

        locationEnabledEvent.onNext(false);
        verify(view).eventsListVisible(false);
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
        return new AllEventsPresenter(view, navigator, Schedulers.immediate(), apiService);
    }
}
