package com.activity_sync.tests;

import com.activity_sync.presentation.models.body_models.AddressBody;
import com.activity_sync.presentation.models.Event;
import com.activity_sync.presentation.models.body_models.EventBody;
import com.activity_sync.presentation.models.EventID;
import com.activity_sync.presentation.models.builders.EventBuilder;
import com.activity_sync.presentation.presenters.EventUpdatePresenter;

import org.junit.Test;
import org.mockito.Mockito;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

public class EventUpdatePresenterTests extends EventCreatorPresenterTests
{
    private Event event;
    private EventID eventID;
    private EventBody eventBody;

    @Override
    public void setup()
    {
        super.setup();

        eventID = new EventID(1);
        event = new EventBuilder().setId(eventID.getEventID()).createEvent();

        eventBody = new EventBody(view.description(),
                view.date(),
                view.players(),
                new AddressBody(view.location()),
                view.discipline().getId(),
                view.level().getId(),
                view.isOrganizerEnrolled(),
                view.status());

        Mockito.when(apiService.updateEvent(anyInt(), any())).thenReturn(Observable.just(eventID));
    }

    @Test
    public void eventCreatorPresenter_createEventClick_openConfirmationDialog()
    {
        EventUpdatePresenter presenter = createPresenter();
        presenter.start();

        createEventClickEvent.onNext(this);

        Mockito.verify(view).showUpdateConfirmationDialog();
    }

    @Test
    public void eventCreatorPresenter_confirmCreation_openEventsScreen()
    {
        EventUpdatePresenter presenter = createPresenter();
        presenter.start();

        confirmActionEvent.onNext(this);

        Mockito.verify(apiService).updateEvent(anyInt(), any());
        Mockito.verify(navigator).openEventDetailsScreen(event.getEventId());
    }

    private EventUpdatePresenter createPresenter()
    {
        return new EventUpdatePresenter(Schedulers.immediate(), view, navigator, apiService, event);
    }
}
