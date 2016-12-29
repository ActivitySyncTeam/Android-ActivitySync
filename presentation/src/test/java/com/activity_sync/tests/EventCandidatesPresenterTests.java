package com.activity_sync.tests;

import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.ParticpantsBuilder;
import com.activity_sync.presentation.models.builders.UserBuilder;
import com.activity_sync.presentation.presenters.EventCandidatesPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class EventCandidatesPresenterTests extends ParticipantsBasePresenterTests
{
    private PublishSubject<User> acceptEventClick = PublishSubject.create();
    private PublishSubject<User> removeEventClick = PublishSubject.create();
    private PublishSubject<User> acceptConfirmEvent = PublishSubject.create();
    private PublishSubject<User> removeConfirmEvent = PublishSubject.create();

    private Participants participants;
    private List<User> candidates = new ArrayList<>();

    @Override
    public void setup()
    {
        super.setup();

        Mockito.when(view.acceptConfirmClick()).thenReturn(acceptConfirmEvent);
        Mockito.when(view.removeConfirmClick()).thenReturn(removeConfirmEvent);
        Mockito.when(view.acceptEventClick()).thenReturn(acceptEventClick);
        Mockito.when(view.removeEventClick()).thenReturn(removeEventClick);

        candidates.add(new UserBuilder().createUser());
        participants = new ParticpantsBuilder().setCandidates(candidates).create();

        Mockito.when(apiService.getEventParticipants(eventId)).thenReturn(Observable.just(participants));
    }

    @Test
    public void candidatesPresenter_init_loadCandidates()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        Mockito.verify(apiService).getEventParticipants(eventId);
        Mockito.verify(view).refreshingVisible(false);
    }

    @Test
    public void candidatesPresenter_acceptClick_openAcceptConfDialog()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        acceptEventClick.onNext(testedParticipant);
        Mockito.verify(view).openAcceptConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeClick_openRemoveConfDialog()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedParticipant);
        Mockito.verify(view).openRemoveConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_confirmAccept_refresh()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        acceptConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).acceptSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeAccept_refresh()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).removeSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    private EventCandidatesPresenter createPresenter()
    {
        return new EventCandidatesPresenter(view, navigator, Schedulers.immediate(), apiService, eventId);
    }
}
