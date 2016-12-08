package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.EventCandidatesPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class EventCandidatesPresenterTests extends ParticipantsBasePresenterTests
{
    private PublishSubject<User> acceptEventClick = PublishSubject.create();
    private PublishSubject<User> removeEventClick = PublishSubject.create();
    private PublishSubject<User> acceptConfirmEvent = PublishSubject.create();
    private PublishSubject<User> removeConfirmEvent = PublishSubject.create();

    @Override
    public void setup()
    {
        super.setup();

        Mockito.when(view.acceptConfirmClick()).thenReturn(acceptConfirmEvent);
        Mockito.when(view.removeConfirmClick()).thenReturn(removeConfirmEvent);
        Mockito.when(view.acceptEventClick()).thenReturn(acceptEventClick);
        Mockito.when(view.removeEventClick()).thenReturn(removeEventClick);
    }

    @Test
    public void candidatesPresenter_acceptClick_openAcceptConfDialog()
    {
        EventCandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        acceptEventClick.onNext(testedParticipant);
        Mockito.verify(view).openAcceptConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeClick_openRemoveConfDialog()
    {
        EventCandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        removeEventClick.onNext(testedParticipant);
        Mockito.verify(view).openRemoveConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_confirmAccept_refresh()
    {
        EventCandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        acceptConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).acceptSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeAccept_refresh()
    {
        EventCandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        removeConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).removeSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    private EventCandidatesPresenter createPresenter(boolean isOrganizer)
    {
        return new EventCandidatesPresenter(view, navigator, Schedulers.immediate(), isOrganizer, apiService);
    }
}
