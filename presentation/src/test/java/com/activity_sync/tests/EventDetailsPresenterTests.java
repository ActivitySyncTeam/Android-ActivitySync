package com.activity_sync.tests;

import com.activity_sync.presentation.presenters.EventDetailsPresenter;
import com.activity_sync.presentation.services.INavigator;
import com.activity_sync.presentation.views.IEventDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class EventDetailsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IEventDetailsView view;

    PublishSubject joinEventClickEvent = PublishSubject.create();
    PublishSubject organizerDetailsClickEvent = PublishSubject.create();
    PublishSubject participantsClickEvent = PublishSubject.create();

    @Before
    public void setup()
    {
        Mockito.when(view.joinEventClick()).thenReturn(joinEventClickEvent);
        Mockito.when(view.organizerDetailsClick()).thenReturn(organizerDetailsClickEvent);
        Mockito.when(view.participantsDetailsClick()).thenReturn(participantsClickEvent);
    }

    @Test
    public void eventDetailsPresenter_clickJoinEvent_openEventsScreen()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        joinEventClickEvent.onNext(this);
        Mockito.verify(navigator).openEventsScreen();
    }

    @Test
    public void eventDetailsPresenter_clickOrganizerDetailsEvent_openDetailsScreen()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        organizerDetailsClickEvent.onNext(this);
        Mockito.verify(navigator).openUserDetailsScreen(1);
    }

    @Test
    public void eventDetailsPresenter_clickParticipantsEvent_openParticipantsScreen()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        participantsClickEvent.onNext(this);
        Mockito.verify(navigator).openParticipantsScreen();
    }

    private EventDetailsPresenter createPresenter()
    {
        return new EventDetailsPresenter(Schedulers.immediate(), view, navigator);
    }
}
