package com.activity_sync.tests;

import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.presenters.EventsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventsView;
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
    IEventsView view;

    PublishSubject eventSelectedEvent = PublishSubject.create();

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
    }

    @Test
    public void eventsPresenter_selectEvent_eventSelected()
    {
        EventsPresenter presenter = createPresenter();
        presenter.start();

        eventSelectedEvent.onNext(testedEvent);
        Mockito.verify(view).eventSelected(testedEvent);
    }

    private EventsPresenter createPresenter()
    {
        return new EventsPresenter(view, navigator, Schedulers.immediate());
    }
}
