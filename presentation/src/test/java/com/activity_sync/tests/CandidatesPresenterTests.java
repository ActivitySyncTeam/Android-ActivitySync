package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.CandidatesPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class CandidatesPresenterTests extends ParticipantsBasePresenterTests
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
        CandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        acceptEventClick.onNext(testedParticipant);
        Mockito.verify(view).openAcceptConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeClick_openRemoveConfDialog()
    {
        CandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        removeEventClick.onNext(testedParticipant);
        Mockito.verify(view).openRemoveConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_confirmAccept_refresh()
    {
        CandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        acceptConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).acceptSuccessMessage(testedParticipant);
        Mockito.verify(view).removeParticipant(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeAccept_refresh()
    {
        CandidatesPresenter presenter = createPresenter(true);
        presenter.start();

        removeConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).removeSuccessMessage(testedParticipant);
        Mockito.verify(view).removeParticipant(testedParticipant);
    }

    private CandidatesPresenter createPresenter(boolean isOrganizer)
    {
        return new CandidatesPresenter(view, navigator, Schedulers.immediate(), isOrganizer, apiService);
    }
}
