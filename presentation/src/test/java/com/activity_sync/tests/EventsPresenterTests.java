package com.activity_sync.tests;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.presenters.EventsFragmentBasePresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class EventsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IEventsFragmentView view;

    PublishSubject eventSelectedEvent = PublishSubject.create();
    PublishSubject refreshEventsEvent = PublishSubject.create();

    Event testedEvent;

    @Before
    public void setup()
    {
        testedEvent = new EventBuilder()
                .setDescription("test")
                .setDate("08.10.2016")
                .setId(123)
                .createEvent();

        Mockito.when(view.selectedEvent()).thenReturn(eventSelectedEvent);
        Mockito.when(view.refreshEvents()).thenReturn(refreshEventsEvent);
    }

    @Test
    public void eventsPresenter_selectEvent_openEventDetails()
    {
        EventsFragmentBasePresenter presenter = createPresenter();
        presenter.start();

        eventSelectedEvent.onNext(testedEvent);
        //Mockito.verify(navigator).openEventDetailsScreen();
        Mockito.verify(view).eventSelected(testedEvent);
    }

    @Test
    public void eventsPresenter_refreshEvent_loadEvents()
    {
        EventsFragmentBasePresenter presenter = createPresenter();
        presenter.start();

        refreshEventsEvent.onNext(this);
        //Mockito.verify(view).apiCallWhichWillBeHere();
        Mockito.verify(view).refreshingVisible(false);
    }

    private EventsFragmentBasePresenter createPresenter()
    {
        return new EventsFragmentBasePresenter(view, navigator, Schedulers.immediate());
    }
}
