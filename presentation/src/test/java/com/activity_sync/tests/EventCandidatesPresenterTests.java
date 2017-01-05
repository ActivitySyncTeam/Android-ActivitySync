package com.activity_sync.tests;

import com.activity_sync.presentation.models.Participants;
import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.models.builders.ParticipantsBuilder;
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

import static org.mockito.Matchers.any;

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
        participants = new ParticipantsBuilder().setCandidates(candidates).create();

        Mockito.when(apiService.acceptCandidate(eventId, testedUser.getUserId())).thenReturn(Observable.just(participants));
        Mockito.when(apiService.rejectCandidate(any())).thenReturn(Observable.just(participants));
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

        acceptEventClick.onNext(testedUser);
        Mockito.verify(view).openAcceptConfirmationDialog(testedUser);
    }

    @Test
    public void candidatesPresenter_removeClick_openRemoveConfDialog()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedUser);
        Mockito.verify(view).openRemoveConfirmationDialog(testedUser);
    }

    @Test
    public void candidatesPresenter_confirmAccept_refresh()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        acceptConfirmEvent.onNext(testedUser);
        Mockito.verify(apiService).acceptCandidate(eventId, testedUser.getUserId());
        Mockito.verify(view).acceptSuccessMessage(testedUser);
        Mockito.verify(view).removeUser(testedUser);
    }

    @Test
    public void candidatesPresenter_removeAccept_refresh()
    {
        EventCandidatesPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedUser);
        Mockito.verify(apiService).rejectCandidate(any());
        Mockito.verify(view).removeSuccessMessage(testedUser);
        Mockito.verify(view).removeUser(testedUser);
    }

    private EventCandidatesPresenter createPresenter()
    {
        return new EventCandidatesPresenter(view, navigator, Schedulers.immediate(), apiService, eventId);
    }
}
