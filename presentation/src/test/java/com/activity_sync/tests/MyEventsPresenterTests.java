package com.activity_sync.tests;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.DisciplineBuilder;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.models.builders.LocationBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.models.builders.UserDetailsBuilder;
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

import java.util.Date;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

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

    @Before
    public void setup()
    {
        testedEvent = new EventBuilder()
                .setUser(new UserBuilder()
                        .setId(12)
                        .setUserDetails(new UserDetailsBuilder()
                                .setFirstName("Marcin")
                                .setlastName("Zielinski")
                                .createUserDetails())
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
    }

    @Test
    public void myEventsPresenter_selectEvent_openEventDetails()
    {
        MyEventsPresenter presenter = createPresenter();
        presenter.start();

        eventSelectedEvent.onNext(testedEvent);
        Mockito.verify(navigator).openEventDetailsScreen(testedEvent.getId());
    }

    @Test
    public void myEventsPresenter_refreshEvent_loadEvents()
    {
        MyEventsPresenter presenter = createPresenter();
        presenter.start();

        refreshEventsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view).refreshingVisible(false);
    }

    private MyEventsPresenter createPresenter()
    {
        return new MyEventsPresenter(view, navigator, Schedulers.immediate(), apiService);
    }
}
