package com.activity_sync.tests;

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

    PublishSubject addNewEventClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.addNewEventClick()).thenReturn(addNewEventClickEvent);
    }

    @Test
    public void eventsFragmentBasePresenter_selectEvent_openEventDetails()
    {
        EventsPresenter presenter = createPresenter();
        presenter.start();

        addNewEventClickEvent.onNext(this);
        Mockito.verify(view).newEventClicked();
    }

    private EventsPresenter createPresenter()
    {
        return new EventsPresenter(view, navigator, Schedulers.immediate());
    }
}
