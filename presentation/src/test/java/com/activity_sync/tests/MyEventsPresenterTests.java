package com.activity_sync.tests;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.EventsCollection;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.EventsCollectionBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.MyEventsPresenter;
import com.activity_sync.presentation.services.IApiService;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

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

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class MyEventsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IEventsFragmentView view;

    @Mock
    IApiService apiService;

    PublishSubject eventSelectedEvent = PublishSubject.create();
    PublishSubject refreshEventsEvent = PublishSubject.create();

    Event testedEvent;
    List<Event> events = new ArrayList<>();
    EventsCollection eventsCollection;

    @Before
    public void setup()
    {
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

        Mockito.when(apiService.getMyEvents()).thenReturn(Observable.just(eventsCollection));

        Mockito.when(view.selectedEvent()).thenReturn(eventSelectedEvent);
        Mockito.when(view.refreshEvents()).thenReturn(refreshEventsEvent);
    }

    @Test
    public void myEventsPresenter_init_hideFilterView()
    {
        MyEventsPresenter presenter = createPresenter();
        presenter.start();

        eventSelectedEvent.onNext(testedEvent);
        Mockito.verify(apiService).getMyEvents();
        Mockito.verify(view).filterLayoutVisible(false);
    }

    @Test
    public void myEventsPresenter_selectEvent_openEventDetails()
    {
        MyEventsPresenter presenter = createPresenter();
        presenter.start();

        eventSelectedEvent.onNext(testedEvent);
        Mockito.verify(navigator).openEventDetailsScreen(testedEvent.getEventId());
    }

    @Test
    public void myEventsPresenter_refreshEvent_loadEvents()
    {
        MyEventsPresenter presenter = createPresenter();
        presenter.start();

        Mockito.reset(view);

        refreshEventsEvent.onNext(this);
        Mockito.verify(apiService, times(2)).getMyEvents();
        Mockito.verify(view, times(2)).refreshingVisible(false);
    }

    private MyEventsPresenter createPresenter()
    {
        return new MyEventsPresenter(view, navigator, Schedulers.immediate(), apiService);
    }
}
