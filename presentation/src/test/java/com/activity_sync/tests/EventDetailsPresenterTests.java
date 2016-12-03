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

import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EventDetailsPresenterTests
{
    @Mock
    INavigator navigator;

    @Mock
    IEventDetailsView view;

    PublishSubject joinLeaveEventClickEvent = PublishSubject.create();
    PublishSubject cancelEventClickEvent = PublishSubject.create();
    PublishSubject joinEventConfirmEvent = PublishSubject.create();
    PublishSubject leaveEventConfirmEvent = PublishSubject.create();
    PublishSubject cancelEventConfirmEvent = PublishSubject.create();
    PublishSubject organizerDetailsClickEvent = PublishSubject.create();
    PublishSubject participantsClickEvent = PublishSubject.create();
    PublishSubject googleMapAsyncEvent = PublishSubject.create();
    PublishSubject commentsClickEvent = PublishSubject.create();

    private int eventId = 1;

    @Before
    public void setup()
    {
        Mockito.when(view.joinLeaveEventClick()).thenReturn(joinLeaveEventClickEvent);
        Mockito.when(view.cancelEventClick()).thenReturn(cancelEventClickEvent);

        Mockito.when(view.joinEventConfirmClick()).thenReturn(joinEventConfirmEvent);
        Mockito.when(view.leaveEventConfirmClick()).thenReturn(leaveEventConfirmEvent);
        Mockito.when(view.cancelEventConfirmClick()).thenReturn(cancelEventConfirmEvent);
        Mockito.when(view.commentsClick()).thenReturn(commentsClickEvent);

        Mockito.when(view.organizerDetailsClick()).thenReturn(organizerDetailsClickEvent);
        Mockito.when(view.participantsDetailsClick()).thenReturn(participantsClickEvent);

        Mockito.when(view.googleMapAsyncCompleted()).thenReturn(googleMapAsyncEvent);
    }

    @Test
    public void eventDetailsPresenter_clickJoinEvent_displayJoinConfirmationDialog()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();
        presenter.createEvent(false, false, false); //will be deleted when api

        joinLeaveEventClickEvent.onNext(this);
        Mockito.verify(view).showEnrollConfirmationDialog();
    }

    @Test
    public void eventDetailsPresenter_clickCancelEvent_displayCancelConfirmationDialog()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        cancelEventClickEvent.onNext(this);
        Mockito.verify(view).showCancelConfirmationDialog();
    }

    @Test
    public void eventDetailsPresenter_clickLeaveEvent_displayLeaveConfirmationDialog()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();
        presenter.createEvent(false, true, false);  //will be deleted when api

        joinLeaveEventClickEvent.onNext(this);
        Mockito.verify(view).showLeaveConfirmationDialog();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmJoinEvent_showJoinMessage()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();
        presenter.createEvent(false, false, false); //will be deleted when api

        joinEventConfirmEvent.onNext(this);
        Mockito.verify(view).setOrganizerParticipantView(any());
        Mockito.verify(view).showEnrollMessage();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmLeaveEvent_showLeaveMessage()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();
        presenter.createEvent(false, false, false); //will be deleted when api

        leaveEventConfirmEvent.onNext(this);
        Mockito.verify(view).setOrganizerParticipantView(any());
        Mockito.verify(view).showLeaveEventMessage();
    }

    @Test
    public void eventDetailsPresenter_clickConfirmCancelEvent_openEventsScreen()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();
        presenter.createEvent(false, false, false); //will be deleted when api

        cancelEventConfirmEvent.onNext(this);
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
        Mockito.verify(navigator).openParticipantsScreen(true);
    }

    @Test
    public void eventDetailsPresenter_clickComments_openCommentsScreen()
    {
        EventDetailsPresenter presenter = createPresenter();
        presenter.start();

        commentsClickEvent.onNext(this);
        Mockito.verify(navigator).openCommentsScreen(eventId);
    }

    private EventDetailsPresenter createPresenter()
    {
        return new EventDetailsPresenter(Schedulers.immediate(), view, navigator, eventId, apiService);
    }
}
