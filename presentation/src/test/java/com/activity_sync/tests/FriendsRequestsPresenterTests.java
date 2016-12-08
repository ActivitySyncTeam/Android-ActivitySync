package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.FriendsRequestPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class FriendsRequestsPresenterTests extends ParticipantsBasePresenterTests
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
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        acceptEventClick.onNext(testedParticipant);
        Mockito.verify(view).openAcceptConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeClick_openRemoveConfDialog()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedParticipant);
        Mockito.verify(view).openRemoveConfirmationDialog(testedParticipant);
    }

    @Test
    public void candidatesPresenter_confirmAccept_refresh()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        acceptConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).acceptSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    @Test
    public void candidatesPresenter_removeAccept_refresh()
    {
        FriendsRequestPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).removeSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    private FriendsRequestPresenter createPresenter()
    {
        return new FriendsRequestPresenter(view, navigator, Schedulers.immediate(), apiService);
    }
}
