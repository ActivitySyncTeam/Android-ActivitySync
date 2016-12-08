package com.activity_sync.tests;

import com.activity_sync.presentation.models.User;
import com.activity_sync.presentation.presenters.AddedFriendsPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@RunWith(MockitoJUnitRunner.class)
public class AddedFriendsPresenterTests extends ParticipantsBasePresenterTests
{
    private PublishSubject<User> removeEventClick = PublishSubject.create();
    private PublishSubject<User> removeConfirmEvent = PublishSubject.create();

    @Override
    public void setup()
    {
        super.setup();

        Mockito.when(view.removeConfirmClick()).thenReturn(removeConfirmEvent);
        Mockito.when(view.removeEventClick()).thenReturn(removeEventClick);
    }

    @Test
    public void registeredParticipantsPresenter_removeClick_openRemoveConfDialog()
    {
        AddedFriendsPresenter presenter = createPresenter();
        presenter.start();

        removeEventClick.onNext(testedParticipant);
        Mockito.verify(view).openRemoveConfirmationDialog(testedParticipant);
    }

    @Test
    public void registeredParticipantsPresenter_removeAccept_refresh()
    {
        AddedFriendsPresenter presenter = createPresenter();
        presenter.start();

        removeConfirmEvent.onNext(testedParticipant);
        Mockito.verify(view).removeSuccessMessage(testedParticipant);
        Mockito.verify(view).removeUser(testedParticipant);
    }

    private AddedFriendsPresenter createPresenter()
    {
        return new AddedFriendsPresenter(view, navigator, Schedulers.immediate(), apiService);
    }
}
