package com.activity_sync.tests;


import com.activity_sync.presentation.presenters.EventUpdatePresenter;

import org.junit.Test;
import org.mockito.Mockito;

import rx.schedulers.Schedulers;

public class EventUpdatePresenterTests extends EventCreatorPresenterTests
{
    int eventId = 1;

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

        Mockito.verify(navigator).openEventDetailsScreen(eventId);
    }

    private EventUpdatePresenter createPresenter()
    {
        return new EventUpdatePresenter(Schedulers.immediate(), view, navigator, apiService, eventId);
    }
}
